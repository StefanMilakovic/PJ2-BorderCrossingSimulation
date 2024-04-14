package terminals;

import vehicles.*;

import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;

public abstract class Terminal
{
    public static Handler handler;
    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Terminal.log");
            Logger.getLogger(Terminal.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    protected String terminalName;
    public TerminalType terminalType;
    public boolean blocked=false;
    public boolean busy=false;
    protected Vehicle currentVehicle=null;

    public Terminal(String name,TerminalType type)
    {
        this.terminalName=name;
        this.terminalType=type;
    }

    public String getTerminalName()
    {
        return terminalName;
    }

    public void setTerminalName(String terminalName)
    {
        this.terminalName = terminalName;
    }

    public TerminalType getTerminalType()
    {
        return terminalType;
    }
    public void setTerminalType(TerminalType terminalType)
    {
        this.terminalType = terminalType;
    }
    public boolean isBlocked()
    {
        return blocked;
    }
    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }
    public boolean isBusy()
    {
        return busy;
    }
    public void setBusy(boolean busy)
    {
        this.busy = busy;
    }
    @Override
    public String toString()
    {
        return getTerminalName()+" "+getTerminalType();
    }

    public boolean checkState(Vehicle vehicle)
    {
        if ((vehicle instanceof PersonalVehicle || vehicle instanceof Bus) && terminalType == TerminalType.CAR_BUS)
        {
            return !blocked && !busy;
        }
        else if (vehicle instanceof Truck && terminalType == TerminalType.TRUCK)
        {
            return !blocked && !busy;
        }
        else
        {
            return false;
        }
    }

    public Vehicle getCurrentVehicle()
    {
        return currentVehicle;
    }
    public void setCurrentVehicle(Vehicle currentVehicle)
    {
        this.currentVehicle = currentVehicle;
    }
    public abstract void processVehicle(Vehicle vehicle);

}

