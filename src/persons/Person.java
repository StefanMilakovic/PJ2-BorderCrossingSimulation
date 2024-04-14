package persons;

import enums.LastNames;
import enums.Names;

import java.io.Serializable;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;

public abstract class Person implements Serializable
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Person.log");
            Logger.getLogger(Person.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    protected String name;
    protected String lastName;
    protected IdentificationDocument ID;

    public Person()
    {
        this.name= Names.getRandomName();
        this.lastName= LastNames.getRandomLastName();
        this.ID=new IdentificationDocument();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public IdentificationDocument getID()
    {
        return ID;
    }

    public void setID(IdentificationDocument ID)
    {
        this.ID = ID;
    }

    @Override
    public String toString()
    {
        return name+" "+lastName+" (ID: "+ getID().getIdNumber()+")";
    }
}
