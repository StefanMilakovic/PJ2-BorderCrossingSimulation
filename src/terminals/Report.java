package terminals;

import gui.ReportsWindow;
import vehicles.Vehicle;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Report implements Serializable
{
    public static Handler handler;
    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Report.log");
            Logger.getLogger(Terminal.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String currentTime = getCurrentTime();
    private static String policeReportsFile="reports/policeReportsFile"+currentTime+".bin";
    private static String customsReportsFile="reports/customsReportsFile"+currentTime+".txt";
    private static PrintWriter customsReport;
    private static DataOutputStream policeReport;


    public static void writePoliceReport(Vehicle v,String message)
    {
        try {
            policeReport = new DataOutputStream(new FileOutputStream(policeReportsFile,true));
            if(v.getPassedPolice())
            {
                policeReport.writeUTF("true,"+v.getLicencePlate()+","+message);
            }
            else {
                policeReport.writeUTF("false,"+v.getLicencePlate()+","+message);
            }
            policeReport.close();
        } catch (IOException e) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Report exception ! Couldn't access policeReportsFile!");
        }
    }


    public static void writeCustomsReport(Vehicle v,String message)
    {
        try
        {
            customsReport=new PrintWriter(new BufferedWriter(new FileWriter(customsReportsFile,true)));
        }catch (IOException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Report exception ! Couldn't access customsReportsFile!");
        }

        if(v.getPassedPolice()&&v.getPassedCustoms())
        {
            customsReport.println("true,"+v.getLicencePlate()+","+message);
        }
        else
        {
            customsReport.println("false,"+v.getLicencePlate()+","+message);
        }
        customsReport.close();
    }

    public static void clearFiles()
    {
        try
        {
            PrintWriter writer = new PrintWriter(customsReportsFile);
            writer.close();
            FileOutputStream fileOutputStream = new FileOutputStream(policeReportsFile);
            fileOutputStream.close();
        }catch (IOException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Report exception ! Couldn't access customsReportsFile!");
        }

    }
    public static void printFilesToReportsWindow()
    {
        try {
            DataInputStream policeReportsInput = new DataInputStream(new FileInputStream(policeReportsFile));
            while (policeReportsInput.available() > 0)
            {
                String msg = policeReportsInput.readUTF();
                String[] params= msg.split(",");
                if("true".equals(params[0]))
                {
                    ReportsWindow.appendCrossedTextArea(params[1]+", "+params[2]);
                }
                else if("false".equals(params[0]))
                {
                    ReportsWindow.appendNotCrossedTextArea(params[1]+", "+params[2]);
                }
            }
            policeReportsInput.close();


            BufferedReader customsReportsInput = new BufferedReader(new FileReader(customsReportsFile));
            String line;
            while ((line = customsReportsInput.readLine()) != null)
            {
                String[] params= line.split(",");
                if("true".equals(params[0]))
                {
                    ReportsWindow.appendCrossedTextArea(params[1]+", "+params[2]);
                }
                else if("false".equals(params[0]))
                {
                    ReportsWindow.appendNotCrossedTextArea(params[1]+", "+params[2]);
                }
            }

            customsReportsInput.close();
        } catch (IOException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Report exception ! Couldn't access policeReportsFile!");
        }
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        Date currentDate = new Date();
        String formattedTime = dateFormat.format(currentDate);
        return formattedTime;
    }
}
