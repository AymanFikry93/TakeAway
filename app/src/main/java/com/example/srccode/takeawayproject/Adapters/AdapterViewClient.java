package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Activities.ActivityLogin;
import com.example.srccode.takeawayproject.Activities.ActivityPlaceOrder;
import com.example.srccode.takeawayproject.Activities.ActivityResturants;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;

import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.accesstoken;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentLatitudefororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentLongtitudefororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentaddressfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentregionIdfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentregionfororder;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-08-29.
 */

public class AdapterViewClient extends RecyclerView.Adapter<AdapterViewClient.MyViewHolder>  {


    ArrayList<ClassClientInformation> classViewClientList;

    ClassClientInformation ViewClientData;


    int startflag=0;
    Context mcontext;



public AdapterViewClient(Context context,ArrayList<ClassClientInformation> objects) {
    classViewClientList = objects;
    mcontext=context;
}
    @Override
    public AdapterViewClient.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewclient_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterViewClient.MyViewHolder holder,final int position) {
        if (classViewClientList!=null){

            ViewClientData =  new Select().from(ClassClientInformation.class).where("Id = ?", classViewClientList.get(position).getId()).executeSingle();


            holder.locationName.setText(mcontext.getString(R.string.location)+classViewClientList.get(position).getregionmapLatitude()+","
                    +classViewClientList.get(position).getregionmapLongtitude());
            int region=classViewClientList.get(position).getregionId();
          holder.regionName.setText(mcontext.getString(R.string.region)+classViewClientList.get(position).getregionname());
            holder.addressName.setText(classViewClientList.get(position).getadress());
            holder.addressName.setTypeface(typeface);

        }


        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().from(ClassClientInformation.class).where("Id = ?", classViewClientList.get(position).getId()).execute();
                classViewClientList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.orderbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(accesstoken!=null){
                 currentaddressfororder=classViewClientList.get(position).getadress();
                 currentregionIdfororder=classViewClientList.get(position).getregionId();
                currentregionfororder=classViewClientList.get(position).getregionname();
                 currentLatitudefororder=classViewClientList.get(position).getregionmapLatitude();
                 currentLongtitudefororder=classViewClientList.get(position).getregionmapLongtitude();
                Intent activityPlaceOrder =new Intent(mcontext,ActivityPlaceOrder.class);
                activityPlaceOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(activityPlaceOrder);
                        }else {
                            Intent gotologin =new Intent(mcontext,ActivityLogin.class);
                    gotologin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(gotologin);
                        }
            }
        });

    }

    @Override
    public int getItemCount() {
        return classViewClientList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView addressName;
        public TextView regionName;
        public TextView locationName;
        ImageButton deletebtn;
        TextView orderbutton;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.addressName = (TextView)itemView.findViewById(R.id.addressname);
            this.regionName = (TextView)itemView.findViewById(R.id.regionsname);
            this.locationName = (TextView)itemView.findViewById(R.id.locationname);
             this.deletebtn=(ImageButton)itemView.findViewById(R.id.deleteaddressbtn);
             this.orderbutton=(TextView) itemView.findViewById(R.id.gotoorderbutton);

        }
    }


//    public AdapterViewClient(Context context, int resource, ArrayList<ClassClientInformation> objects) {
//        super(context, resource, objects);
//
//        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Resource = resource;
//        classViewClientList = objects;
//
//
//    }
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        View v = convertView;
//
//        // if website is closed
//        if(v == null){
//            holder = new AdapterViewClient.ViewHolder();
//
//            v = vi.inflate(Resource,null);
//
//            holder.addressName = (TextView)v.findViewById(R.id.addressname);
//            holder.regionName = (TextView)v.findViewById(R.id.regionsname);
//            holder.locationName = (TextView)v.findViewById(R.id.locationname);
//            v.setTag(holder);
//
//
//        }else{
//
//            holder = (AdapterViewClient.ViewHolder)v.getTag();
//        }
//
//
//        if (classViewClientList!=null){
//
//            ViewClientData =  new Select().from(ClassClientInformation.class).where("Id = ?", classViewClientList.get(position).getId()).executeSingle();
//
//
//            holder.locationName.setText("location :"+classViewClientList.get(position).getregionmapLatitude()+","
//                    +classViewClientList.get(position).getregionmapLongtitude());
//            int region=classViewClientList.get(position).getregionId();
//            ClassArea classArea = new Select().from(ClassArea.class).where("regionid = ?",region).executeSingle();
//
//            holder.regionName.setText("region :"+classArea.getStreetname());
//            holder.addressName.setText(classViewClientList.get(position).getadress());
//            holder.addressName.setTypeface(typeface);
//
//        }
//
//
//        Button deletebtn=(Button)v.findViewById(R.id.deleteaddressbtn);
//        deletebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Delete().from(ClassClientInformation.class).where("Id = ?", classViewClientList.get(position).getId()).execute();
//                classViewClientList.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });
//        TextView orderbutton=(TextView) v.findViewById(R.id.gotoorderbutton);
//
//        orderbutton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                 currentaddressfororder=classViewClientList.get(position).getadress();
//                 currentregionIdfororder=classViewClientList.get(position).getregionId();
//                ClassArea classArea = new Select().from(ClassArea.class).where("regionid = ?",currentregionIdfororder).executeSingle();
//                currentregionfororder=classArea.getStreetname();
//                 currentLatitudefororder=classViewClientList.get(position).getregionmapLatitude();
//                 currentLongtitudefororder=classViewClientList.get(position).getregionmapLongtitude();
//                Intent activityPlaceOrder =new Intent(getContext(),ActivityPlaceOrder.class);
//                activityPlaceOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getContext().startActivity(activityPlaceOrder);
//
//            }
//        });
//
//
//        return v;
//    }


//    static class ViewHolder {
//        public TextView addressName;
//        public TextView regionName;
//        public TextView locationName;
//    }



}
