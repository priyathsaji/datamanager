package com.cdm.priyathsaji.datamanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * Created by PRIYATH SAJI on 17-11-2016.
 */
public class initialisationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    dataHolder data = new dataHolder();
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConnectivityManager manager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager manager1 = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(c.getTime());
        data.date = formattedDate;
        data.mDownloaded = TrafficStats.getTotalRxBytes();
        data.mUploaded = TrafficStats.getTotalTxBytes();
        if(manager1.isWifiEnabled())
            data.wUploaded = 0;
        else if((info!=null)&&(info.isConnected()))
            data.wUploaded = 1;

        data.wDownloaded = 0;

        try {
            FileOutputStream out = null;
            out = this.openFileOutput("indata",MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(data);
            oos.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent1 = new Intent(this,initialisationService.class);
        stopService(intent1);
        return START_STICKY;
    }



}
