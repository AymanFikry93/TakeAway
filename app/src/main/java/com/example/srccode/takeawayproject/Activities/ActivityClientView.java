package com.example.srccode.takeawayproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterViewClient;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;
import java.util.ArrayList;
import java.util.List;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class ActivityClientView extends AppCompatActivity {
    private AdapterViewClient adapterViewClient;

    List<ClassClientInformation> tableViewClientDB;
    ArrayList<ClassClientInformation> classViewClientDBs;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerViewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view);
        ActiveAndroid.initialize(this);

        tableViewClientDB = new Select().from(ClassClientInformation.class).execute();
        createviewlist();
        SharedPreferences sharedPreferences_show=getSharedPreferences("accessTokendata", Context.MODE_PRIVATE);// to get the contetnt of the data base
        accesstoken =sharedPreferences_show.getString("accesstokenkey",null);


        Button addnewaddress=(Button) findViewById(R.id.addnewaddress);

        addnewaddress.setTypeface(typeface);
        addnewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent ClientInformation =new Intent(getApplicationContext(),ActivityClientInformation.class);
                    startActivity(ClientInformation);


            }
        });
    }

    public  void createviewlist(){

        classViewClientDBs = new ArrayList<ClassClientInformation>();
        for (int i = 0; i<tableViewClientDB.size(); i++) {
            classViewClientDBs.add(tableViewClientDB.get(i));
        }


        recyclerViewOrder = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerViewOrder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(layoutManager);
        recyclerViewOrder.setItemAnimator(new DefaultItemAnimator());

        adapterViewClient = new AdapterViewClient(getApplicationContext(), classViewClientDBs);
        recyclerViewOrder.setAdapter(adapterViewClient);

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
