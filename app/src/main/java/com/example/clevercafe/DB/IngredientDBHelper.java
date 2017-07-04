package com.example.clevercafe.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chudofom on 14.07.16.
 */
public class IngredientDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "smartcafe.db";
    public static final String INGREDIENT_C_TABLE = "ingredientCategory";
    public static final String ID = "_id";
    public static final String CATEGORY_NAME = "name";

    public static final String INGREDIENT_TABLE = "ingredients";
    public static final String CATEGORY_ID = "categoryId";
    public static final String INGREDIENT_NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String UNITS = "units";

    private static final String CREATE_INGREDIENT_C_TABLE =
            "CREATE TABLE " + INGREDIENT_C_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_NAME + " TEXT)";
    private static final String CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + INGREDIENT_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_ID + " INTEGER, " + INGREDIENT_NAME + " TEXT, " +
                    QUANTITY + " REAL, " + UNITS + " TEXT)";

    private static final String INGR_C_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + INGREDIENT_C_TABLE;

    private static final String INGR_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + INGREDIENT_TABLE;

    public IngredientDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENT_C_TABLE);
        db.execSQL(CREATE_INGREDIENT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(INGR_C_DEL_ENTRIES);
        db.execSQL(INGR_DEL_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}