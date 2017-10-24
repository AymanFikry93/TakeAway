package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.WebServices.DownloadImageTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-05-24.
 */

public class AdapterViewOrder extends RecyclerView.Adapter<AdapterViewOrder.MyViewHolder> {


    ArrayList<ClassViewOrderDb> classViewOrderList;
    LayoutInflater vi;
    int Resource;
   // AdapterViewOrder.ViewHolder holder;
    ClassViewOrderDb ViewOrderData;

    float neworderprice=0;

    int  quantityorder=0;
    int startflag=0;


Context mcontext;

//    public AdapterViewOrder(Context context, int resource, ArrayList<ClassViewOrderDb> objects) {
//        super(context, resource, objects);
//
//        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Resource = resource;
//        classViewOrderList = objects;
//
//    }
public AdapterViewOrder(Context context,ArrayList<ClassViewOrderDb> objects) {
    mcontext=context;
    classViewOrderList = objects;
}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vieworder_row, parent, false);

        AdapterViewOrder.MyViewHolder myViewHolder = new AdapterViewOrder.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {


                if (classViewOrderList!=null){

         ViewOrderData =  new Select().from(ClassViewOrderDb.class).where("orderid = ?", classViewOrderList.get(position).getorderid()).executeSingle();
            String price=classViewOrderList.get(position).getOrderprice();
            String quant =classViewOrderList.get(position).getOrderquantity();
            float nprice=Float.parseFloat(price)*Float.parseFloat(quant);
            float oldorderprice=Float.parseFloat(ViewOrderData.getOrderprice());
            if(startflag==0){
                 oldorderprice  = Float.parseFloat(ViewOrderData.getOrderprice());
                quantityorder= Integer.parseInt(ViewOrderData.getOrderquantity());
                neworderprice=oldorderprice*quantityorder;
            }
            if(classViewOrderList.get(position).getadditionName()!=null){
                holder.addtionName.setText(classViewOrderList.get(position).getadditionName());
            }
            if(classViewOrderList.get(position).getdrinksName()!=null){
                holder.drinkName.setText(classViewOrderList.get(position).getdrinksName());
            }
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            new DownloadImageTask(holder.imageView).execute(classViewOrderList.get(position).getOrderimage());
            holder.orderName.setText(classViewOrderList.get(position).getOrdername());

            holder.orderName.setTypeface(typeface);
                    holder.orderprice.setTypeface(typeface);
                    holder.totalorderprice.setTypeface(typeface);
                    holder.orderquantitytxt.setTypeface(typeface);
                    holder.drinkName.setTypeface(typeface);
                    holder.addtionName.setTypeface(typeface);


                    //classViewOrderList.get(position).getadditionName()
            holder.orderprice.setText(mcontext.getString(R.string.Price)+" "+oldorderprice);
            holder.totalorderprice.setText(mcontext.getString(R.string.TotalPrice)+" "+String.valueOf(nprice) );

            //holder.totalorderprice.setText(getContext().getString(R.string.TotalPrice)+" "+nprice+" SAR");
            holder.orderquantitytxt.setText(""+classViewOrderList.get(position).getOrderquantity());




        }
        else
        {
            float oldorderprice =0;
            neworderprice=0;
            quantityorder=0;


        }

      //  Button deletebtn=(Button)holder.findViewById(R.id.deleteorderbtn);
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new Delete().from(ClassViewOrder.class).where("Id = ?", ViewOrderData.getId()).execute();
               new Delete().from(ClassViewOrderDb.class).where("id = ?", classViewOrderList.get(position).getId()).execute();

                classViewOrderList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.incrmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startflag=1;
                ViewOrderData =  new Select().from(ClassViewOrderDb.class).where("id = ?", classViewOrderList.get(position).getId()).executeSingle();

                quantityorder= Integer.parseInt(ViewOrderData.getOrderquantity());
                 quantityorder++;
                float oldorderprice = Float.parseFloat(ViewOrderData.getOrderprice());

                neworderprice=quantityorder*oldorderprice;
                classViewOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
                classViewOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
                ViewOrderData.setOrderquantity(String.valueOf(quantityorder));
                ViewOrderData.setOrderprice(String.valueOf(oldorderprice));
                ViewOrderData.save();
                notifyDataSetChanged();


            }
        });
         //decrmntbtn=(Button)v.findViewById(R.id.decrementorderbtn);
        holder.decrmntbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startflag=1;
                ViewOrderData =  new Select().from(ClassViewOrderDb.class).where("id = ?", classViewOrderList.get(position).getId()).executeSingle();
                quantityorder= Integer.parseInt(ViewOrderData.getOrderquantity());
                quantityorder--;
                float oldorderprice = Float.parseFloat(ViewOrderData.getOrderprice());

                if(quantityorder<1){
                    Toast.makeText(mcontext,"can't be less than 1",Toast.LENGTH_LONG).show();
                    quantityorder=1;
                }
                else {

                    neworderprice=quantityorder*oldorderprice;
                classViewOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
                    classViewOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
                    ViewOrderData.setOrderquantity(String.valueOf(quantityorder));
                    ViewOrderData.setOrderprice(String.valueOf(oldorderprice));
                    ViewOrderData.save();
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return classViewOrderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView orderName;
        public TextView addtionName;
        public TextView drinkName;
        public TextView orderprice;
        public TextView totalorderprice;
        public TextView  orderquantitytxt;
        Button decrmntbtn;
        Button incrmentbtn;
        Button deletebtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView)itemView.findViewById(R.id.orderimage);
            this.orderName = (TextView)itemView.findViewById(R.id.ordername);
            this.addtionName = (TextView)itemView.findViewById(R.id.addtionname);
            this.drinkName = (TextView)itemView.findViewById(R.id.drinkname);
            this.orderprice = (TextView)itemView.findViewById(R.id.itemorderprice);
            this.totalorderprice=(TextView)itemView.findViewById(R.id.totalitemorderprice);
            this.orderquantitytxt=(TextView)itemView.findViewById(R.id.orderquantity);
            this.imageView = (ImageView) itemView.findViewById(R.id.orderimage);
            incrmentbtn=(Button)itemView.findViewById(R.id.incrementorderbtn);
            decrmntbtn=(Button)itemView.findViewById(R.id.decrementorderbtn);
             deletebtn=(Button)itemView.findViewById(R.id.deleteorderbtn);

        }
    }
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        View v = convertView;
//
//        // if website is closed
//        if(v == null){
//            holder = new ViewHolder();
//
//            v = vi.inflate(Resource,null);
//
//            holder.imageView = (ImageView)v.findViewById(R.id.orderimage);
//            holder.orderName = (TextView)v.findViewById(R.id.ordername);
//            holder.addtionName = (TextView)v.findViewById(R.id.addtionname);
//            holder.drinkName = (TextView)v.findViewById(R.id.drinkname);
//            holder.orderprice = (TextView)v.findViewById(R.id.itemorderprice);
//            holder.totalorderprice=(TextView)v.findViewById(R.id.totalitemorderprice);
//            holder.orderquantitytxt=(TextView)v.findViewById(R.id.orderquantity);
//            v.setTag(holder);
//
//
//        }else{
//
//            holder = (ViewHolder)v.getTag();
//        }
//
//
//        if (classViewOrderList!=null){
//
//         ViewOrderData =  new Select().from(ClassViewOrderDb.class).where("orderid = ?", classViewOrderList.get(position).getorderid()).executeSingle();
//            String price=classViewOrderList.get(position).getOrderprice();
//            String quant =classViewOrderList.get(position).getOrderquantity();
//            float nprice=Float.parseFloat(price)*Float.parseFloat(quant);
//            float oldorderprice=Float.parseFloat(ViewOrderData.getOrderprice());
//            if(startflag==0){
//                 oldorderprice  = Float.parseFloat(ViewOrderData.getOrderprice());
//                quantityorder= Integer.parseInt(ViewOrderData.getOrderquantity());
//                neworderprice=oldorderprice*quantityorder;
//            }
//            if(classViewOrderList.get(position).getadditionName()!=null){
//                holder.addtionName.setText(classViewOrderList.get(position).getadditionName());
//            }
//            if(classViewOrderList.get(position).getdrinksName()!=null){
//                holder.drinkName.setText(classViewOrderList.get(position).getdrinksName());
//            }
//            holder.imageView.setImageResource(R.mipmap.ic_launcher);
//            new DownloadImageTask(holder.imageView).execute(classViewOrderList.get(position).getOrderimage());
//            holder.orderName.setText(classViewOrderList.get(position).getOrdername());
//            AssetManager am = getContext().getAssets();
//            Typeface typeface = Typeface.createFromAsset(am,
//                    String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
//            holder.orderName.setTypeface(typeface);
//
//            //classViewOrderList.get(position).getadditionName()
//            holder.orderprice.setText(getContext().getString(R.string.Price)+" "+oldorderprice);
//            holder.totalorderprice.setText(getContext().getString(R.string.TotalPrice)+" "+String.valueOf(nprice) );
//
//            //holder.totalorderprice.setText(getContext().getString(R.string.TotalPrice)+" "+nprice+" SAR");
//            holder.orderquantitytxt.setText(""+classViewOrderList.get(position).getOrderquantity());
//
//
//
//
//        }
//        else
//        {
//            float oldorderprice =0;
//            neworderprice=0;
//            quantityorder=0;
//
//
//        }
//
//        Button deletebtn=(Button)v.findViewById(R.id.deleteorderbtn);
//        deletebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // new Delete().from(ClassViewOrder.class).where("Id = ?", ViewOrderData.getId()).execute();
//               new Delete().from(ClassViewOrderDb.class).where("orderid = ?", classViewOrderList.get(position).getorderid()).execute();
//
//                classViewOrderList.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });
//
//         incrmentbtn=(Button)v.findViewById(R.id.incrementorderbtn);
//        incrmentbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startflag=1;
//                ViewOrderData =  new Select().from(ClassViewOrderDb.class).where("orderid = ?", classViewOrderList.get(position).getorderid()).executeSingle();
//
//                quantityorder= Integer.parseInt(ViewOrderData.getOrderquantity());
//                 quantityorder++;
//                float oldorderprice = Float.parseFloat(ViewOrderData.getOrderprice());
//
//                neworderprice=quantityorder*oldorderprice;
//                classViewOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
//                classViewOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
//                ViewOrderData.setOrderquantity(String.valueOf(quantityorder));
//                ViewOrderData.setOrderprice(String.valueOf(oldorderprice));
//                ViewOrderData.save();
//                notifyDataSetChanged();
//
//
//            }
//        });
//         decrmntbtn=(Button)v.findViewById(R.id.decrementorderbtn);
//        decrmntbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startflag=1;
//                ViewOrderData =  new Select().from(ClassViewOrderDb.class).where("orderid = ?", classViewOrderList.get(position).getorderid()).executeSingle();
//                quantityorder= Integer.parseInt(ViewOrderData.getOrderquantity());
//                quantityorder--;
//                float oldorderprice = Float.parseFloat(ViewOrderData.getOrderprice());
//
//                if(quantityorder<1){
//                    Toast.makeText(getContext(),"can't be less than 1",Toast.LENGTH_LONG).show();
//                    quantityorder=1;
//                }
//                else {
//
//                    neworderprice=quantityorder*oldorderprice;
//                classViewOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
//                    classViewOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
//                    ViewOrderData.setOrderquantity(String.valueOf(quantityorder));
//                    ViewOrderData.setOrderprice(String.valueOf(oldorderprice));
//                    ViewOrderData.save();
//                    notifyDataSetChanged();
//                }
//            }
//        });
//
//
//
//
//        return v;
//    }


//    static class ViewHolder {
//        public ImageView imageView;
//        public TextView orderName;
//        public TextView addtionName;
//        public TextView drinkName;
//        public TextView orderprice;
//        public TextView totalorderprice;
//        public TextView  orderquantitytxt;
//    }


    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;


            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
            }


            return mIcon11;
        }

        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }

}
