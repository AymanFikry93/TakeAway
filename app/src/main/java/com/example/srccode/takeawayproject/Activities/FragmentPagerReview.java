package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import com.example.srccode.takeawayproject.WebServices.PagerReviewJson;


import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.reviewdelivery;
import static com.example.srccode.takeawayproject.Global.GlopalClass.reviewprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.reviewquality;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

/**
 * Created by ayman on 2017-05-21.
 */

public class FragmentPagerReview extends Fragment{



    Button putReview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_pager_review_layout,container,false);
        reviewquality=(TextView)view.findViewById(R.id.qualityreviewid);
        reviewdelivery=(TextView)view.findViewById(R.id.deliveryreview);
        reviewprice=(TextView)view.findViewById(R.id.pricereviewid);

        TextView  textView1=(TextView)view.findViewById(R.id.textView1);
        TextView  textView2=(TextView)view.findViewById(R.id.textView2);
        TextView  textView3=(TextView)view.findViewById(R.id.textView3);


        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);

        putReview=(Button)view.findViewById(R.id.puturReview);

        new PagerReviewJson(getContext()).execute("http://"+ HostName+"/api/RestaurantRate/QualityRate?RestaurantID="+resturantId,
                "http://"+  HostName+"/api/RestaurantRate/PriceRate?RestaurantID="+resturantId,
                "http://"+  HostName+"/api/RestaurantRate/TimeRate?RestaurantID="+resturantId);



        putReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testActivity = new Intent(getContext(),ActivityClientReview.class);
                startActivity(testActivity);
            }
        });
        return  view;

    }

}
