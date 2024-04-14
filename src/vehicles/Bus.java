package vehicles;

import persons.Passenger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class Bus extends Vehicle
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Bus.log");
            Logger.getLogger(Bus.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Bus()
    {
        super();
        Random rand = new Random();
        setNumberOfPassengers(rand.nextInt(Vehicle.MAX_PASSENGERS_BUS));
        this.passengers = new ArrayList<Passenger>();

        for (int i = 0; i < getNumberOfPassengers(); i++)
        {
            Passenger passenger = new Passenger();
            if (rand.nextDouble() <= 0.7)
            {
                passenger.setHasLuggage(true);
                passenger.setNewLuggage();
            }
            this.passengers.add(passenger);
        }
    }

    @Override
    public String toString()
    {
        String putnici="";
        int i=1;
        for(Passenger p:passengers)
        {
            boolean suitcase=false;
            if(p.getLuggage()!=null)
            {
                suitcase=true;
            }
            if(suitcase)
            {
                putnici+=i+". "+p.toString()+"(kofer) ";
            }
            else if(!suitcase)
            {
                putnici+=i+". "+p.toString()+"(-) ";
            }
            i++;
        }
        return "Bus -> "+"Vozac: "+getDriver()+"   |Putnici: "+putnici;
    }
}
