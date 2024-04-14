package gui;
import persons.Driver;
import simulation.BorderCrossing;
import terminals.TerminalWatcher;
import vehicles.Bus;
import vehicles.PersonalVehicle;
import vehicles.Truck;
import vehicles.Vehicle;

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

public class BorderCrossingWindow extends JFrame implements Runnable, ActionListener
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "BorderCrossingWindow.log");
            Logger.getLogger(Driver.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private ImageIcon icon=new ImageIcon("images/icon.png");
    private ImageIcon startIcon=new ImageIcon("images/play.png");
    private ImageIcon queueIcon=new ImageIcon("images/car.png");
    private ImageIcon incident=new ImageIcon("images/warning.png");
    private JButton start = new JButton();
    private JButton p2 = new JButton("p2");
    private JButton p1 = new JButton("p1");
    private JButton c1 = new JButton("c1");
    private JButton pk = new JButton("pk");
    private JButton ck = new JButton("ck");
    private JButton position0 = new JButton("0");
    private JButton position1 = new JButton("1");
    private JButton position2 = new JButton("2");
    private JButton position3 = new JButton("3");
    private JButton position4 = new JButton("4");
    private JLabel timeLabel = new JLabel("t:");
    private JPanel panel=new JPanel();
    private JButton queueButton=new JButton();
    private JButton vehiclesWithIncident =new JButton();
    private static JTextArea consoleTextArea =new JTextArea();
    private ArrayList<JButton> vehicleButtons =new ArrayList<JButton>();
    private ArrayList<JButton> policeButtons =new ArrayList<JButton>();
    private ArrayList<JButton> customsButtons =new ArrayList<JButton>();

    public void refreshVehicleButtons()
    {
        for (int i = 0; i < vehicleButtons.size(); i++)
        {
            if (i < BorderCrossing.vehicleQueue.length)
            {
                vehicleButtons.get(i).setText(getButtonText(BorderCrossing.vehicleQueue[i]));
                vehicleButtons.get(i).setBackground(getButtonColor(BorderCrossing.vehicleQueue[i]));
            } else {
                vehicleButtons.get(i).setText(" ");
                vehicleButtons.get(i).setBackground(null);
            }
        }
    }
    public void refreshPoliceButtons()
    {
        policeButtons.get(0).setText("P1:"+getButtonText(BorderCrossing.policeTerminals.get(0).getCurrentVehicle()));
        policeButtons.get(0).setBackground(getButtonColor(BorderCrossing.policeTerminals.get(0).getCurrentVehicle()));

        policeButtons.get(1).setText("P2:"+getButtonText(BorderCrossing.policeTerminals.get(1).getCurrentVehicle()));
        policeButtons.get(1).setBackground(getButtonColor(BorderCrossing.policeTerminals.get(1).getCurrentVehicle()));

        policeButtons.get(2).setText("PK:"+getButtonText(BorderCrossing.policeTerminals.get(2).getCurrentVehicle()));
        policeButtons.get(2).setBackground(getButtonColor(BorderCrossing.policeTerminals.get(2).getCurrentVehicle()));

    }

    public void refreshCustomsButtons()
    {
        customsButtons.get(0).setText("C1:"+getButtonText(BorderCrossing.customsTerminals.get(0).getCurrentVehicle()));
        customsButtons.get(0).setBackground(getButtonColor(BorderCrossing.customsTerminals.get(0).getCurrentVehicle()));

        customsButtons.get(1).setText("CK:"+getButtonText(BorderCrossing.customsTerminals.get(1).getCurrentVehicle()));
        customsButtons.get(1).setBackground(getButtonColor(BorderCrossing.customsTerminals.get(1).getCurrentVehicle()));
    }

    public void refreshTime()
    {
        long currentTime = System.currentTimeMillis();
        if(!TerminalWatcher.pause)
        {
            long elapsedTime = currentTime - TerminalWatcher.startTime-TerminalWatcher.totalPauseTime;
            timeLabel.setText("Time:"+elapsedTime / 1000+"s");
        }
    }

    public static Color getButtonColor(Vehicle vehicle)
    {
        if(vehicle instanceof PersonalVehicle)
        {
            return Color.RED;
        }
        else if(vehicle instanceof Bus)
        {
            return Color.CYAN;
        }
        else if(vehicle instanceof Truck)
        {
            return Color.BLUE;
        }
        return Color.WHITE;
    }

    public static String getButtonText(Vehicle vehicle)
    {
        if(vehicle instanceof PersonalVehicle)
        {
            return "Car";
        }
        else if(vehicle instanceof Bus)
        {
            return "Bus";
        }
        else if(vehicle instanceof Truck)
        {
            return "Truck";
        }
        return "";
    }

    public BorderCrossingWindow()
    {
        panel.setLayout(null);
        panel.setBounds(20,50,445,400);
        panel.setBackground(new Color(192,192,192));

        int panelWidth = panel.getWidth();

        int buttonWidth = 90;
        int buttonHeight = 50;
        int buttonSpacing = 5;

        int centerX = (panelWidth - buttonWidth) / 2;

        timeLabel.setBounds(10, 370, 100, 20);
        panel.add(timeLabel);

        p1.setBounds(centerX - buttonWidth - buttonSpacing, 10 + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
        p1.addActionListener(this);
        panel.add(p1);
        policeButtons.add(p1);

        p2.setBounds(centerX, 10 + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
        p2.addActionListener(this);
        panel.add(p2);
        policeButtons.add(p2);

        pk.setBounds(centerX + buttonWidth + buttonSpacing, 10 + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
        pk.addActionListener(this);
        panel.add(pk);
        policeButtons.add(pk);

        c1.setBounds(centerX - buttonWidth - buttonSpacing, 10, buttonWidth, buttonHeight);
        c1.addActionListener(this);
        panel.add(c1);
        customsButtons.add(c1);

        ck.setBounds(centerX + buttonWidth + buttonSpacing, 10, buttonWidth, buttonHeight);
        ck.addActionListener(this);
        panel.add(ck);
        customsButtons.add(ck);

        position0.setBounds(centerX, 10 + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        position0.addActionListener(this);
        panel.add(position0);
        vehicleButtons.add(position0);

        position1.setBounds(centerX, 10 + 3 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        position1.addActionListener(this);
        panel.add(position1);
        vehicleButtons.add(position1);

        position2.setBounds(centerX, 10 + 4 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        position2.addActionListener(this);
        panel.add(position2);
        vehicleButtons.add(position2);

        position3.setBounds(centerX, 10 + 5 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        position3.addActionListener(this);
        panel.add(position3);
        vehicleButtons.add(position3);

        position4.setBounds(centerX, 10 + 6 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        position4.addActionListener(this);
        panel.add(position4);
        vehicleButtons.add(position4);

        start.setIcon(startIcon);
        start.setBounds(20, 10, 32, 32);
        start.addActionListener(e->{if(TerminalWatcher.pause==false)
        {
            appendToConsole("PAUZA");

            TerminalWatcher.pauseStartTime=System.currentTimeMillis();
            TerminalWatcher.setPause(true);
        } else if (TerminalWatcher.pause==true)
        {
            appendToConsole("NASTAVAK");

            TerminalWatcher.pauseEndTime=System.currentTimeMillis();
            TerminalWatcher.totalPauseTime+=TerminalWatcher.getTotalPauseTime();
            TerminalWatcher.setPause(false);
        }});

        queueButton.setIcon(queueIcon);
        queueButton.setBounds(432,10,32,32);
        queueButton.addActionListener(e->{VehicleQueueFrame queueFrame=new VehicleQueueFrame();
            Thread queueFrameThread=new Thread(queueFrame);
            queueFrameThread.start();});

        vehiclesWithIncident.setIcon(incident);
        vehiclesWithIncident.setBounds(390,10,32,32);
        vehiclesWithIncident.setEnabled(false);
        vehiclesWithIncident.addActionListener(e->{new ReportsWindow();});

        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20,470,445,200);
        consoleTextArea.setEditable(false);

        this.setTitle("Border Crossing Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,730);
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.setVisible(true);
        this.add(panel);
        this.add(start);
        this.add(queueButton);
        this.add(scrollPane);
        this.add(vehiclesWithIncident);
    }
    public static void appendToConsole(String text)
    {
        consoleTextArea.append(text + "\n");
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
    }

    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < vehicleButtons.size(); i++)
        {
            if (e.getSource() == vehicleButtons.get(i))
            {
                if(BorderCrossing.vehicleQueue[i]!=null)
                {
                    Vehicle currentVehicle = BorderCrossing.vehicleQueue[i];

                    JScrollPane scrollPane = new JScrollPane(new JTextArea(currentVehicle.toString()));
                    scrollPane.setPreferredSize(new Dimension(600, 100));

                    JOptionPane.showMessageDialog(null, scrollPane, "Informacije o vozilu na poziciji " + (i + 1), JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        for(int i=0;i<policeButtons.size();i++)
        {

            if (e.getSource() == policeButtons.get(i))
            {
                if(BorderCrossing.policeTerminals.get(i).getCurrentVehicle()!=null)
                {
                    Vehicle currentVehicle = BorderCrossing.policeTerminals.get(i).getCurrentVehicle();

                    JScrollPane scrollPane = new JScrollPane(new JTextArea(currentVehicle.toString()));
                    scrollPane.setPreferredSize(new Dimension(600, 100));
                    JOptionPane.showMessageDialog(null, scrollPane, "Informacije o vozilu na policijskom terminalu: " + (i + 1), JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        for(int i=0;i<customsButtons.size();i++)
        {
            if(e.getSource()==customsButtons.get(i))
            {
                if(BorderCrossing.customsTerminals.get(i).getCurrentVehicle()!=null)
                {
                    Vehicle currentVehicle = BorderCrossing.policeTerminals.get(i).getCurrentVehicle();

                    JScrollPane scrollPane = new JScrollPane(new JTextArea(currentVehicle.toString()));
                    scrollPane.setPreferredSize(new Dimension(600, 100));

                    JOptionPane.showMessageDialog(null, scrollPane, "Informacije o vozilu na carinskom terminalu: " + (i + 1), JOptionPane.INFORMATION_MESSAGE);
                }
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
            Logger.getLogger(Vehicle.class.getName()).log(Level.WARNING,"Thread interupted! BorderCrossingWindow.");

        }

        while(BorderCrossing.simulationRunning)
        {
            refreshVehicleButtons();
            refreshPoliceButtons();
            refreshCustomsButtons();
            refreshTime();
        }
        vehiclesWithIncident.setEnabled(true);
        start.setEnabled(false);
    }

}
