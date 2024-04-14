package vehicles;

import persons.Passenger;
import transportable.Cargo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class Truck extends Vehicle
{
    Cargo cargo;
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Truck.log");
            Logger.getLogger(Truck.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Truck()
    {
        super();
        cargo=new Cargo();
        Random rand = new Random();
        setNumberOfPassengers(rand.nextInt(Vehicle.MAX_PASSENGERS_TRUCK));
        this.passengers = new ArrayList<Passenger>();

        for (int i = 0; i < getNumberOfPassengers(); i++)
        {
            this.passengers.add(new Passenger());
        }
    }

    @Override
    public String toString()
    {
        return "Truck -> "+super.toString()+"\n     Declared Cargo Weight: "+cargo.getDeclaredCargoWeight()+" Actual Cargo Weight: "+cargo.getActualCargoWeight();
    }

    public Cargo getCargo()
    {
        return cargo;
    }

    public void setCargo(Cargo cargo)
    {
        this.cargo = cargo;
    }


}
