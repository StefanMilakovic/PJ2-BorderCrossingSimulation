package enums;
import java.util.Random;

public enum Names
{

    Ana, Marko, Jelena, Nikola, Milica, Aleksandar, Ivana, Stefan, Marija, Dusan, Milena, Nenad,
    Tamara, Vladimir, Katarina, Petar, Anamarija, Jovana, Bojan, Sofija, Uros, Aleksa, Kristina,
    Vuk, Sanja, Milos, Maja, Djordje, Nemanja, Mila, Veljko, Danica, Vasilija, Nevena, Lazar,
    Jovanka, Milan, Sonja, Igor, Andjela, Ognjen, Natalija, Vukasin, Dragana, Sava, Aleksandra,
    Aleksandrina, Zoran, Slavica, Dimitrije, Marijana, Luka, Dragica, Ljubisa, Jovica, Marina,
    Tanja, Branislav, Snezana, Vojin, Jelisaveta, Zdravko, Olivera, Aleksandrija, Branislava,
    Goran, Radmila, Dejan, Bojana, Miodrag, Anica, Predrag, Draginja, Miroslav, Radomir, Lidija,
    Vlada, Tatjana, Bozidar, Ruzica, Danilo, Biljana, Mladen, Zorica, Dragan, Natasa, Slobodan,
    Milovan, Jelica,  Valentina, Sreten,  Radoslav, Zorana, Vladan;

    private static final Random random = new Random();
    private static final Names[] values = Names.values();

    public static String getRandomName()
    {
        return values[random.nextInt(values.length)].toString();
    }

}
