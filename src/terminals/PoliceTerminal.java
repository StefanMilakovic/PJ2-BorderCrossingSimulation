package terminals;

import gui.BorderCrossingWindow;
import persons.Passenger;
import persons.Person;
import simulation.BorderCrossing;
import vehicles.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PoliceTerminal extends Terminal
{
    public static Handler handler;

    static
    {
        try
        {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "PoliceTerminal.log");
            Logger.getLogger(PoliceTerminal.class.getName()).addHandler(handler);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public PoliceTerminal(String name, TerminalType type)
    {
        super(name, type);
    }

    public static int numVehiclesPolice = 0;
    private ArrayList<Person> punishedPersons=new ArrayList<Person>();
    public static String punishedPassengersFile="reports/punishedPassengers.bin";


    public void writePunishedPersonsToFile(Person p)
    {
        punishedPersons.add(p);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(punishedPassengersFile, true)))
        {
            outputStream.writeObject(punishedPersons);
        } catch (IOException e) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"PoliceTerminal: Couldn't access punishedPassengersFile");
        }
    }

    @Override
    public synchronized void processVehicle(Vehicle vehicle)
    {
        this.currentVehicle=vehicle;
        this.busy = true;
        boolean driverOK=checkDriver();
        if (driverOK)
        {
            currentVehicle.setPassedPolice(true);
            for(int i=0;i<currentVehicle.getPassengers().size();i++)
            {
                Passenger p=currentVehicle.getPassengers().get(i);
                if(!checkPasseneger(p))
                {
                    vehicle.setHadIncident(true);
                    writePunishedPersonsToFile(p);
                    currentVehicle.removePassenger(p);
                    BorderCrossingWindow.appendToConsole(terminalName+": Izbacen putnik(policija). Vozilo: "+currentVehicle.licencePlate+". Putnik:"+p);
                    Report.writePoliceReport(currentVehicle,terminalName+": Izbacen putnik(policija). Putnik:"+p);
                    i--;
                }
            }

            numVehiclesPolice++;
            //System.out.println(terminalName+": ("+numVehiclesPolice + "). Vozilo: " + this.currentVehicle.licencePlate);
            BorderCrossingWindow.appendToConsole(terminalName+": ("+numVehiclesPolice + "). Vozilo: " + this.currentVehicle.licencePlate);
            BorderCrossing.policeSemaphore.release();
        }
        else if(!driverOK)
        {
            currentVehicle.setPassedPolice(false);

            Report.writePoliceReport(currentVehicle,terminalName+". Vozac ima nevazece dokumente. Vozilo izbaceno iz kolone sa svim putnicima zbog vozaca."+". Vozac:"+currentVehicle.getDriver());
            BorderCrossingWindow.appendToConsole(terminalName+". Vozac ima nevazece dokumente. Vozilo: "+currentVehicle.licencePlate+". Vozac:"+currentVehicle.getDriver());
            vehicle.setHadIncident(true);
            writePunishedPersonsToFile(currentVehicle.getDriver());
            currentVehicle.removeDriver();
            for(int i=0;i<currentVehicle.getPassengers().size();i++)
            {

                Passenger p=currentVehicle.getPassengers().get(i);
                currentVehicle.removePassenger(p);
                i--;
                BorderCrossingWindow.appendToConsole(terminalName+": Izbacen putnik(zbog vozaca): "+p.getID().getIdNumber()+"iz vozila "+currentVehicle.licencePlate+". Putnik:"+p);
            }

            BorderCrossingWindow.appendToConsole(terminalName+": Vozilo: "+currentVehicle.getLicencePlate()+" izbaceno iz kolone sa svim putnicima zbog vozaca.");
            this.currentVehicle=null;
            this.busy=false;
        }
    }


    protected boolean checkDriver()
    {
        try
        {
            if(currentVehicle instanceof Bus)
            {
                Thread.sleep(100);
            }
            else if(currentVehicle instanceof PersonalVehicle || currentVehicle instanceof Truck)
            {
                Thread.sleep(500);
            }

        }catch (InterruptedException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interrupted ! PoliceTerminal: "+getTerminalName());
        }
        return this.currentVehicle.getDriver().getID().isValid();
    }

    protected boolean checkPasseneger(Passenger p)
    {
        try
        {
            if(currentVehicle instanceof Bus)
            {
                Thread.sleep(100);
            }
            else if(currentVehicle instanceof PersonalVehicle || currentVehicle instanceof Truck)
            {
                Thread.sleep(500);
            }
        }catch (InterruptedException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interrupted ! PoliceTerminal: "+getTerminalName());
        }
        return p.getID().isValid();
    }
}


