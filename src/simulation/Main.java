package simulation;

import gui.BorderCrossingWindow;

public class Main
{
    public static void main(String[] args)
    {

        BorderCrossingWindow newWindow=new BorderCrossingWindow();
        Thread newWindowThread = new Thread(newWindow);
        newWindowThread.start();

        BorderCrossing borderCrossing=new BorderCrossing(35,5,10);

    }
}
