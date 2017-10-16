package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterViewOrder;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;


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


        ActiveAndroid.initialize(this);
//        mToolbar = (Toolbar) findViewById(R.id.htab_toolbar);
//        if (mToolbar != null) {
//           this.setSupportActionBar(mToolbar);
//        }
//
//        if (mToolbar != null) {
//            this.getSupportActionBar()
//                    .setDisplayHomeAsUpEnabled(true);
//
//            mToolbar.setNavigationIcon(R.drawable.back_arrow);
//
//        }
//
//        mToolbar.setTitleTextColor(Color.WHITE);
//
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            getBaseContext().getmDrawerLayout().openDrawer(GravityCompat.START);
//            }
//        });

        tableViewOrderDB = new Select().from(ClassViewOrderDb.class).execute();

        createviewlist();



        Button submitbtn=(Button) findViewById(R.id.submitorderbtn);
        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
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
