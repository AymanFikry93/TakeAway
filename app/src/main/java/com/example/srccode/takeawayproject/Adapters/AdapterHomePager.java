package com.example.srccode.takeawayproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.example.srccode.takeawayproject.Activities.FragmentHomeResturant;
import com.example.srccode.takeawayproject.Activities.FragmentLastOrder;
import com.example.srccode.takeawayproject.Activities.MainFragment;

import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantofferFlag;

/**
 * Created by ayman on 2017-08-14.
 */

public class AdapterHomePager extends FragmentStatePagerAdapter {
    final int pageCount=3;
    Context context;
    private  String tabTitles []=new  String[]{"Resturantn by Regions","Last Order","Resturantn by offers "};

    public AdapterHomePager(FragmentManager fm) {
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
    public Fragment getItem(int  position) {
        switch (position){
            case 0:

                MainFragment fragmentCategory=new MainFragment();
                return  fragmentCategory;


            case 1:
                FragmentLastOrder fragmentPagerlastorder=new FragmentLastOrder();
                return  fragmentPagerlastorder;
            case 2:
                FragmentHomeResturant fragmentPageOffer=new FragmentHomeResturant();
                //   resturantofferFlag=1;
                return  fragmentPageOffer;


        }

        return null;
    }
//    @Override
//    public int getItemPosition(Object object) {
//        // this method will be called for every fragment in the ViewPager
//        if (object instanceof MainFragment) {
//            return POSITION_UNCHANGED; // don't force a reload
//        } else if (object instanceof FragmentHomeResturant) {
//            return POSITION_UNCHANGED; // don't force a reload
//        }
//        else {
//            // POSITION_NONE means something like: this fragment is no longer valid
//            // triggering the ViewPager to re-build the instance of this fragment.
//            return POSITION_NONE;
//        }
//    }


}
