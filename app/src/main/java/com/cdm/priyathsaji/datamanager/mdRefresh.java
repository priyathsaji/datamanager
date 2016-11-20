package com.cdm.priyathsaji.datamanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by PRIYATH SAJI on 16-11-2016.
 */
public class mdRefresh extends Service {
    public int onStartCommand(Intent intent, int flags, int startId) {

        mobileDataService.refresh();
        Toast.makeText(this,"Refreshed",Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
