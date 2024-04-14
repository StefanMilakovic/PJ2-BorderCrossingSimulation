package gui;

import persons.Driver;
import simulation.BorderCrossing;
import vehicles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleQueueFrame extends JFrame implements ActionListener,Runnable
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "VehicleQueueFrame.log");
            Logger.getLogger(Driver.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private ImageIcon queueIcon=new ImageIcon("images/car.png");
    private ArrayList<JButton> vehicleButtons=new ArrayList<JButton>();
    public VehicleQueueFrame()
    {
        this.setTitle("Vehicle Queue");
        this.setResizable(false);
        this.setIconImage(queueIcon.getImage());
        this.setSize(500,300);
        this.setVisible(true);
        this.setLayout(new GridLayout(5,9));

        for (int i = 0; i < 45; i++)
        {
            JButton button = new JButton();
            vehicleButtons.add(button);
            button.addActionListener(this);
            this.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < vehicleButtons.size(); i++)
        {
            if (e.getSource() == vehicleButtons.get(i))
            {
                if(BorderCrossing.vehicleQueue[i+5]!=null)
                {
                    Vehicle currentVehicle = BorderCrossing.vehicleQueue[i+5];

                    JScrollPane scrollPane = new JScrollPane(new JTextArea(currentVehicle.toString()));
                    scrollPane.setPreferredSize(new Dimension(600, 100));

                    JOptionPane.showMessageDialog(null, scrollPane, "Informacije o vozilu na poziciji " + (i + 6), JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(200);
        }catch (InterruptedException e)
        {
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! VehicleQueueFrame");

        }
        while(BorderCrossing.simulationRunning)
        {
            refreshVehicleButtons();
        }
    }

    public void refreshVehicleButtons()
    {
        for (int i = 0; i < vehicleButtons.size(); i++)
        {
            if (i < BorderCrossing.vehicleQueue.length+5)
            {
                vehicleButtons.get(i).setText(BorderCrossingWindow.getButtonText(BorderCrossing.vehicleQueue[i+5]));
                vehicleButtons.get(i).setBackground(BorderCrossingWindow.getButtonColor(BorderCrossing.vehicleQueue[i+5]));
            } else {
                vehicleButtons.get(i).setText(" ");
                vehicleButtons.get(i).setBackground(null);
            }
        }
    }
}
