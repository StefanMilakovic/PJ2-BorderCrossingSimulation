package persons;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class IdentificationDocument implements Serializable
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "IdentificationDocument.log");
            Logger.getLogger(Person.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean valid=true;
    private int idNumber;

    public IdentificationDocument()
    {
        Random rand=new Random();
        this.idNumber=1000+rand.nextInt(9000);
        if(rand.nextDouble()<=0.03)
        {
            this.valid=false;
        }
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }

    public int getIdNumber()
    {
        return idNumber;
    }

    public void setIdNumber(int idNumber)
    {
        this.idNumber = idNumber;
    }
}
