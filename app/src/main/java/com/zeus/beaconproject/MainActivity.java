package com.zeus.beaconproject;
import android.app.Notification;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.goncalves.pugnotification.notification.PugNotification;

public class MainActivity extends AppCompatActivity {

    Region region;
    BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //updateShoppingList();
        beaconManager =new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region1, List<Beacon> list) {
                if(region==region1 && !list.isEmpty()){
                    Beacon b = list.get(0);
                    String Id=String.format("%d:%d",b.getMajor(),b.getMinor());


                }
            }
        });

        region = new Region("ranged region", null, null, null);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(region);
                beaconManager.startRanging(region);
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                PugNotification.with(getApplicationContext())
                        .load()
                        .title("HELLOO!!")
                        .message("Welcome to Rajasthan.!!")
                        .bigTextStyle("Hurry")
                        .smallIcon(R.drawable.pugnotification_ic_launcher)
                        .largeIcon(R.drawable.pugnotification_ic_launcher)
                        .flags(Notification.DEFAULT_ALL)
                        .color(android.R.color.background_dark)
                        .simple()
                        .build();
            }

            @Override
            public void onExitedRegion(Region region) {

                PugNotification.with(getApplicationContext())
                        .load()
                        .title("GoodBye!!")
                        .message("Thanks For Visiting.!! \n See you soon :)")
                        .smallIcon(R.drawable.pugnotification_ic_launcher)
                        .largeIcon(R.drawable.pugnotification_ic_launcher)
                        .flags(Notification.DEFAULT_ALL)
                        .color(android.R.color.background_dark)
                        .simple()
                        .build();
                // could add an "exit" notification too if you want (-:
            }
        });
    }


    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
