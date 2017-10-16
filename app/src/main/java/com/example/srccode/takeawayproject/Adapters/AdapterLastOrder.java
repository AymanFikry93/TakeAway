package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassLastOrderDb;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-07-25.
 */

public class AdapterLastOrder extends RecyclerView.Adapter<AdapterLastOrder.MyViewHolder>{


    ArrayList<ClassLastOrderDb> classlastOrderList;
    ClassLastOrderDb LastOrderData;
   Context mcontext;
    float neworderprice=0;

    int  quantityorder=0;
    int startflag=0;

    public AdapterLastOrder(Context context,ArrayList<ClassLastOrderDb> objects) {
        mcontext=context;
        classlastOrderList = objects;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lastorderrow, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterLastOrder.MyViewHolder holder, final int position) {
        if (classlastOrderList!=null){
             long idd=classlastOrderList.get(position).getId();

            LastOrderData =  new Select().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).executeSingle();
            String price=classlastOrderList.get(position).getOrderprice();
            String quant =classlastOrderList.get(position).getOrderquantity();
            float nprice=Float.parseFloat(price)*Float.parseFloat(quant);
            float oldorderprice=Float.parseFloat(LastOrderData.getOrderprice());
            if(startflag==0){
                oldorderprice  = Float.parseFloat(LastOrderData.getOrderprice());
                quantityorder= Integer.parseInt(LastOrderData.getOrderquantity());
                neworderprice=oldorderprice*quantityorder;
            }
            if(classlastOrderList.get(position).getadditionName()!=null){
                holder.addtionName.setText(classlastOrderList.get(position).getadditionName());
            }
            if(classlastOrderList.get(position).getdrinksName()!=null){
                holder.drinkName.setText(classlastOrderList.get(position).getdrinksName());
            }
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            new AdapterLastOrder.DownloadImageTask(holder.imageView).execute(classlastOrderList.get(position).getOrderimage());
            holder.orderName.setText(classlastOrderList.get(position).getOrdername());

            holder.orderName.setTypeface(typeface);

            //classViewOrderList.get(position).getadditionName()
            holder.orderprice.setText(mcontext.getString(R.string.Price)+" "+oldorderprice);
            holder.totalorderprice.setText(mcontext.getString(R.string.TotalPrice)+" "+nprice);
            holder.orderquantitytxt.setText(""+classlastOrderList.get(position).getOrderquantity());




        }
        else
        {
            float oldorderprice =0;
            neworderprice=0;
            quantityorder=0;


        }

//        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // new Delete().from(ClassViewOrder.class).where("Id = ?", ViewOrderData.getId()).execute();
//                new Delete().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).execute();
//
//                classlastOrderList.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });

        holder.incrmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startflag=1;
                LastOrderData =  new Select().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).executeSingle();

                quantityorder= Integer.parseInt(LastOrderData.getOrderquantity());
                quantityorder++;
                float oldorderprice = Float.parseFloat(LastOrderData.getOrderprice());

                neworderprice=quantityorder*oldorderprice;
                classlastOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
                classlastOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
                LastOrderData.setOrderquantity(String.valueOf(quantityorder));
                LastOrderData.setOrderprice(String.valueOf(oldorderprice));
                LastOrderData.save();
                notifyDataSetChanged();


            }
        });
        holder.decrmntbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startflag=1;
                LastOrderData =  new Select().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).executeSingle();
                quantityorder= Integer.parseInt(LastOrderData.getOrderquantity());
                quantityorder--;
                float oldorderprice = Float.parseFloat(LastOrderData.getOrderprice());

                if(quantityorder<1){
                    Toast.makeText(mcontext,"can't be less than 1",Toast.LENGTH_LONG).show();
                    quantityorder=1;
                }
                else {

                    neworderprice=quantityorder*oldorderprice;
                    classlastOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
                    classlastOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
                    LastOrderData.setOrderquantity(String.valueOf(quantityorder));
                    LastOrderData.setOrderprice(String.valueOf(oldorderprice));
                    LastOrderData.save();
                    notifyDataSetChanged();
                }
            }

        });
//        holder.orderbutton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                    currentusedresturantId = tableListLastOrderDB.get(position).getSelectedResturantID();
//                     resturantId=currentusedresturantId;
//
//                    ClassViewOrderDb tableViewOrderDB= new ClassViewOrderDb(
//                            tableListLastOrderDB.get(position).getorderid(),
//                            tableListLastOrderDB.get(position).getOrdername(),
//                            tableListLastOrderDB.get(position).getOrderprice(),
//                            tableListLastOrderDB.get(position).getOrderimage(),
//                            tableListLastOrderDB.get(position).getOrderquantity(),
//                            tableListLastOrderDB.get(position).getdrinksID(),
//                            tableListLastOrderDB.get(position).getdrinksName(),
//                            tableListLastOrderDB.get(position).getadditionID(),
//                            tableListLastOrderDB.get(position).getadditionName(),
//                            tableListLastOrderDB.get(position).getitemofferorderedID(),
//                            tableListLastOrderDB.get(position).getCookingCatorderedID(),
//                            tableListLastOrderDB.get(position).getSelectedResturantID());
//                    tableViewOrderDB.save();
//
//                    Intent gotovieworder =new Intent(mcontext,PagerActivity.class);
//                gotovieworder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mcontext.startActivity(gotovieworder);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return classlastOrderList.size();
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
//        Button deletebtn;
//        TextView orderbutton;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView)itemView.findViewById(R.id.orderimage);
            this.orderName = (TextView)itemView.findViewById(R.id.ordername);
            this.addtionName = (TextView)itemView.findViewById(R.id.addtionname);
            this.drinkName = (TextView)itemView.findViewById(R.id.drinkname);
            this.orderprice = (TextView)itemView.findViewById(R.id.itemorderprice);
            this.totalorderprice=(TextView)itemView.findViewById(R.id.totalitemorderprice);
            this.orderquantitytxt=(TextView)itemView.findViewById(R.id.orderquantity);
            this.incrmentbtn=(Button)itemView.findViewById(R.id.incrementorderbtn);
            this.decrmntbtn=(Button)itemView.findViewById(R.id.decrementorderbtn);
