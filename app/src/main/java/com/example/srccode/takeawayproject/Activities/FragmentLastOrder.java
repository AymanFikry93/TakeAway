package com.example.srccode.takeawayproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Adapters.AdapterLastOrder;
import com.example.srccode.takeawayproject.Classes.ClassLastOrderDb;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.UI.RecyclerItemClickListener;

import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.adapterlastOrder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classLastOrderDBs;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableListLastOrderDB;

/**
 * Created by ayman on 2017-08-16.
 */

public class FragmentLastOrder  extends Fragment {

    View v;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerLastOrder;
    Context myContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.activity_last_order, container, false);


        ActiveAndroid.initialize(getContext());

        tableListLastOrderDB = new Select().from(ClassLastOrderDb.class).execute();

        createviewlist();

        recyclerLastOrder = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerLastOrder.setHasFixedSize(true);
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

        return v;

    }

    public  void createviewlist(){



        classLastOrderDBs = new ArrayList<ClassLastOrderDb>();
        for (int i = 0; i<tableListLastOrderDB.size(); i++) {
            classLastOrderDBs.add(tableListLastOrderDB.get(i));
        }




    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        layoutManager = new LinearLayoutManager(getActivity());
        recyclerLastOrder.setLayoutManager(layoutManager);
        recyclerLastOrder.setItemAnimator(new DefaultItemAnimator());
        adapterlastOrder = new AdapterLastOrder( myContext,classLastOrderDBs);
        recyclerLastOrder.setAdapter(adapterlastOrder);
    }

    @Override
    public void onAttach(Context context) {
                myContext=context;

        super.onAttach(context);
    }


}
