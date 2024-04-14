package terminals;

import gui.BorderCrossingWindow;
import persons.Passenger;
import simulation.BorderCrossing;
import transportable.CargoDocumentation;
import vehicles.*;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CustomsTerminal extends Terminal
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "CustomsTerminal.log");
            Logger.getLogger(CustomsTerminal.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public CustomsTerminal(String name, TerminalType type)
    {
        super(name, type);

    }
    public static int numVehiclesCustoms =0;
    private synchronized void processPersonalVehicle(PersonalVehicle personalVehicle)
    {
        personalVehicle.setPassedCustoms(true);
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! CustomsTerminal: "+getTerminalName());

        }
    }
    private synchronized void processBus(Bus bus)
    {
        bus.setPassedCustoms(true);
        BorderCrossingWindow.appendToConsole(terminalName+": Autobus dosao do carine,obrada traje duze");

        for(int i=0;i<bus.getPassengers().size();i++)
        {
            Passenger p=bus.getPassengers().get(i);
            if(p.getHasLuggage())
            {
                if(!p.getLuggage().canPassCustomsControl())
                {
                    bus.setHadIncident(true);
                    bus.removePassenger(p);
                    i--;
                    BorderCrossingWindow.appendToConsole(terminalName+": Izbacen putnik(carina): "+p+" iz vozila "+currentVehicle.licencePlate+" zbog posjedovanja nedozvoljenih stvari u koferu.");
                    Report.writeCustomsReport(currentVehicle,terminalName+": Izbacen putnik(carina): "+p+" zbog posjedovanja nedozvoljenih stvari u koferu.");

                }
            }


            try
            {
                Thread.sleep(500);
            }catch (InterruptedException e)
            {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! CustomsTerminal: "+getTerminalName());
            }

        }
    }
    private synchronized void processTruck(Truck truck)
    {
        try
        {
            Thread.sleep(500);
        }catch (InterruptedException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! CustomsTerminal: "+getTerminalName());
        }
        if(!truck.getCargo().canPassCustomsControl())
        {
            truck.setPassedCustoms(false);
            truck.setHadIncident(true);
            BorderCrossingWindow.appendToConsole(terminalName+": Kamion ne moze preci granicu(carina). Deklarisana masa tereta: "+truck.getCargo().getDeclaredCargoWeight()+". Stvarna masa tereta: "+truck.getCargo().getActualCargoWeight());
            Report.writeCustomsReport(truck,terminalName+": Kamion ne moze preci granicu(carina). Deklarisana masa tereta: "+truck.getCargo().getDeclaredCargoWeight()+". Stvarna masa tereta: "+truck.getCargo().getActualCargoWeight());

        }
        else
        {
            truck.setPassedCustoms(true);
            if(truck.getCargo().isDocumentationNeeded())
            {
                truck.getCargo().setCargoDocumentation(new CargoDocumentation());
                BorderCrossingWindow.appendToConsole(terminalName+": Generisana dokumentacija za kamion "+currentVehicle.licencePlate+". ID: "+truck.getCargo().getCargoDocumentation().getIdNumber());
            }
        }
    }



    @Override
    public synchronized void processVehicle(Vehicle vehicle)
    {
        this.currentVehicle=vehicle;
        this.busy = true;

        if(currentVehicle instanceof PersonalVehicle)
        {
            processPersonalVehicle((PersonalVehicle) currentVehicle);
        }
        else if (currentVehicle instanceof Bus)
        {
            processBus((Bus)currentVehicle);
        }
        else if(currentVehicle instanceof Truck)
        {
            processTruck((Truck) currentVehicle);
        }

        if(currentVehicle.getPassedCustoms())
        {
            numVehiclesCustoms++;
            //System.out.println("---------------------------------------------------------------------"+terminalName+": ("+numVehiclesCustoms + "). Vozilo: " + this.currentVehicle.licencePlate);
            BorderCrossingWindow.appendToConsole(terminalName+": ("+numVehiclesCustoms + "). Vozilo: " + this.currentVehicle.licencePlate);
        }

        this.busy = false;
        this.currentVehicle = null;
        BorderCrossing.customsSemaphore.release();
    }
}
