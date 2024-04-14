package transportable;

import interfaces.Transportable;
import vehicles.Bus;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class Luggage implements Serializable, Transportable
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Luggage.log");
            Logger.getLogger(Bus.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean forbiddenItems=false;

    public Luggage()
    {
        Random rand=new Random();
        this.forbiddenItems =(rand.nextDouble()<=0.1);
    }
    public void setForbiddenItems(boolean forbiddenItems)
    {
        this.forbiddenItems = forbiddenItems;
    }
    public boolean isForbiddenItems()
    {
        return forbiddenItems;
    }
    @Override
    public boolean canPassCustomsControl()
    {
        return !forbiddenItems;
    }
}
