package com.example.srccode.takeawayproject.service;

import com.example.srccode.takeawayproject.Classes.JSONResponse;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ayman on 2017-09-28.
 */

public interface RetrofitService {
    @GET("api/Restaurants?RegionID=1")
    Call<List<RetofitResturantClass>> getMovies();
    @GET("api/Restaurants?RegionID=1")
    Call<JSONResponse> getJSON();
}
