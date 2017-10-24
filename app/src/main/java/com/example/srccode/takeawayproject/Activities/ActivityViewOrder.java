package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterViewOrder;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.feeType;
import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;


public class ActivityViewOrder extends AppCompatActivity {

    private ListView Vieworderlist;
    private AdapterViewOrder adapterViewOrder;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerViewOrder;
    List<ClassViewOrderDb> tableViewOrderDB;
    ArrayList<ClassViewOrderDb>  classViewOrderDBs;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.ViewOrderdetails);
        mTitle.setTypeface(typeface);
        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                startActivity(homeIntent);
            }
        });

        ActiveAndroid.initialize(this);

        tableViewOrderDB = new Select().from(ClassViewOrderDb.class).execute();
        Button submitbtn=(Button) findViewById(R.id.submitorderbtn);

        if(tableViewOrderDB.size()==0){//&&!Restopening.equals("Opening")
            submitbtn.setVisibility(View.GONE);
        }else {
            submitbtn.setVisibility(View.VISIBLE);
        }
        createviewlist();


        submitbtn.setTypeface(typeface);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ClientInformation = new Intent(getApplicationContext(), ActivityClientView.class);
                startActivity(ClientInformation);
            }
        });
    }

    public  void createviewlist(){
        classViewOrderDBs = new ArrayList<ClassViewOrderDb>();
        for (int i = 0; i<tableViewOrderDB.size(); i++) {
            classViewOrderDBs.add(tableViewOrderDB.get(i));
        }
      //  Vieworderlist= (ListView) findViewById(R.id.vieworderlist);
       // adapterViewOrder = new AdapterViewOrder(getApplicationContext(),R.layout.vieworder_row, classViewOrderDBs);
//        Vieworderlist.setAdapter(adapterViewOrder);
        recyclerViewOrder = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerViewOrder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(layoutManager);
        recyclerViewOrder.setItemAnimator(new DefaultItemAnimator());

         adapterViewOrder = new AdapterViewOrder(getApplicationContext(), classViewOrderDBs);
        recyclerViewOrder.setAdapter(adapterViewOrder);
    }

//    @Override
//    protected void onDestroy() {
//        // call the superclass method first
//        super.onDestroy();
//        ActiveAndroid.initialize(this);
//        new Delete().from(ClassViewOrderDb.class).execute();
//
//    }
}
