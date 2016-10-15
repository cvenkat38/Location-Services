package com.geography.location.location.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.geography.location.location.model.LocationDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 15-10-2016.
 * creating Database.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private String TAG=DatabaseHandler.class.getSimpleName();

    // Database Name
    private static final String DATABASE_NAME = "locationmanager";

    // Contacts table name
    private static final String TABLE_LOCATION_DETAILS= "locationdetails";

    // Location Details Table Columns names

    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_TIME="time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"in db create ");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATION_DETAILS + "("
                + KEY_TIME + " TEXT PRIMARY KEY," + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT" +  ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        Log.d(TAG,"in db upgrade ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_DETAILS);

        // Create tables again
        onCreate(db);
    }
    // Adding new Location
    public void addLocation(LocationDetails locationDetails)
    {
        Log.d(TAG,"in db add location ");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME,locationDetails.getTime());
        values.put(KEY_LATITUDE, locationDetails.getLatitude()); // Location Latitude
        values.put(KEY_LONGITUDE, locationDetails.getLongitude()); // Loaction Longitude

        // Inserting Row
        db.insert(TABLE_LOCATION_DETAILS, null, values);
        db.close(); // Closing database connection
    }
    // fetching single Location details at particular time
    public LocationDetails getLocationDetails(String time)
    {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATION_DETAILS, new String[] { KEY_TIME,
                        KEY_LATITUDE, KEY_LONGITUDE }, KEY_TIME + "=?",
                new String[] { String.valueOf(time) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationDetails locationDetails = new LocationDetails(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        // return locationDetails
        return locationDetails;
    }

    // Getting All Location Details
    public List<LocationDetails> getAllLocationDetails() {
        Log.d(TAG,"in db getAll ");
        List<LocationDetails> locationDetailsList = new ArrayList<LocationDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationDetails locationDetails = new LocationDetails();
                locationDetails.setTime(cursor.getString(0));
                locationDetails.setLatitude(cursor.getString(1));
                locationDetails.setLongitude(cursor.getString(2));
                // Adding Location to list
                locationDetailsList.add(locationDetails);
            } while (cursor.moveToNext());
        }

        // return Location list
        return locationDetailsList;
    }

    // Count number of location details fetched
    public int getCountLocationDetails() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION_DETAILS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Deleting single Location detail
    public void deleteLocationDetail(LocationDetails locationDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION_DETAILS, KEY_TIME + " = ?",
                new String[] { String.valueOf(locationDetails.getTime()) });
        db.close();
    }


}
