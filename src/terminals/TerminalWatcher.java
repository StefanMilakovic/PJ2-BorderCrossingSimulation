package terminals;

import simulation.BorderCrossing;
import vehicles.Bus;
import vehicles.Vehicle;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TerminalWatcher extends Thread
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "TerminalWatcher.log");
            Logger.getLogger(Bus.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static boolean pause=false;
    public static long startTime=0;
    public static long pauseStartTime=0;
    public static long pauseEndTime=0;
    public static long totalPauseTime=0;
    public static final String terminalStateFile="terminalStates.txt";

    public static long getTotalPauseTime()
    {
        return pauseEndTime-pauseStartTime;
    }
    protected synchronized void checkTerminalState(Terminal t)
    {
        try
        {
            BufferedReader terminalStates = new BufferedReader(new FileReader(terminalStateFile));
            String str;
            while((str = terminalStates.readLine()) != null)
            {
                String[] parameters = str.split(",");
                if (t.terminalName.equals(parameters[0]))
                {
                    t.blocked=Boolean.parseBoolean(parameters[1]);
                }
            }
            terminalStates.close();
        }catch (IOException e) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"TerminalWatcher exception! Couldn't access terminalStateFile!");
        }
    }


    public static void setPause(boolean p)
    {
        pause=p;
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(terminalStateFile));
            writer.write("p1,"+pause);
            writer.newLine();
            writer.write("p2,"+pause);
            writer.newLine();
            writer.write("pk,"+pause);
            writer.newLine();
            writer.write("c1,"+pause);
            writer.newLine();
            writer.write("ck,"+pause);
            writer.newLine();

            writer.close();
        }
        catch (IOException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"TerminalWatcher exception! Couldn't access terminalStateFile!");
        }
    }

    @Override
    public void run()
    {
        while(BorderCrossing.numberOfVehicles!=0)
        {
            for(Terminal pt: BorderCrossing.policeTerminals)
            {
                checkTerminalState(pt);
            }
            for(Terminal ct: BorderCrossing.customsTerminals)
            {
                checkTerminalState(ct);
            }
            try
            {
                Thread.sleep(200);
            }catch (InterruptedException e)
            {
                Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"TerminalWatcher interrupted!");

            }
        }
    }
}
