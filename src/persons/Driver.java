package persons;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class Driver extends Person
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Driver.log");
            Logger.getLogger(Driver.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Driver()
    {
        super();
    }

}
