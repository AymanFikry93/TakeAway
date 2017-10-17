package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.srccode.takeawayproject.Activities.FragmentCategory;
import com.example.srccode.takeawayproject.Activities.FragmentPagerRestInfromation;
import com.example.srccode.takeawayproject.Activities.FragmentPagerReview;

/**
 * Created by ayman on 2017-05-21.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    final int pageCount=2;
    Context context;
    private  String tabTitles []=new  String[]{"Reviews ","Information"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }


    @Override
    public int getCount() {
        return pageCount;
    }
    @Override
    public CharSequence getPageTitle(int position) {
      //  Log.v("tabTitles - getPageTitle = ",tabTitles[position]);



        return tabTitles[position];
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
//            case 0:
//
//                FragmentCategory fragmentCategory=new FragmentCategory();
//                return  fragmentCategory;


            case 0:
                FragmentPagerReview fragmentPagerReview=new FragmentPagerReview();

                return  fragmentPagerReview;


            case 1:
               FragmentPagerRestInfromation fragmentPagerRestInfromation=new FragmentPagerRestInfromation();
                return  fragmentPagerRestInfromation;


        }

        return null;
    }


}
