package com.example.srccode.takeawayproject.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ayman on 2017-09-28.
 */

public class RetrofitClass {
    // Trailing slash is needed
    public static final String BASE_URL = "http://takeawayapi.afshat.com";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
