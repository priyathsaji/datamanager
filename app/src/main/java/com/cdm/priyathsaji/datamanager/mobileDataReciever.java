package com.cdm.priyathsaji.datamanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by PRIYATH SAJI on 16-11-2016.
 */
public class mobileDataReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager manager1 = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(manager1.isWifiEnabled()){
            Intent intent1 = new Intent(context,wifiService.class);
            Intent intent2 = new Intent(context,mobileDataService.class);
            Intent intent3 = new Intent(context,initialisationService.class);
            context.stopService(intent1);
            context.stopService(intent2);
            context.stopService(intent3);;
            context.startService(intent3);
            context.startService(intent1);

        }else if((info!=null)&&(info.isConnected())){
            Intent intent1 = new Intent(context,wifiService.class);
            Intent intent2 = new Intent(context,mobileDataService.class);
            Intent intent3 = new Intent(context,initialisationService.class);
            context.stopService(intent1);
            context.stopService(intent2);
            context.stopService(intent3);
            context.startService(intent3);
            context.startService(intent2);


        }else{

            Intent intent1 = new Intent(context, mobileDataService.class);
            context.stopService(intent1);

            Intent intent2 = new Intent(context,wifiService.class);
            context.stopService(intent2);


        }
    }
}
