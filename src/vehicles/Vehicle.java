package vehicles;

import enums.LicencePlates;
import persons.*;
import simulation.BorderCrossing;
import terminals.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;

public abstract class Vehicle extends Thread
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Vehicle.log");
            Logger.getLogger(Vehicle.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static final int MAX_PASSENGERS_CAR = 5;
    public static final int MAX_PASSENGERS_BUS = 52;
    public static final int MAX_PASSENGERS_TRUCK = 3;

    protected Driver driver;
    protected ArrayList<Passenger> passengers;
    private int numberOfPassengers;
    public String licencePlate;
    public int queuePosition;
    private boolean hadIncident=false;
    private boolean passedPolice=false;
    private boolean passedCustoms=false;

    public Vehicle()
    {
        this.driver=new Driver();
        this.licencePlate= LicencePlates.getRandomLicencePlates();
    }

    public synchronized boolean isNextQueuePositionFree()
    {
        return BorderCrossing.vehicleQueue[queuePosition-1]==null;
    }
    public void removeDriver()
    {
        this.driver=null;
    }

    public synchronized void removeVehicleFromQueue()
    {
        if(this.queuePosition==0)
        {
            BorderCrossing.vehicleQueue[queuePosition]=null;
            BorderCrossing.numberOfVehicles--;
        }
    }

    public synchronized void moveForward()
    {
        if(isNextQueuePositionFree())
        {
            BorderCrossing.vehicleQueue[queuePosition-1]=this;
            BorderCrossing.vehicleQueue[queuePosition]=null;
            this.queuePosition--;
        }
    }

    public synchronized Terminal findPoliceTerminal()
    {
        while (true)
        {
            for (PoliceTerminal p : BorderCrossing.policeTerminals)
            {
                if(p.checkState(this))
                {
                    return p;
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING, "Thread interrupted! Vehicle: " + this.licencePlate);
            }
        }
    }

    public synchronized Terminal findCustomsTerminal()
    {
        while (true)
        {
            for (CustomsTerminal c : BorderCrossing.customsTerminals)
            {
                if (c.checkState(this))
                {
                    return c;
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING, "Thread interrupted! Vehicle: " + this.licencePlate);
            }
        }
    }
    public synchronized void removePassenger(Passenger p)
    {
        Iterator<Passenger> iterator = passengers.iterator();
        while (iterator.hasNext())
        {
            Passenger passenger = iterator.next();
            if (passenger.equals(p)) {
                iterator.remove();
                numberOfPassengers--;
            }
        }

    }

    @Override
    public void run()
    {
        while(queuePosition>0)
        {
            if(!TerminalWatcher.pause)
            {
                if(isNextQueuePositionFree())
                    moveForward();
            }

            try{
                sleep(200);
            }catch (InterruptedException e)
            {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! Vehicle: "+this.licencePlate);
            }
        }

        PoliceTerminal policeTerminal=(PoliceTerminal) findPoliceTerminal();
        removeVehicleFromQueue();

        policeTerminal.processVehicle(this);

        if(passedPolice)
        {
            try {
                BorderCrossing.policeSemaphore.acquire();
            }catch (InterruptedException e) {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! Vehicle: "+this.licencePlate);
            }

            CustomsTerminal customsTerminal=(CustomsTerminal) findCustomsTerminal();
            policeTerminal.setBusy(false);
            policeTerminal.setCurrentVehicle(null);

            customsTerminal.processVehicle(this);

            try {
                BorderCrossing.customsSemaphore.acquire();
            }catch (InterruptedException e) {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! Vehicle: "+this.licencePlate);
            }
        }
    }

    public boolean getHadIncident()
    {
        return hadIncident;
    }

    public boolean getPassedPolice()
    {
        return passedPolice;
    }

    public void setPassedPolice(boolean passedPolice)
    {
        this.passedPolice = passedPolice;
    }

    public boolean getPassedCustoms()
    {
        return passedCustoms;
    }

    public void setPassedCustoms(boolean passedCustoms)
    {
        this.passedCustoms = passedCustoms;
    }

    public boolean HadIncident()
    {
        return hadIncident;
    }

    public void setHadIncident(boolean hadIncident)
    {
        this.hadIncident = hadIncident;
    }
    public String getLicencePlate()
    {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate)
    {
        this.licencePlate = licencePlate;
    }

    public int getQueuePosition()
    {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition)
    {
        this.queuePosition = queuePosition;
    }

    public int getNumberOfPassengers()
    {
        return numberOfPassengers;
    }
    public void setNumberOfPassengers(int numberOfPassengers)
    {
        this.numberOfPassengers = numberOfPassengers;
    }
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public ArrayList<Passenger> getPassengers()
    {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers)
    {
        this.passengers = passengers;
    }

    @Override
    public String toString()
    {
        String putnici="";
        int i=1;
        for(Passenger p:passengers)
        {
            putnici+=i+". "+p.toString()+" ";
            i++;
        }
        return "Vozac: "+getDriver()+"   |Putnici: "+putnici;
    }
}
