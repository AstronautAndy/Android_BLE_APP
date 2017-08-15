package com.example.helpdesk.bluetoothapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ortiza on 7/26/17.
 * This class is used to handle all interactions between the program and the SQL database instantiated
 * in this program. Any activity that wants to load data from or save data to the database will
 * create an instance of this class , loading its context into the object's constructor parameter.
 * The activity can then call the public methods implemented to use the Database.
 */

public class BLEDeviceDbHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String SQL_CREATE_ENTRIES=
                    "CREATE TABLE " + BLEContract.BLEEntry.TABLE_NAME + " (" + BLEContract.BLEEntry.UUID_TITLE + " text PRIMARY KEY, " +
                                                                            BLEContract.BLEEntry.SENIOR_NAME_TITLE + " text, " +
                                                                            BLEContract.BLEEntry.ROOM_NUMBER_TITLE + " text)";
    ;
    private static final String SQL_DELETE_ENTRIES=
                    "DROP TABLE IF EXISTS " + BLEContract.BLEEntry.TABLE_NAME; 

    public BLEDeviceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * This method will call the necessary SQLite function that checks the Database for the given UUID
     * @param UUIDCheck
     * @return
     */
    public boolean checkForValue(String UUIDCheck){
        return false;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
