package com.cdm.priyathsaji.datamanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by PRIYATH SAJI on 16-11-2016.
 */
public class wdRefresh extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startId) {
        wifiService.refresh();
        Toast.makeText(this,"Wifi counter Refreshed",Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
}
