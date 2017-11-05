package com.example.srccode.takeawayproject.service;

import  com.example.srccode.takeawayproject.Classes.JSONResponse;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ayman on 2017-09-28.
 */

public interface RetrofitService {
    @GET("api/Restaurants?RegionID=1")
    Call<JSONResponse> getResturants();
    @GET("api/Restaurants")//?RegionID={GlobalRegionID}
    Call<JSONResponse> getJSON(@Query("RegionID") int regionid);//@Path("GlobalRegionID") int regionid);


    @GET("api/Restaurants?RegionID={regionid}")
      Observable<ArrayList<RetofitResturantClass>> getUser(@Path("regionid") int regionid);

    @GET("api/Restaurants?RegionID=1")
    Observable<JSONResponse> getobserveResturant();
}
