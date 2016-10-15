package com.geography.location.location.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.geography.location.location.model.LocationDetails;
import com.geography.location.location.adapter.LocationDetailsAdapter;
import com.geography.location.location.R;
import com.geography.location.location.database.DatabaseHandler;
import com.geography.location.location.service.LocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Venkat_Chenna on 15-10-2016.
 */

/* -Launcher Activity
   -Permissions checks and background service for location started from this activity
 */
public class LocationActivity extends AppCompatActivity {

    private  String TAG=LocationActivity.class.getSimpleName();
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION=21,MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=22;
    private RecyclerView recyclerView;
    private LocationDetailsAdapter mAdapter;
    private List<LocationDetails> locationDetailsList=new ArrayList<LocationDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_details_list);


        fetchPermissionsFromUser();
        enableLocationServices();

        recyclerView= (RecyclerView) findViewById(R.id.location_details_recycler_view);
        DatabaseHandler databaseHandler=new DatabaseHandler(getApplicationContext());
        locationDetailsList=databaseHandler.getAllLocationDetails();


        /**
           settings adapter to recyclerview
         * */
        mAdapter = new LocationDetailsAdapter(locationDetailsList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        /**
         * start service based on it's current state
         */
        if(isMyServiceRunning(LocationService.class)) {
            Intent service = new Intent(LocationActivity.this, LocationService.class);
            startService(service);
        }
    }


    /**
     *
     * @param serviceClass
     * @return true  - service is not running in background
     *         false - service is already running in background
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fetch permissions from user dynamically.
     */
    private void fetchPermissionsFromUser()
    {
        Log.d(TAG,"in fetch permisssion s");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"in fetch permisssion s ACCESS_COARSE_LOCATION");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d(TAG,"in fetch permisssion s ACCESS_COARSE_LOCATION show request");

            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG,"in fetch permisssion s ACCESS_COARSE_LOCATION with return request");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"in fetch permisssion s ACCESS_FINE_LOCATION");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG,"in fetch permisssion s ACCESS_FINE_LOCATION erequest");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Log.d(TAG,"in fetch permisssion s ACCESS_FINE_LOCATION with return requet");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }




    }


    public void enableLocationServices()
    {
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getApplicationContext().getResources().getString(R.string.location_not_enabled));
            dialog.setPositiveButton(getApplicationContext().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getApplicationContext().startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getApplicationContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }
}
