package transportable;

import vehicles.Bus;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class CargoDocumentation
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "CargoDocumentation.log");
            Logger.getLogger(Bus.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private int idNumber;

    public CargoDocumentation()
    {
        Random random=new Random();
        this.idNumber= 10000+random.nextInt(90000);
    }

    public int getIdNumber()
    {
        return idNumber;
    }

    public void setIdNumber(int idNumber)
    {
        this.idNumber = idNumber;
    }

    @Override
    public String toString()
    {
        return "ID: "+idNumber;
    }

}
