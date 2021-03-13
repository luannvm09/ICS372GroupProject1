package helpers;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Random;

public class RandomHelper {
    private static Random rand = new Random();

    public static int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static double randomDouble(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

    public static String randomString() {
        byte[] array = new byte[randomInt(5, 25)];
        rand.nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    public static Calendar randomCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, randomInt(1900, 2020));
        calendar.set(Calendar.MONTH, randomInt(1, 12));
        calendar.set(Calendar.DAY_OF_WEEK, randomInt(1, 7));
        return calendar;
    }
}
