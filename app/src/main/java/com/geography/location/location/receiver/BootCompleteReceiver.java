package com.geography.location.location.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.geography.location.location.service.LocationService;

/**
 * Created by Admin on 15-10-2016.
 * restart the service for location after restart
 */

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent service = new Intent(context, LocationService.class);
            context.startService(service);

        }
    }



}
