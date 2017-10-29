package com.example.clevercafe.utils;

/**
 * Created by Chudofom on 15.11.16.
 */

public class Units {
    public static String milliliter = "мл";
    public static String liter = "л";
    public static String kilogram = "кг";
    public static String gram = "г";
    public static String count = "шт";
    public static String[] units = {milliliter, liter, kilogram, gram, count};

    public static int idOfUnit(String nameOfUnit)
    {
        for (int i = 0; i< units.length; i++)
        {
            if (nameOfUnit.equals(units[i]))
            {
                return i;
            }
        }
        return -1;
    }
}
