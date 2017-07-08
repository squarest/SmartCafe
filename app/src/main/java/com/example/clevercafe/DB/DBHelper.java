package com.example.clevercafe.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chudofom on 14.07.16.
 */
public class DBHelper extends SQLiteOpenHelper {
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

    public static final String PRODUCT_C_TABLE = "productCategory";

    public static final String PRODUCT_TABLE = "products";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_COST = "cost";

    public static final String PRODUCT_CNS_TABLE = "productsConsist";
    public static final String PRODUCT_ID = "productId";
    public static final String INGREDIENT_ID = "ingredientId";

    public static final String ORDER_QUEUE_TABLE = "orderQueue";
    public static final String COMPLETE_ORDERS_TABLE = "completeOrders";
    public static final String ORDER_SUM = "sum";
    public static final String DATE_TIME = "dateTime";

    public static final String QUEUE_PRODUCTS_TABLE = "queueProducts";
    public static final String ORDER_PRODUCTS_TABLE = "orderProducts";
    public static final String ORDER_ID = "orderID";

    private static final String CREATE_INGREDIENT_C_TABLE =
            "CREATE TABLE " + INGREDIENT_C_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_NAME + " TEXT)";

    private static final String CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + INGREDIENT_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_ID + " INTEGER, " + INGREDIENT_NAME + " TEXT, " +
                    QUANTITY + " REAL, " + UNITS + " TEXT)";


    private static final String CREATE_PRODUCT_C_TABLE =
            "CREATE TABLE " + PRODUCT_C_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_NAME + " TEXT)";

    private static final String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + PRODUCT_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_ID + " INTEGER, " + PRODUCT_NAME + " TEXT, " +
                    PRODUCT_COST + " REAL," + UNITS + " TEXT)";

    private static final String CREATE_PRODUCT_CNS_TABLE =
            "CREATE TABLE " + PRODUCT_CNS_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCT_ID + " INTEGER, " + INGREDIENT_ID + " INTEGER, " +
                    QUANTITY + " REAL)";


    private static final String CREATE_ORDER_QUEUE_TABLE =
            "CREATE TABLE " + ORDER_QUEUE_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_SUM + " REAL," + DATE_TIME + " INTEGER)";

    private static final String CREATE_COMPL_ORD_TABLE =
            "CREATE TABLE " + COMPLETE_ORDERS_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_SUM + " REAL," + DATE_TIME + " INTEGER)";

    private static final String CREATE_QUEUE_PROD_TABLE =
            "CREATE TABLE " + QUEUE_PRODUCTS_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_ID + " INTEGER, " + PRODUCT_ID + " INTEGER, " +
                    QUANTITY + " REAL)";

    private static final String CREATE_ORDER_PROD_TABLE =
            "CREATE TABLE " + ORDER_PRODUCTS_TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_ID + " INTEGER, " + PRODUCT_ID + " INTEGER, " +
                    QUANTITY + " REAL)";


    private static final String INGR_C_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + INGREDIENT_C_TABLE;

    private static final String INGR_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + INGREDIENT_TABLE;


    private static final String PROD_C_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + PRODUCT_C_TABLE;

    private static final String PROD_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + PRODUCT_TABLE;

    private static final String PROD_CNS_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + PRODUCT_CNS_TABLE;

    private static final String ORDER_QUEUE_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + ORDER_QUEUE_TABLE;

    private static final String COMPL_ORD_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + COMPLETE_ORDERS_TABLE;

    private static final String QUEUE_PROD_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + QUEUE_PRODUCTS_TABLE;

    private static final String ORDER_PROD_DEL_ENTRIES =
            "DROP TABLE IF EXISTS " + ORDER_PRODUCTS_TABLE;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENT_C_TABLE);
        db.execSQL(CREATE_INGREDIENT_TABLE);

        db.execSQL(CREATE_PRODUCT_C_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_PRODUCT_CNS_TABLE);

        db.execSQL(CREATE_ORDER_PROD_TABLE);
        db.execSQL(CREATE_COMPL_ORD_TABLE);
        db.execSQL(CREATE_QUEUE_PROD_TABLE);
        db.execSQL(CREATE_ORDER_QUEUE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(INGR_C_DEL_ENTRIES);
        db.execSQL(INGR_DEL_ENTRIES);

        db.execSQL(PROD_C_DEL_ENTRIES);
        db.execSQL(PROD_DEL_ENTRIES);
        db.execSQL(PROD_CNS_DEL_ENTRIES);

        db.execSQL(ORDER_QUEUE_DEL_ENTRIES);
        db.execSQL(COMPL_ORD_DEL_ENTRIES);
        db.execSQL(QUEUE_PROD_DEL_ENTRIES);
        db.execSQL(ORDER_PROD_DEL_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}