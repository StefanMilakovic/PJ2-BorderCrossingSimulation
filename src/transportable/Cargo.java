package transportable;

import interfaces.Transportable;
import vehicles.Bus;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public final class Cargo implements Serializable, Transportable
{
    public static Handler handler;

    static
    {
        try {
            handler = new FileHandler(System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Cargo.log");
            Logger.getLogger(Bus.class.getName()).addHandler(handler);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private double declaredCargoWeight;
    private double actualCargoWeight;
    private boolean documentationNeeded=false;
    private CargoDocumentation cargoDocumentation=null;

    public Cargo()
    {
        Random rand=new Random();
        this.declaredCargoWeight = rand.nextDouble(20);
        this.actualCargoWeight = this.declaredCargoWeight;

        if (rand.nextDouble() <= 0.2)
        {
            this.actualCargoWeight *= 1.3;
        }
        this.documentationNeeded = (rand.nextDouble() < 0.5);
    }

    @Override
    public boolean canPassCustomsControl()
    {
        return actualCargoWeight<=declaredCargoWeight;
    }
    public double getDeclaredCargoWeight()
    {
        return declaredCargoWeight;
    }
    public void setDeclaredCargoWeight(double declaredCargoWeight)
    {
        this.declaredCargoWeight = declaredCargoWeight;
    }

    public double getActualCargoWeight()
    {
        return actualCargoWeight;
    }

    public void setActualCargoWeight(double actualCargoWeight)
    {
        this.actualCargoWeight = actualCargoWeight;
    }

    public boolean isDocumentationNeeded()
    {
        return documentationNeeded;
    }

    public void setDocumentationNeeded(boolean documentationNeeded)
    {
        this.documentationNeeded = documentationNeeded;
    }

    public CargoDocumentation getCargoDocumentation()
    {
        return cargoDocumentation;
    }

    public void setCargoDocumentation(CargoDocumentation cargoDocumentation)
    {
        this.cargoDocumentation = cargoDocumentation;
    }

}