//            this.deletebtn=(Button)itemView.findViewById(R.id.deleteorderbtn);
//            this.orderbutton=(TextView) itemView.findViewById(R.id.gotoorderbutton);

        }
    }
//    public AdapterLastOrder(Context context, int resource, ArrayList<ClassLastOrderDb> objects) {
//        super(context, resource, objects);
//
//        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Resource = resource;
//        classlastOrderList = objects;
//
//
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        View v = convertView;
//
//        // if website is closed
//        if(v == null){
//            holder = new AdapterLastOrder.ViewHolder();
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
//            holder = (AdapterLastOrder.ViewHolder)v.getTag();
//        }
//
//
//        if (classlastOrderList!=null){
//             long idd=classlastOrderList.get(position).getId();
//
//            LastOrderData =  new Select().from(com.example.srccode.takeawayproject.ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).executeSingle();
//            String price=classlastOrderList.get(position).getOrderprice();
//            String quant =classlastOrderList.get(position).getOrderquantity();
//            float nprice=Float.parseFloat(price)*Float.parseFloat(quant);
//            float oldorderprice=Float.parseFloat(LastOrderData.getOrderprice());
//            if(startflag==0){
//                oldorderprice  = Float.parseFloat(LastOrderData.getOrderprice());
//                quantityorder= Integer.parseInt(LastOrderData.getOrderquantity());
//                neworderprice=oldorderprice*quantityorder;
//            }
//            if(classlastOrderList.get(position).getadditionName()!=null){
//                holder.addtionName.setText(classlastOrderList.get(position).getadditionName());
//            }
//            if(classlastOrderList.get(position).getdrinksName()!=null){
//                holder.drinkName.setText(classlastOrderList.get(position).getdrinksName());
//            }
//            holder.imageView.setImageResource(R.mipmap.ic_launcher);
//            new AdapterLastOrder.DownloadImageTask(holder.imageView).execute(classlastOrderList.get(position).getOrderimage());
//            holder.orderName.setText(classlastOrderList.get(position).getOrdername());
//            AssetManager am = getContext().getAssets();
//            Typeface typeface = Typeface.createFromAsset(am,
//                    String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
//            holder.orderName.setTypeface(typeface);
//
//            //classViewOrderList.get(position).getadditionName()
//            holder.orderprice.setText(getContext().getString(R.string.Price)+" "+oldorderprice);
//            holder.totalorderprice.setText(getContext().getString(R.string.TotalPrice)+" "+nprice);
//            holder.orderquantitytxt.setText(""+classlastOrderList.get(position).getOrderquantity());
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
//                // new Delete().from(ClassViewOrder.class).where("Id = ?", ViewOrderData.getId()).execute();
//                new Delete().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).execute();
//
//                classlastOrderList.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });
//
//        incrmentbtn=(Button)v.findViewById(R.id.incrementorderbtn);
//        incrmentbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startflag=1;
//                LastOrderData =  new Select().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).executeSingle();
//
//                quantityorder= Integer.parseInt(LastOrderData.getOrderquantity());
//                quantityorder++;
//                float oldorderprice = Float.parseFloat(LastOrderData.getOrderprice());
//
//                neworderprice=quantityorder*oldorderprice;
//                classlastOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
//                classlastOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
//                LastOrderData.setOrderquantity(String.valueOf(quantityorder));
//                LastOrderData.setOrderprice(String.valueOf(oldorderprice));
//                LastOrderData.save();
//                notifyDataSetChanged();
//
//
//            }
//        });
//        decrmntbtn=(Button)v.findViewById(R.id.decrementorderbtn);
//        decrmntbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startflag=1;
//                LastOrderData =  new Select().from(ClassLastOrderDb.class).where("Id = ?", classlastOrderList.get(position).getId()).executeSingle();
//                quantityorder= Integer.parseInt(LastOrderData.getOrderquantity());
//                quantityorder--;
//                float oldorderprice = Float.parseFloat(LastOrderData.getOrderprice());
//
//                if(quantityorder<1){
//                    Toast.makeText(getContext(),"can't be less than 1",Toast.LENGTH_LONG).show();
//                    quantityorder=1;
//                }
//                else {
//
//                    neworderprice=quantityorder*oldorderprice;
//                    classlastOrderList.get(position).setOrderprice(String.valueOf(neworderprice));
//                    classlastOrderList.get(position).setOrderquantity(String.valueOf(quantityorder));
//                    LastOrderData.setOrderquantity(String.valueOf(quantityorder));
//                    LastOrderData.setOrderprice(String.valueOf(oldorderprice));
//                    LastOrderData.save();
//                    notifyDataSetChanged();
//                }
//            }
//
//        });
//
//
//
//
//        return v;
//    }
//
//
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
