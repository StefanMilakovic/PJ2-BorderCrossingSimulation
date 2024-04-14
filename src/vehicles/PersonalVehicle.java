package vehicles;

import persons.Passenger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class PersonalVehicle extends Vehicle
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "PersonalVehicle.log");
            Logger.getLogger(PersonalVehicle.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public PersonalVehicle()
    {
        super();
        Random rand = new Random();
        setNumberOfPassengers(rand.nextInt(Vehicle.MAX_PASSENGERS_CAR));
        this.passengers = new ArrayList<Passenger>();

        for (int i = 0; i < getNumberOfPassengers(); i++)
        {
            this.passengers.add(new Passenger());
        }
    }

    @Override
    public String toString()
    {
        return "PersonalVehicle -> "+super.toString();
    }

}
