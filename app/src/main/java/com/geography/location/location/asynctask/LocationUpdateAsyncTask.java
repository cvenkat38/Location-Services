package com.geography.location.location.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.geography.location.location.database.DatabaseHandler;
import com.geography.location.location.model.LocationDetails;
import com.geography.location.location.service.GPSTracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Admin on 15-10-2016.
 * Asynctask to fetch longitude,latitude.
 */

public class LocationUpdateAsyncTask extends AsyncTask<String,Void,String> {

    private Context context;

    private String TAG=LocationUpdateAsyncTask.class.getSimpleName();
    public LocationUpdateAsyncTask(Context context)
    {
        this.context=context;
    }
    @Override
    protected String doInBackground(String... params) {

        return null;
    }

    @Override
    protected void onPostExecute(String s) {


        GPSTracker gpsTracker=new GPSTracker(context);

        /**
         *  add data to database
         */
        DatabaseHandler databaseHandler=new DatabaseHandler(context);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        LocationDetails locationDetails=new LocationDetails();
        locationDetails.setTime(datetime);
        locationDetails.setLongitude(String.valueOf(gpsTracker.getLongitude()));
        locationDetails.setLatitude(String.valueOf(gpsTracker.getLatitude()));
        databaseHandler.addLocation(locationDetails);


    }
}
