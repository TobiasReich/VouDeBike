package br.dayanelima.voudebike.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.dayanelima.voudebike.data.Bike;

/**
 * Created by T on 06.05.2017. */
public class DataBaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = DataBaseHelper.class.getSimpleName();


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "HealthTracker.db";

    // Table Names
    private static final String TABLE_BIKES = "bike";
    private static final String TABLE_CUSTOMERS = "customer";
    private static final String TABLE_BOOKINGS = "booking";

    // Common column names
    private static final String KEY_ID = "id";

    // MEDICINES Table - column names
    private static final String KEY_BIKE_NAME = "name";
    private static final String KEY_BIKE_DESCRIPTION = "description";
    private static final String KEY_BIKE_TYPE = "type";
    private static final String KEY_BIKE_PRICE = "price";


    // Table Create Statements
    // Medicine table create statement
    private static final String CREATE_TABLE_BIKES
            = "CREATE TABLE " + TABLE_BIKES + " ("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BIKE_NAME + " TEXT,"
            + KEY_BIKE_DESCRIPTION + " TEXT,"
            + KEY_BIKE_TYPE + " INTEGER,"
            + KEY_BIKE_PRICE + " INTEGER)";


    public SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DataBaseHelper -> Constructor");
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "OnCreate -> Creating database");
        db.execSQL(CREATE_TABLE_BIKES);
        //db.execSQL(CREATE_TABLE_CUSTOMERS);
        //db.execSQL(CREATE_TABLE_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    /* ---------------------- END OF BIKES ---------------------- */

    /** Write a Bike object to the database
     *
     * @param bike
     * @return long the row id inserted
     */
    public long insertBike(Bike bike) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIKE_NAME, bike.name);
        values.put(KEY_BIKE_DESCRIPTION, bike.description);
        values.put(KEY_BIKE_TYPE, bike.type);
        values.put(KEY_BIKE_PRICE, bike.price);

        long row = db.insert(TABLE_BIKES, null, values);

        Log.i(TAG, "Adding medicine to database: " + bike + " @column: " + row);

        return row;
    }

    /** get single Bike from the database.
     *
     * This is useful for loading only one element in order to show it (or edit etc.)
     *
     * @param bikeID id of the bike to get
     * @return Bike */
    public Bike getBike(long bikeID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BIKES + " WHERE " + KEY_ID + " = " + bikeID;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return getBikeFromCursor(c);
    }

    private Bike getBikeFromCursor(Cursor c) {
        Bike bike = new Bike();
        bike.id = c.getInt(c.getColumnIndex(KEY_ID));
        bike.name = c.getString(c.getColumnIndex(KEY_BIKE_NAME));
        bike.description = c.getString(c.getColumnIndex(KEY_BIKE_DESCRIPTION));
        bike.type = c.getString(c.getColumnIndex(KEY_BIKE_TYPE));
        bike.price = c.getInt(c.getColumnIndex(KEY_BIKE_PRICE));

        return bike;
    }

    /** getting all bikes
     *
     * @return List of bikes */
    public List<Bike> getAllBikes(boolean orderByName) {
        List<Bike> bikesList = new ArrayList<>();

        // TODO: The sorting is just for showing how this works.
        // This might be useful for CUSTOMER sorting or
        // Sorting Bookings by date etc.

        String selectQuery;
        if (orderByName)
            selectQuery = "SELECT * FROM " + TABLE_BIKES + " ORDER BY " + KEY_BIKE_NAME + " ASC";
        else
            selectQuery = "SELECT * FROM " + TABLE_BIKES;

        Log.i(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                bikesList.add(getBikeFromCursor(c));
            } while (c.moveToNext());
        }

        return bikesList;
    }

    /**
     * Updating a bike entry
     *
     * @param bike
     * @return int id of the row that was updated
     */
    public int updateBike(Bike bike) {
        ContentValues values = new ContentValues();
        values.put(KEY_BIKE_NAME, bike.name);
        values.put(KEY_BIKE_DESCRIPTION, bike.description);
        values.put(KEY_BIKE_TYPE, bike.type);
        values.put(KEY_BIKE_PRICE, bike.price);

        // updating row
        return db.update(TABLE_BIKES, values, KEY_ID + " = ?", new String[] { String.valueOf(bike.id) });
    }

    /** Delete a bike from the list
     *
     * @param id the ID of the bike to delete */
    public void deleteBikeFromList(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIKES, KEY_ID + " = ?", new String[] { String.valueOf(id) });
    }


    /* ---------------------- END OF BIKES ---------------------- */

    /* ---------------------- CUSTOMERS ---------------------- */

    /* ---------------------- END OF CUSTOMERS ---------------------- */


}
