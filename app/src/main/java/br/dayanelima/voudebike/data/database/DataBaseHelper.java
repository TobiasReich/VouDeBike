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
import br.dayanelima.voudebike.data.Client;

/**
 * Created by T on 06.05.2017. */
public class DataBaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = DataBaseHelper.class.getSimpleName();


    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    public static final String DATABASE_NAME = "VouDeBike.db";

    // Table Names
    private static final String TABLE_BIKES = "bike";
    private static final String TABLE_CLIENTS = "client";
    private static final String TABLE_BOOKINGS = "booking";

    // Common column names
    private static final String KEY_ID = "id";

    // MEDICINES Table - column names
    private static final String KEY_BIKE_NAME = "name";
    private static final String KEY_BIKE_DESCRIPTION = "description";
    private static final String KEY_BIKE_TYPE = "type";
    private static final String KEY_BIKE_PRICE = "price";
    private static final String KEY_BIKE_COLOUR = "colour";


    private static final String KEY_CLIENT_NAME = "name";

    // Table Create Statements
    private static final String CREATE_TABLE_BIKES
            = "CREATE TABLE " + TABLE_BIKES + " ("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BIKE_NAME + " TEXT,"
            + KEY_BIKE_DESCRIPTION + " TEXT,"
            + KEY_BIKE_TYPE + " TEXT,"
            + KEY_BIKE_COLOUR + " TEXT,"
            + KEY_BIKE_PRICE + " INTEGER)";


    private static final String CREATE_TABLE_CLIENT
            = "CREATE TABLE " + TABLE_CLIENTS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CLIENT_NAME + " TEXT)";



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DataBaseHelper -> Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "OnCreate -> Creating database");
        db.execSQL(CREATE_TABLE_BIKES);
        db.execSQL(CREATE_TABLE_CLIENT);
        //db.execSQL(CREATE_TABLE_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        Log.d(TAG, "Update database from " + oldVersion + " to " + newVersion);
        // create new tables
        onCreate(db);
    }

    // closing database
    private void closeDB(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    /* ---------------------- BIKES ---------------------- */

    /** Write a Bike object to the database
     *
     * @param bike
     * @return long the row id inserted
     */
    public void insertBike(Bike bike, IDataBaseWriteCallback  callback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIKE_NAME, bike.name);
        values.put(KEY_BIKE_DESCRIPTION, bike.description);
        values.put(KEY_BIKE_TYPE, bike.type);
        values.put(KEY_BIKE_PRICE, bike.price);
        values.put(KEY_BIKE_COLOUR, bike.color);

        long row = db.insert(TABLE_BIKES, null, values);

        Log.i(TAG, "Adding bike to database: " + bike + " @column: " + row);

        closeDB(db);

        if (callback != null)
            callback.dataWritten(row != -1);
    }

    /** get single Bike from the database.
     *
     * This is useful for loading only one element in order to show it (or edit etc.)
     *
     * @param bikeID id of the bike to get
     * @return Bike */
    public Bike getBike(long bikeID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_BIKES + " WHERE " + KEY_ID + " = " + bikeID;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Bike bike = getBikeFromCursor(c);

        closeDB(db);

        return bike;
    }


    /**
     * | ID | NAME | DESC | TYPE | PRICE | COLOR
     *
     * @param c
     * @return
     */
    private Bike getBikeFromCursor(Cursor c) {
        Bike bike = new Bike();
        try {
            bike.id = c.getInt(c.getColumnIndex(KEY_ID));
            bike.name = c.getString(c.getColumnIndex(KEY_BIKE_NAME));
            bike.description = c.getString(c.getColumnIndex(KEY_BIKE_DESCRIPTION));
            bike.type = c.getString(c.getColumnIndex(KEY_BIKE_TYPE));
            bike.price = c.getInt(c.getColumnIndex(KEY_BIKE_PRICE));
            bike.color = c.getString(c.getColumnIndex(KEY_BIKE_COLOUR));
        } catch (Exception e) {
            Log.d(TAG, "Error getting Bike from Database");
        }
        return bike;
    }

    /** getting all bikes
     *
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     *
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

        closeDB(db);

        return bikesList;
    }

    /**
     * Updating a bike entry
     *
     * @param bike
     * @return int id of the row that was updated
     */
    public void updateBike(Bike bike, IDataBaseWriteCallback  callback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIKE_NAME, bike.name);
        values.put(KEY_BIKE_DESCRIPTION, bike.description);
        values.put(KEY_BIKE_TYPE, bike.type);
        values.put(KEY_BIKE_PRICE, bike.price);
        values.put(KEY_BIKE_COLOUR, bike.color);

        // updating row
        int row = db.update(TABLE_BIKES, values,
                KEY_ID + " = ?",
                new String[] { String.valueOf(bike.id)});

        closeDB(db);

        if (callback != null)
            callback.dataWritten(row != -1);
    }

    /** Delete a bike from the list
     *
     * @param id the ID of the bike to delete */
    public void deleteBikeFromList(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIKES, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        closeDB(db);
    }


    /* ---------------------- END OF BIKES ---------------------- */



    /* ---------------------- CLIENTS ---------------------- */

    /** Write a Bike object to the database
     *
     * @param client
     * @return long the row id inserted
     */
    public void insertClient(Client client, IDataBaseWriteCallback  callback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_NAME, client.name);

        long row = db.insert(TABLE_CLIENTS, null, values);

        Log.i(TAG, "Adding client to database: " + client + " @column: " + row);

        closeDB(db);

        if (callback != null)
            callback.dataWritten(row != -1);
    }

    /** get single client from the database.
     *
     * This is useful for loading only one element in order to show it (or edit etc.)
     *
     * @param clientID id of the bike to get
     * @return Bike */
    public Client getClient(long clientID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CLIENTS + " WHERE " + KEY_ID + " = " + clientID;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Client client = getClientFromCursor(c);

        closeDB(db);

        return client;
    }


    /**
     * | ID | NAME | DESC | TYPE | PRICE |
     *
     * @param c
     * @return
     */
    private Client getClientFromCursor(Cursor c) {
        Client client = new Client();
        try {
            client.id = c.getInt(c.getColumnIndex(KEY_ID));
            client.name = c.getString(c.getColumnIndex(KEY_CLIENT_NAME));

        } catch (Exception e) {
            Log.d(TAG, "Error getting Bike from Database");
        }
        return client;
    }

    /** getting all bikes
     *
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     * | ID | NAME | DESC | TYPE | PRICE |
     *
     *
     * @return List of bikes */
    public List<Client> getAllClients(boolean orderByName) {
        List<Client> clientList = new ArrayList<>();

        // TODO: The sorting is just for showing how this works.
        // This might be useful for CUSTOMER sorting or
        // Sorting Bookings by date etc.

        String selectQuery;
        if (orderByName)
            selectQuery = "SELECT * FROM " + TABLE_CLIENTS + " ORDER BY " + KEY_CLIENT_NAME + " ASC";
        else
            selectQuery = "SELECT * FROM " + TABLE_CLIENTS;

        Log.i(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                clientList.add(getClientFromCursor(c));
            } while (c.moveToNext());
        }

        closeDB(db);

        return clientList;
    }

    /**
     * Updating a bike entry
     *
     * @param client
     * @return int id of the row that was updated
     */
    public void updateClient(Client client, IDataBaseWriteCallback  callback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_NAME, client.name);


        // updating row
        int row = db.update(TABLE_CLIENTS, values,
                KEY_ID + " = ?",
                new String[] { String.valueOf(client.id)});

        closeDB(db);

        if (callback != null)
            callback.dataWritten(row != -1);
    }

    /** Delete a bike from the list
     *
     * @param id the ID of the bike to delete */
    public void deleteClientFromList(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        closeDB(db);
    }

    /* ---------------------- END OF CLIENTS ---------------------- */


}
