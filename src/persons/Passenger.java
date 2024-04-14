package persons;

import transportable.Luggage;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class Passenger extends Person
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Passenger.log");
            Logger.getLogger(Passenger.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean hasLuggage=false;
    private Luggage luggage =null;

    public Passenger()
    {
       super();
       if(this.getLuggage()!=null)
       {
           hasLuggage=true;
       }
    }

    public Luggage getLuggage()
    {
        return luggage;
    }

    public void setNewLuggage()
    {
        this.luggage = new Luggage();
    }

    public void setLuggage(Luggage luggage)
    {
        this.luggage = luggage;
    }

    public boolean getHasLuggage()
    {
        return hasLuggage;
    }

    public void setHasLuggage(boolean hasLuggage)
    {
        this.hasLuggage = hasLuggage;
    }

    public void setNewLuggage(Luggage luggage)
    {
        this.luggage = luggage;
    }

    public boolean isHasLuggage()
    {
        return hasLuggage;
    }
}
