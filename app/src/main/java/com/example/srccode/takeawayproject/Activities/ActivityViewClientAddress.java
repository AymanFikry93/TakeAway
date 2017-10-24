package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterClientAdress;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;


import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterclientaddress;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classViewOrderDB;
import static com.example.srccode.takeawayproject.Global.GlopalClass.clientadresslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableClientInformationDB;


public class ActivityViewClientAddress extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_address);
        ActiveAndroid.initialize(this);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("Address");
        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                startActivity(homeIntent);
            }
        });
        ActiveAndroid.initialize(this);
        tableClientInformationDB =new Select().from(ClassClientInformation.class).execute();

        createviewlist();

        clientadresslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent testActivity = new Intent(getApplicationContext(),ActivityClientInformation.class); // ActivityClientReview ActivityCounter ActivityClientInformation ActivityPlaceOrder   ActivityMaps   ActivityItemFoodDetails
//                startActivity(testActivity);

            }
        });

        Button addadressbtn=(Button)findViewById(R.id.addadressbtn);
        addadressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testActivity = new Intent(getApplicationContext(),ActivityClientInformation.class); // ActivityClientReview ActivityCounter ActivityClientInformation ActivityPlaceOrder   ActivityMaps   ActivityItemFoodDetails
                startActivity(testActivity);
            }
        });
//        ImageButton imageButton=(ImageButton)findViewById(R.id.backtoresturantbtn);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    ActivityViewClientAddress.super.onBackPressed();
//            }
//        });
    }

    public  void createviewlist(){



        classViewOrderDB = new ArrayList<ClassClientInformation>();
        for (int i = 0; i<tableClientInformationDB.size(); i++) {
            classViewOrderDB.add(tableClientInformationDB.get(i));
        }

        clientadresslist= (ListView) findViewById(R.id.viewaddresslist);

        adapterclientaddress = new AdapterClientAdress(getApplicationContext(),R.layout.clientadressview_row, classViewOrderDB);

        clientadresslist.setAdapter(adapterclientaddress);


    }
}
