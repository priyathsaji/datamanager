package com.cdm.priyathsaji.datamanager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by PRIYATH SAJI on 16-11-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<dataHolder> Data;
    private int choice;

    String [] unitArray = new String[]{
            "B", "KB", "MB", "GB"
    };
    String [] Month = new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    String [] week = new String[] {"SUN","MON","TUE","WED","THU","FRI","SAT"};
    int k,b,temp1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView downloaded;
        public TextView uploaded;
        public CardView cview;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            downloaded = (TextView)itemView.findViewById(R.id.downloaded);
            uploaded = (TextView)itemView.findViewById(R.id.Uploaded);
            imageView = (ImageView)itemView.findViewById(R.id.icon);


            cview = (CardView)itemView.findViewById(R.id.card_view);
        }
    }


    public void remove(int i){

        Data.remove(i);

        notifyItemRemoved(i);


    }

    public MyAdapter (ArrayList<dataHolder> myDataSet, int ch){
        Data = myDataSet;
        choice = ch;
        for(int i = 0; i <Data.size();i++) {
            dataHolder dhd = Data.get(i);
            if (choice == 0) {
                if (dhd.mDownloaded == 0) {
                    Data.remove(i);
                }


            } else if (choice == 1) {
                if (dhd.wDownloaded == 0) {
                    Data.remove(i);
                }

            }
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        dataHolder dhd;
        dhd = Data.get(position);
        int k1,k2,a=0,b1=0;

        double downloaded,uploaded;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

        try {
            c.setTime(df.parse(dhd.date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dow = c.get(Calendar.DAY_OF_WEEK);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(c.MONTH);

        //Bitmap b = generateBitmap(dhd.date);
        Bitmap bitmap = Bitmap.createBitmap(200,210, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(120);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(String.valueOf(day),10,90,paint);
        paint.setColor(Color.BLUE);
        paint.setTextSize(70);
        canvas.drawText(week[dow],10,155,paint);
        //holder.imageView.setImageBitmap(bitmap);
        paint.setColor(Color.RED);
        paint.setTextSize(70);
        canvas.drawText(Month[month],10,210,paint);
        holder.imageView.setImageBitmap(bitmap);

        if (choice == 1) {
            unitDecider(dhd.wDownloaded);
            long down = dhd.wDownloaded/k;
            //down = Math.round((down*100)/100);
            holder.downloaded.setText(String.valueOf(down)+"."+temp1+unitArray[b]);

            unitDecider(dhd.wUploaded);
            long up = dhd.wUploaded/k;
            //up = Math.round((up*100)/100);
            holder.uploaded.setText(String.valueOf(up)+"."+temp1+" "+unitArray[b]);

        }else if(choice == 0){

            unitDecider(dhd.mDownloaded);
            long down = dhd.mDownloaded/k;
            //down = Math.round((down*100)/100);
            holder.downloaded.setText(String.valueOf(down)+"."+temp1+" "+unitArray[b]);

            unitDecider(dhd.mUploaded);
            long up = dhd.mUploaded/k;
            //up = Math.round((up*100)/100);
            holder.uploaded.setText(String.valueOf(up)+"."+temp1+" "+unitArray[b]);


        }
        //holder.tview2.setText((int) dhd.mDownloaded);
        //holder.tview2.setText((int) dhd.mUploaded);
        //holder.tview2.setText((int) dhd.wDownloaded);
        //holder.tview2.setText((int) dhd.wUploaded);

    }

    void unitDecider(long num){
        int temp2=0;

        if(num < 1024){
            temp1 = (int)(num/1);
            temp2 = (int)(num/0.01);
            temp1 = temp2 - (temp1*100);
            k = 1;
            b=0;
        } else if (num > 1073741824){
            temp1 = (int)(num/1073741824);
            temp2 = (int)(num/10737418.24);
            temp1 = temp2 - (temp1*100);
            k = 1073741824;
            b=3;
        } else if (num > (1048576)) {
            temp1 = (int)(num/1048576);
            temp2 = (int)(num/10485.76);
            temp1 = temp2 - (temp1*100);
            k =  1048576;
            b=2;
        } else if (num > 1024) {
            temp1 = (int)(num/1024);
            temp2 = (int)(num/10.24);
            temp1 = temp2 - (temp1*100);
            k = 1024;
            b=1;
        }
    }
    Bitmap generateBitmap(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        c.setTime(df.parse(date));
        int dow = c.get(Calendar.DAY_OF_WEEK);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(c.MONTH);
        Bitmap bitmap = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(38);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText("s",50,10,paint);
        /*paint.setTextSize(19);
        canvas.drawText(week[dow-1],50,100,paint);
        paint.setTextSize(8);
        canvas.drawText(Month[month-1],50,150,paint);
        canvas.drawText("50",50,50,paint);
*/
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }
}

