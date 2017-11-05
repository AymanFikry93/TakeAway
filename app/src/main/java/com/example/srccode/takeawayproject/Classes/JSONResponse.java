package com.example.srccode.takeawayproject.Classes;

import java.util.ArrayList;

/**
 * Created by ayman on 2017-10-01.
 */

public class JSONResponse {

    private ArrayList<RetofitResturantClass> RestaurantDataList;

    public ArrayList<RetofitResturantClass> getRetrofirRetofitResturant() {
        return  RestaurantDataList;
    }
    public void SetRetrofirRetofitResturant(ArrayList<RetofitResturantClass> RetofitResturant) {
        this.RestaurantDataList=RetofitResturant;
    }

}
