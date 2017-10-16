package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterLastOrder;
import com.example.srccode.takeawayproject.Classes.ClassLastOrderDb;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;

import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterlastOrder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classLastOrderDBs;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.lastorderlist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableListLastOrderDB;

public class ActivityLastOrder extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerLastOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_order);

        ActiveAndroid.initialize(getApplicationContext());

        tableListLastOrderDB = new Select().from(ClassLastOrderDb.class).execute();

        createviewlist();

        recyclerLastOrder = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerLastOrder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerLastOrder.setLayoutManager(layoutManager);
        recyclerLastOrder.setItemAnimator(new DefaultItemAnimator());
        adapterlastOrder = new AdapterLastOrder( getApplicationContext(),classLastOrderDBs);
        recyclerLastOrder.setAdapter(adapterlastOrder);
//        recyclerLastOrder.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view,final int position) {
//                currentusedresturantId = tableListLastOrderDB.get(position).getSelectedResturantID();
//                ClassViewOrderDb tableViewOrderDB= new ClassViewOrderDb(
//                        tableListLastOrderDB.get(position).getorderid(),
//                        tableListLastOrderDB.get(position).getOrdername(),
//                        tableListLastOrderDB.get(position).getOrderprice(),
//                        tableListLastOrderDB.get(position).getOrderimage(),
//                        tableListLastOrderDB.get(position).getOrderquantity(),
//                        tableListLastOrderDB.get(position).getdrinksID(),
//                        tableListLastOrderDB.get(position).getdrinksName(),
//                        tableListLastOrderDB.get(position).getadditionID(),
//                        tableListLastOrderDB.get(position).getadditionName(),
//                        tableListLastOrderDB.get(position).getitemofferorderedID(),
//                        tableListLastOrderDB.get(position).getCookingCatorderedID(),
//                        tableListLastOrderDB.get(position).getSelectedResturantID());
//                tableViewOrderDB.save();
//                Intent gotovieworder=new Intent(getContext(),ActivityPlaceOrder.class);
//                startActivity(gotovieworder);
//            }
//
//        })
//        );

    }



    public  void createviewlist(){


        classLastOrderDBs = new ArrayList<ClassLastOrderDb>();
        for (int i = 0; i<tableListLastOrderDB.size(); i++) {
            classLastOrderDBs.add(tableListLastOrderDB.get(i));
        }


    }



}
