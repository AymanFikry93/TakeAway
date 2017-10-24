package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.ClassPlaceOrder;

import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-06-05.
 */

public class AdapterClassPlaceOrder extends   RecyclerView.Adapter<AdapterClassPlaceOrder.MyViewHolder> {

        ArrayList<ClassPlaceOrder> classPlaceOrderslist;
    Context mcontext;
    public AdapterClassPlaceOrder(Context context, ArrayList<ClassPlaceOrder> classPlaceOrderlst) {

        classPlaceOrderslist = classPlaceOrderlst;
        mcontext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.placeorder_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        double totalplaceprice=0.0;
        int quantityplace=0;
        quantityplace =Integer.valueOf(classPlaceOrderslist.get(position).getquantity());
        totalplaceprice=  Double.parseDouble(classPlaceOrderslist.get(position).getprice());
        if(classPlaceOrderslist.get(position).getadditionname()!=null){
            holder.placeorderaddition.setText(classPlaceOrderslist.get(position).getadditionname());
            holder.placeorderaddition.setTypeface(typeface);

        }
        if(classPlaceOrderslist.get(position).getdrinkname()!=null){
            holder.placeorderdrink.setText(classPlaceOrderslist.get(position).getdrinkname());
            holder.placeorderdrink.setTypeface(typeface);

        }
        holder.placeorderName.setText(classPlaceOrderslist.get(position).getName());
         holder.placeorderquantity.setText(classPlaceOrderslist.get(position).getquantity());
         holder.placeorderprice.setText(String.valueOf( quantityplace*totalplaceprice)+mcontext.getString(R.string.SAR));
        holder.placeorderName.setTypeface(typeface);
        holder.placeorderquantity.setTypeface(typeface);
        holder.placeorderprice.setTypeface(typeface);


    }

    @Override
    public int getItemCount() {
        return classPlaceOrderslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView placeorderName;
        public TextView placeorderquantity;
        public TextView placeorderprice;
        public TextView placeorderaddition;
        public TextView placeorderdrink;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.placeorderName = (TextView)itemView.findViewById(R.id.Pordernametxt);
            this.placeorderaddition = (TextView)itemView.findViewById(R.id.additonname);
            this.placeorderdrink = (TextView)itemView.findViewById(R.id.drinkname);
            this.placeorderquantity = (TextView)itemView.findViewById(R.id.Porderquantitytxt);
            this.placeorderprice = (TextView)itemView.findViewById(R.id.placeorderpricetxt);

        }
    }





}
