package com.cdm.priyathsaji.datamanager;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView rView ;
    RecyclerView.LayoutManager lManager;
    static int ch = 0;
    ArrayList<dataHolder> data = new ArrayList<>();
    ArrayList<dataHolder> adapterdata;
    String [] Title = {"Mobile Data Usage","Wifi Data Usage"};
    GestureOverlayView gesture;
    GestureLibrary lb;
    ArrayList<Prediction> prediction;
    int temp1,b,k;

    String [] unitArray = new String[]{
            "B", "KB", "MB", "GB"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Todays Usage");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rView = (RecyclerView)findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        rView.setLayoutManager(lManager);
        CardView cardView = (CardView)findViewById(R.id.card_view1);
        CardView mobile = (CardView)findViewById(R.id.mCard);
        data.clear();
        data = getDataHolder();
        if(data.size() != 0) {
            mobile.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.INVISIBLE);


            final ArrayList<dataHolder> temp = new ArrayList<>();
            for (int i = 0; i < (data.size()); i++) {
                temp.add(data.get(i));
            }
            data.clear();
            for (int i = (temp.size() - 1); i >= 0; i--) {
                data.add(temp.get(i));
            }

            try {
                Home();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void toogle(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Title[ch]);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        adapterdata = new ArrayList<>();
        for (int i = 0;i<(data.size()); i++) {
            adapterdata.add(data.get(i));
        }
        MyAdapter mAdapter = new MyAdapter(adapterdata,ch);
        //if(data.size()!=0)
        rView.setAdapter(mAdapter);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void Home() throws InterruptedException {
        TextView mData = (TextView) findViewById(R.id.mdownloaded);
        TextView wData = (TextView) findViewById(R.id.wdownloaded);
        TextView mUint = (TextView) findViewById(R.id.mUnit);
        TextView wUnit = (TextView) findViewById(R.id.wUnit);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String todays_Date = df.format(c.getTime());
        dataHolder d = data.get(0);
        int mp,wp;
        ProgressBar mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        ProgressBar mProgressBar2 = (ProgressBar)findViewById(R.id.circular_progress_bar2);
        if(Objects.equals(todays_Date,d.date))
        {
            unitDecider(d.mDownloaded);
            mData.setText(String.valueOf(d.mDownloaded / k) + "." + temp1);
            mUint.setText(unitArray[b]);
            unitDecider(d.wDownloaded);
            wData.setText(String.valueOf(d.wDownloaded / k) + "." + temp1);
            wUnit.setText(unitArray[b]);

            long m = (d.wDownloaded+d.mDownloaded);
            long w = (d.wDownloaded+d.mDownloaded);

            mp = (int) (d.mDownloaded*100/m);
            wp = (int) (d.wDownloaded*100/w);



        }
        else
        {
            mp = 0;
            wp = 0;
            mData.setText("0.0");
            mUint.setText("B");
            wData.setText("0.0");
            wUnit.setText("B");
        }

        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0,mp);
        anim.setDuration(2000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        ObjectAnimator anim2 = ObjectAnimator.ofInt(mProgressBar2, "progress", 0,wp);
        anim2.setDuration(2000);
        anim2.setInterpolator(new DecelerateInterpolator());
        anim2.start();






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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {
            if(data.size()!=0) {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Todays Usage");
                setSupportActionBar(toolbar);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();


                CardView mobile = (CardView) findViewById(R.id.mCard);
                RecyclerView rview = (RecyclerView) findViewById(R.id.recyclerView);
                rview.setVisibility(View.INVISIBLE);
                mobile.setVisibility(View.VISIBLE);
                try {
                    Home();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else if (id == R.id.Mobile_Data) {
            RecyclerView rview = (RecyclerView)findViewById(R.id.recyclerView);
            rview.setVisibility(View.VISIBLE);
            CardView mobile = (CardView)findViewById(R.id.mCard);
            mobile.setVisibility(View.INVISIBLE);
            ch = 0;
            toogle();
        } else if (id == R.id.Wifi) {
            RecyclerView rview = (RecyclerView)findViewById(R.id.recyclerView);
            rview.setVisibility(View.VISIBLE);
            CardView mobile = (CardView)findViewById(R.id.mCard);
            mobile.setVisibility(View.INVISIBLE);
            ch = 1;
            toogle();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<dataHolder> getDataHolder(){
        ArrayList<dataHolder> data = new ArrayList<>();
        try {
            FileInputStream in = openFileInput("data");
            ObjectInputStream ois = new ObjectInputStream(in);

            data = (ArrayList<dataHolder>) ois.readObject();
            ois.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return data;

    }
}
