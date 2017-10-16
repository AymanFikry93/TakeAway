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
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterClassPlaceOrder;
import com.example.srccode.takeawayproject.ClassPlaceOrder;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.WebServices.PostOrderJson;


import java.util.ArrayList;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferFeeTypeId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.OfferResturantValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterClassPlaceOrder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classPlaceOrders;
import static com.example.srccode.takeawayproject.Global.GlopalClass.fee;
import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;
import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableViewOrderDB;

public class ActivityPlaceOrder extends AppCompatActivity {


     Button btnSubmit;
    TextView orderFeetotal;
    double totalplaceprice=0.0;
    RecyclerView recyclerPlaceOrderView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
     //   ImageView toolbar=(ImageView)findViewById(R.id.toolbar_bottom);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences_show=getSharedPreferences("accessTokendata", Context.MODE_PRIVATE);// to get the contetnt of the data base
        accesstoken =sharedPreferences_show.getString("accesstokenkey",null);
        ActiveAndroid.initialize(this);
        tableViewOrderDB = new Select().from(ClassViewOrderDb.class).execute();

        classPlaceOrders =new ArrayList<ClassPlaceOrder>();
        fee= FeeDeliveryValue;


        double placeprice=0.0;
        int quantityplace=0;
        if(tableViewOrderDB.size()!=0){

            for (int i = 0; i<tableViewOrderDB.size(); i++) {

                ClassPlaceOrder classPlaceOrder=new ClassPlaceOrder();
                classPlaceOrder.setId(tableViewOrderDB.get(i).getorderid());
                classPlaceOrder.setName(tableViewOrderDB.get(i).getOrdername());
                classPlaceOrder.setprice(tableViewOrderDB.get(i).getOrderprice());
                classPlaceOrder.setquantity(tableViewOrderDB.get(i).getOrderquantity());
                classPlaceOrder.setaddtionname(tableViewOrderDB.get(i).getadditionName());
                classPlaceOrder.setadditionId(tableViewOrderDB.get(i).getadditionID());
                classPlaceOrder.setdrinkname(tableViewOrderDB.get(i).getdrinksName());
                classPlaceOrder.setdrinkId(tableViewOrderDB.get(i).getdrinksID());
                classPlaceOrders.add(classPlaceOrder);
                quantityplace =Integer.valueOf(tableViewOrderDB.get(i).getOrderquantity());
                placeprice=Double.parseDouble(tableViewOrderDB.get(i).getOrderprice());
                totalplaceprice=totalplaceprice+(placeprice*quantityplace);

            }
        }
       else{

////            List<ClassLastOrderDb> tableLastOrderlist  = new Select().from(ClassLastOrderDb.class).execute();
////
////            for (int i = 0; i<tableLastOrderlist.size(); i++) {
////
////                ClassPlaceOrder classPlaceOrder=new ClassPlaceOrder();
////                classPlaceOrder.setId(tableLastOrderlist.get(i).getorderid());
////                classPlaceOrder.setName(tableLastOrderlist.get(i).getOrdername());
////                classPlaceOrder.setprice(tableLastOrderlist.get(i).getOrderprice());
////                classPlaceOrder.setquantity(tableLastOrderlist.get(i).getOrderquantity());
////                classPlaceOrder.setaddtionname(tableLastOrderlist.get(i).getadditionName());
////                classPlaceOrder.setadditionId(tableLastOrderlist.get(i).getadditionID());
////                classPlaceOrder.setdrinkname(tableLastOrderlist.get(i).getdrinksName());
////                classPlaceOrder.setdrinkId(tableLastOrderlist.get(i).getdrinksID());
////                classPlaceOrders.add(classPlaceOrder);
////                quantityplace =Integer.valueOf(tableLastOrderlist.get(i).getOrderquantity());
////                placeprice=Double.parseDouble(tableLastOrderlist.get(i).getOrderprice());
////                totalplaceprice=totalplaceprice+(placeprice*quantityplace);
//            }

        }

        recyclerPlaceOrderView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerPlaceOrderView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerPlaceOrderView.setLayoutManager(layoutManager);
        recyclerPlaceOrderView.setItemAnimator(new DefaultItemAnimator());
         adapterClassPlaceOrder =new AdapterClassPlaceOrder(classPlaceOrders);

        recyclerPlaceOrderView.setAdapter(adapterClassPlaceOrder);
      //  ListView listView = (ListView )findViewById(R.id.placeorderlist);
      //  adapterClassPlaceOrder =new AdapterClassPlaceOrder(getApplicationContext(),R.layout.placeorder_row, classPlaceOrders);
       // listView.setAdapter(adapterClassPlaceOrder);
        adapterClassPlaceOrder.notifyDataSetChanged();

                if(OfferFeeTypeId==1) {
                    totalplaceprice=totalplaceprice-OfferResturantValue;
                }
                else if(OfferFeeTypeId==2) {
                    totalplaceprice=totalplaceprice-(totalplaceprice*OfferResturantValue*.01);
                }

         orderFeetotal =(TextView) findViewById(R.id.placeorderFeeprice);
        orderFeetotal.setText(String.valueOf(fee));
        totalplaceprice=totalplaceprice+fee;
        TextView orderpricetotal =(TextView) findViewById(R.id.placeordertotalprice);
        orderpricetotal.setText(String.valueOf(totalplaceprice));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.setVisibility(v.GONE);
                if(totalplaceprice<mincharge){
                    Toast.makeText(getApplicationContext(),"purchases value must be more than minimum charge",Toast.LENGTH_LONG).show();

                    Intent gotologin =new Intent(getApplicationContext(),PagerActivity.class);
                    startActivity(gotologin);

                }
                else if(accesstoken!=null){
                    new PostOrderJson(getApplicationContext()).execute(HostName+"/api/Orders",""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api
                }else {
                    Intent gotologin =new Intent(getApplicationContext(),ActivityLogin.class);
                    startActivity(gotologin);
                }
//                ClassLastOrderDb tableLastOrderDB;
//                for (int i = 0; i<tableViewOrderDB.size(); i++) {
//                    tableLastOrderDB= new ClassLastOrderDb(tableViewOrderDB.get(i).getorderid(),tableViewOrderDB.get(i).getOrdername(),
//                         tableViewOrderDB.get(i).getOrderprice() ,tableViewOrderDB.get(i).getOrderimage(),tableViewOrderDB.get(i).getOrderquantity()
//                            ,tableViewOrderDB.get(i).getdrinksID(),tableViewOrderDB.get(i).getdrinksName(),tableViewOrderDB.get(i).getadditionID()
//                            ,tableViewOrderDB.get(i).getadditionName());
//                          tableLastOrderDB.save();
//                }
//                new Delete().from(ClassViewOrderDb.class).execute();

            }
        });


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