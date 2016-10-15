package com.geography.location.location.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


import com.geography.location.location.asynctask.LocationUpdateAsyncTask;


/**
 * Created by Venkat_chenna on 15-10-2016.
 *
 */

public class LocationService extends Service {

    /*time difference is in milliseconds*/
    private int TIME_DIFFERENCE_TO_RESTART_SERVICE = 120000;
    private String TAG=LocationService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"in satrt ");
        new LocationUpdateAsyncTask(getApplicationContext()).execute();
        startThread();
        return START_STICKY;
    }

    /**
     * To start a alaram for time specified.
     */
   public void startThread() {

        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + TIME_DIFFERENCE_TO_RESTART_SERVICE, restartServicePI);

    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d(TAG,"in create");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"in destory ");
    }
}