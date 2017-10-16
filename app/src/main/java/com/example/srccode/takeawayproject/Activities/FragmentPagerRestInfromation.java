package com.example.srccode.takeawayproject.Activities;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srccode.takeawayproject.WebServices.RestInformationJson;


import static com.example.srccode.takeawayproject.Global.GlopalClass.GlobalRegionID;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.RegionId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.deliveryWay;
import static com.example.srccode.takeawayproject.Global.GlopalClass.feeType;
import static com.example.srccode.takeawayproject.Global.GlopalClass.feeTypeValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.openOrClose;
import static com.example.srccode.takeawayproject.Global.GlopalClass.payWay;
import static com.example.srccode.takeawayproject.Global.GlopalClass.restregion;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-05-21.
 */

public class FragmentPagerRestInfromation extends Fragment {






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_pager_restinformation_layout,container,false);
        restregion=(TextView)view.findViewById(R.id.region_rest_id);
        deliveryWay=(TextView)view.findViewById(R.id.DeliveryWayid);
        feeType=(TextView)view.findViewById(R.id.FeeTypeid);
        feeTypeValue=(TextView)view.findViewById(R.id.FeeTypeValueid);
        openOrClose=(TextView)view.findViewById(R.id.OpenOrCloseid);
        payWay=(TextView)view.findViewById(R.id.PayWayid);

        TextView textView2=(TextView)view.findViewById(R.id.textView2);
        TextView textView15=(TextView)view.findViewById(R.id.textView15);
        TextView textView16=(TextView)view.findViewById(R.id.textView16);
        TextView textView20=(TextView)view.findViewById(R.id.textView20);
        TextView textView18=(TextView)view.findViewById(R.id.textView18);
        TextView textView14=(TextView)view.findViewById(R.id.textView14);


//        AssetManager am = getContext().getAssets();
//        Typeface typeface = Typeface.createFromAsset(am,
//                String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
        textView2.setTypeface(typeface);
        textView15.setTypeface(typeface);
        textView16.setTypeface(typeface);
        textView20.setTypeface(typeface);
        textView18.setTypeface(typeface);
        textView14.setTypeface(typeface);


        new RestInformationJson(getContext()).execute("http://"+ HostName+"/api/Restaurants?RegionID="+GlobalRegionID);
        return  view;

    }








}
