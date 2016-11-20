package com.cdm.priyathsaji.datamanager;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by PRIYATH SAJI on 17-11-2016.
 */
public class addingService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    dataHolder initialData,finalData;
    ArrayList<dataHolder> Data = new ArrayList<>();
    ArrayList<dataHolder> newData = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            FileInputStream in = openFileInput("indata");
            ObjectInputStream ois = new ObjectInputStream(in);
            initialData = (dataHolder) ois.readObject();
            ois.close();
            in.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        Data = getDataHolder();
        if (Data.size() == 0) {
            dataHolder d = new dataHolder();
            d.date = initialData.date;
            d.mUploaded = 0;
            d.wUploaded = 0;
            d.wDownloaded = 0;
            d.mUploaded = 0;
            Data.add(d);
        }

        finalData = Data.get(Data.size() - 1);

        if(!Objects.equals(finalData.date,initialData.date)){
            dataHolder d = new dataHolder();
            d.date = initialData.date;
            d.mUploaded = 0;
            d.wUploaded = 0;
            d.wDownloaded = 0;
            d.mUploaded = 0;
            Data.add(d);

            finalData = Data.get(Data.size()-1);

        }

        finalData.date = initialData.date;
        if (initialData.wUploaded == 0) {
            finalData.date = initialData.date;
            finalData.wDownloaded += (TrafficStats.getTotalRxBytes()-initialData.mDownloaded);
            finalData.wUploaded += (TrafficStats.getTotalTxBytes() - initialData.mUploaded);


        } else if(initialData.wUploaded == 1){
            finalData.date = initialData.date;
            finalData.mDownloaded += (TrafficStats.getTotalRxBytes()-initialData.mDownloaded);
            finalData.mUploaded += (TrafficStats.getTotalTxBytes() - initialData.mUploaded);

        }

        mupdateDataHolder(finalData);


        Intent intent1 = new Intent(this,addingService.class);
        stopService(intent1);
        return START_STICKY;
    }

    public void mupdateDataHolder(dataHolder d){
        newData.clear();
        for(int i = 0;i<Data.size();i++)
            newData.add(Data.get(i));
        Data.clear();
        for(int i = 0;i<newData.size()-1;i++)
            Data.add(newData.get(i));
        Data.add(d);

        putDataHolder();
    }
    private void putDataHolder(){

        try {
            if(Data.size()> 10){
                ArrayList<dataHolder> temp = new ArrayList<>();
                for(int i = 0; i<Data.size();i++){
                    temp.add(Data.get(i));
                }
                Data.clear();
                for(int i=1;i<temp.size();i++){
                    Data.add(temp.get(i));
                }
            }
            File f = new File("data");
            f.delete();
            FileOutputStream out = this.openFileOutput("data",MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(Data);
            oos.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<dataHolder> getDataHolder(){
        ArrayList<dataHolder> data = new ArrayList<>();
        try {
            FileInputStream in = openFileInput("data");
            ObjectInputStream ois = new ObjectInputStream(in);

            data = (ArrayList<dataHolder>) ois.readObject();
            ois.close();
            in.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return data;
//;
    }

}
