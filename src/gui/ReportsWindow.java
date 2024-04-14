package gui;

import persons.Driver;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ReportsWindow extends JFrame
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "ReportsWindow.log");
            Logger.getLogger(Driver.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static JTextArea  notCrossedTextArea = new JTextArea();
    private static JTextArea crossedTextArea = new JTextArea();
    private ImageIcon incident=new ImageIcon("images/warning.png");
    public ReportsWindow()
    {
        this.setTitle("Reports");
        this.setResizable(false);
        this.setIconImage(incident.getImage());
        this.setSize(500,345);
        this.setLayout(null);

        JLabel notCrossedLabel = new JLabel("Vozila koja nisu prešla granicu: ");
        JLabel crossedLabel = new JLabel("Vozila koja su prešla granicu sa problemima: ");

        notCrossedLabel.setBounds(10, 10, 300, 20);
        crossedLabel.setBounds(10, 160, 300, 20);

        notCrossedTextArea.setEditable(false);
        JScrollPane notCrossedScrollPane = new JScrollPane(notCrossedTextArea);

        notCrossedTextArea.setBounds(10, 40, 460, 100);
        notCrossedScrollPane.setBounds(10, 40, 460, 100);

        crossedTextArea.setEditable(false);
        JScrollPane crossedScrollPane = new JScrollPane(crossedTextArea);

        crossedTextArea.setBounds(10, 190, 460, 100);
        crossedScrollPane.setBounds(10, 190, 460, 100);

        this.add(notCrossedLabel);
        this.add(notCrossedScrollPane);
        this.add(crossedLabel);
        this.add(crossedScrollPane);
        this.setVisible(true);
    }

    public static void appendNotCrossedTextArea(String msg)
    {
        notCrossedTextArea.append(msg + "\n");
        notCrossedTextArea.setCaretPosition(notCrossedTextArea.getDocument().getLength());
    }
    public static void appendCrossedTextArea(String msg)
    {
        crossedTextArea.append(msg + "\n");
        crossedTextArea.setCaretPosition(crossedTextArea.getDocument().getLength());
    }
}
