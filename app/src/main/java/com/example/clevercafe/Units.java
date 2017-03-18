package com.example.clevercafe;

/**
 * Created by Chudofom on 15.11.16.
 */

public class Units {
    public static String milliliter = "мл";
    public static String liter = "л";
    public static String kilogram = "кг";
    public static String gram = "г";
    public static String count = "шт";
    public static String[] array = {milliliter, liter, kilogram, gram, count};

    public static int idOfUnit(String nameOfUnit)
    {
        for (int i = 0; i< array.length; i++)
        {
            if (nameOfUnit.equals(array[i]))
            {
                return i;
            }
        }
        return -1;
    }
}
