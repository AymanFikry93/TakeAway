package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Classes.ClassLastOrderDb;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.srccode.takeawayproject.Global.GlopalClass.cTimer;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableLastOrderDB;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class ActivityCounter extends AppCompatActivity {
    TextView countertxt;
    int counter=90;
    List<ClassViewOrderDb> tableViewOrderDB;
    final Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView  mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("Counter");
        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                startActivity(homeIntent);
            }
        });
       // ImageView img=(ImageView)findViewById(R.id.welcome_img);
        if(cTimer!=null)
            cTimer.cancel();
//        if(languagetype.equals("en")){
//            toolbar.setBackground(getResources().getDrawable(R.drawable.entakeawayfooter));
//        }

        countertxt = (TextView) findViewById(R.id.counter_txt);
        TextView customtextView=(TextView)findViewById(R.id.welcome_txt);
        TextView _customtextView=(TextView)findViewById(R.id.finish_txt);


        customtextView.setTypeface(typeface);
        _customtextView.setTypeface(typeface);
        ActiveAndroid.initialize(this);
        tableViewOrderDB = new Select().from(ClassViewOrderDb.class).execute();


        for (int i = 0; i<tableViewOrderDB.size(); i++) {
            tableLastOrderDB= new ClassLastOrderDb(tableViewOrderDB.get(i).getorderid(),tableViewOrderDB.get(i).getOrdername(),
                    tableViewOrderDB.get(i).getOrderprice() ,tableViewOrderDB.get(i).getOrderimage(),tableViewOrderDB.get(i).getOrderquantity()
                    ,tableViewOrderDB.get(i).getdrinksID(),tableViewOrderDB.get(i).getdrinksName(),tableViewOrderDB.get(i).getadditionID()
                    ,tableViewOrderDB.get(i).getadditionName(),tableViewOrderDB.get(i).getitemofferorderedID()
                    ,tableViewOrderDB.get(i).getCookingCatorderedID(),tableViewOrderDB.get(i).getSelectedResturantID());
            tableLastOrderDB.save();
        }
        new Delete().from(ClassViewOrderDb.class).execute();

        final Timer myTimer = new Timer();
       // final MediaPlayer mp = new MediaPlayer();
        final Runnable myRunnable = new Runnable() {
            public void run() {

                countertxt.setTextSize(130);
                countertxt.setText(String.valueOf(counter));
            }
        };
        myTimer.schedule(new TimerTask() {
            // run is a method of TimeTask, which contains the task to run
            public void run() {
                counter--;
                if (counter == 0) {
// Canceling the Timer object
                    myTimer.cancel();
                    Intent backtohome = new Intent(getApplicationContext(), Home_MainActivity.class);
                    startActivity(backtohome);
                } else {
                    myHandler.post(myRunnable);
                }
            }
        }, 0, 1000);



    }
}

