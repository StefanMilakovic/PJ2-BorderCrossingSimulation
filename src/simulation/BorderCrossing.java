package simulation;

import persons.Person;
import terminals.*;
import vehicles.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BorderCrossing
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "BorderCrossing.log");
            Logger.getLogger(Person.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private int numberOfPersonalVehicles;
    private int numberOfBuses;
    private int numberOfTrucks;
    public volatile static int numberOfVehicles;
    public static List<Vehicle> vehicleList;
    public static Vehicle[] vehicleQueue;
    public static ArrayList<PoliceTerminal> policeTerminals;
    public static List<CustomsTerminal> customsTerminals;
    public synchronized static int getNumberOfVehicles()
    {
        return numberOfVehicles;
    }
    public static void setNumberOfVehicles(int numberOfVehicles)
    {
        BorderCrossing.numberOfVehicles = numberOfVehicles;
    }

    public static Semaphore policeSemaphore =new Semaphore(0,true);
    public static Semaphore customsSemaphore =new Semaphore(0,true);
    public static boolean simulationRunning=false;


    public BorderCrossing(int personalVehicles, int buses, int trucks)
    {
        //ugaseno jer se kreiraju novi fajlovi za svako pokretanje
        //Report.clearFiles();

        this.numberOfVehicles=personalVehicles+buses+trucks;
        this.numberOfPersonalVehicles=personalVehicles;
        this.numberOfBuses=buses;
        this.numberOfTrucks=trucks;

        this.vehicleList=Collections.synchronizedList(new ArrayList<Vehicle>(numberOfVehicles));
        for (int i = 0; i < numberOfPersonalVehicles; i++)
        {
            vehicleList.add(new PersonalVehicle());
        }

        for (int i = 0; i < numberOfBuses; i++)
        {
            vehicleList.add(new Bus());
        }

        for (int i = 0; i < numberOfTrucks; i++)
        {
            vehicleList.add(new Truck());
        }
        Collections.shuffle(vehicleList);

        this.vehicleQueue=new Vehicle[numberOfVehicles];
        for(int i=0;i<numberOfVehicles;i++)
        {
            this.vehicleQueue[i]=vehicleList.get(i);
            this.vehicleQueue[i].queuePosition=i;
        }

        this.policeTerminals=new ArrayList<PoliceTerminal>();
        this.customsTerminals=new ArrayList<CustomsTerminal>();

        policeTerminals.add(new PoliceTerminal("p1", TerminalType.CAR_BUS));
        policeTerminals.add(new PoliceTerminal("p2", TerminalType.CAR_BUS));
        policeTerminals.add(new PoliceTerminal("pk", TerminalType.TRUCK));

        customsTerminals.add(new CustomsTerminal("c1",TerminalType.CAR_BUS));
        customsTerminals.add(new CustomsTerminal("ck",TerminalType.TRUCK));

        TerminalWatcher watcher=new TerminalWatcher();
        watcher.start();

        clearFiles();

        simulationRunning=true;
        TerminalWatcher.startTime= System.currentTimeMillis();
        for (Vehicle v : vehicleQueue)
        {
            v.start();
        }

        for(Vehicle v: vehicleList)
        {
            try
            {
                v.join();
            }catch (InterruptedException e)
            {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interrupted !");

            }
        }
        simulationRunning=false;

        System.out.println("End Of The Simulation!");

        Report.printFilesToReportsWindow();
    }
    public static void clearFiles()
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(PoliceTerminal.punishedPassengersFile);
            fileOutputStream.close();
        } catch (IOException e) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Unable to access punishedPassengersFile!");
        }
    }

    @Override
    public String toString()
    {
        String vozila="";
        for (Vehicle v : vehicleQueue)
        {
            vozila += v.toString() + " \n";
        }
        return vozila;
    }
}
