package com.example.srccode.takeawayproject.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.NotificationCompat;
import android.util.TypedValue;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.util.Log;
import com.example.srccode.takeawayproject.Adapters.AdapterHomePager;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.UI.CustomDrawerButton;

import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.cTimer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;
import static com.example.srccode.takeawayproject.Global.GlopalClass.restfragment;

public class Home_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


   // Button button;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__main);

        SharedPreferences sharedPreferences_show=getSharedPreferences("languagedata", Context.MODE_PRIVATE);// to get the contetnt of the data base
         languagetype =sharedPreferences_show.getString("languagetype","en");

        Locale locale = new Locale(languagetype);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config,getApplicationContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences_notify_show=getSharedPreferences("notifydata", Context.MODE_PRIVATE);// to get the contetnt of the data base
       boolean notify =sharedPreferences_notify_show.getBoolean("notifytype",false);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
//        TextView tv = new TextView(getApplicationContext());
//        tv.setTextSize(10);
//
//        if (mToolbar != null) {
//            this.getSupportActionBar()
//                    .setDisplayHomeAsUpEnabled(true);
//            this.getSupportActionBar()
//                    .setCustomView(tv);
//            mToolbar.setNavigationIcon(R.drawable.back_arrow);
//        }
//
//        mToolbar.setTitleTextColor(Color.WHITE);
//        mToolbar.setTitle("");//R.string.action_home
//
        MainFragment fragment=new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        AdapterHomePager viewPagerAdapter = new AdapterHomePager(getSupportFragmentManager());
//        viewPagerAdapter.getItem(mViewPager.getCurrentItem());
//        mViewPager.setAdapter(viewPagerAdapter);
//       // mViewPager.setOffscreenPageLimit(0);
//           mViewPager.setCurrentItem(0);
//
//        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.setupWithViewPager(mViewPager);
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            //Resturantn by Regions","Last Order","Resturantn by offers "
//            tabLayout.getTabAt(0).setText(getString(R.string.ResturantnbyRegions));
//            tabLayout.getTabAt(1).setText(getString(R.string.LastOrder));
//            tabLayout.getTabAt(2).setText(getString(R.string.Resturantnbyoffers));
//        }
//      Button button=(Button)findViewById(R.id.togglenavigationid);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                drawer.openDrawer(GravityCompat.START);
//
////                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                        getParent(), drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////                drawer.setDrawerListener(toggle);
////                toggle.syncState();
//            }
//        });
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final CustomDrawerButton customDrawerButton = (CustomDrawerButton)findViewById(R.id.btnOpenDrawer);
        customDrawerButton.setDrawerLayout( drawer );
        customDrawerButton.getDrawerLayout().addDrawerListener( customDrawerButton );
        customDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawerButton.changeState();
            }
        });



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        ActiveAndroid.initialize(this);
        new Delete().from(ClassViewOrderDb.class).execute();

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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_resturantoffer) {
            // Handle the camera action
            FragmentHomeResturant fragmentPageOffer=new FragmentHomeResturant();

            android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragmentPageOffer);
            fragmentTransaction.commit();

        }
        else if (id == R.id.action_cobon) {
            Intent gotoCobon=new Intent(getApplicationContext(),ActivityCobon.class);
            startActivity(gotoCobon);
        }
        else if (id == R.id.action_home) {
            if(cTimer!=null)
                cTimer.cancel();
            finish();
            startActivity(getIntent());


        } else if (id == R.id.action_settings) {

            if(cTimer!=null)
                cTimer.cancel();
            Intent gotositting=new Intent(getApplicationContext(),SittingActivity.class);
            startActivity(gotositting);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
