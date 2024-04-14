package enums;

import java.util.Random;

public enum LastNames
{
    Jovanovic, Petrovic, Markovic, Nikolic, Popovic, DJordjevic, Stojanovic, Ilic, Kovacevic, Pavlovic, Simic,
    Miljkovic, Jankovic, Todorovic, Matic, Tomic, Kostic, Stevanovic, Ristic, Milic, Vukovic, Lukic, Randjelovic,
    Gajic, Mladenovic, Milosevic, Marinkovic, DJukic, Nenadovic, Jelic, Vasic, Arsic, Mitic, Peric, Milanovic,
    Stankovic, Ciric, Radosavljevic, Jaksic, Knezevic, Cvetkovic, Rankovic, Milovanovic, Zdravkovic, Savic, Jevtic,
    Maksimovic, Lazic, Radovanovic, Jovancic;

    private static final Random random = new Random();
    private static final LastNames[] values = LastNames.values();

    public static String getRandomLastName()
    {
        return values[random.nextInt(values.length)].toString();
    }
}
