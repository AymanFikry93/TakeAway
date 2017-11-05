package com.example.srccode.takeawayproject.service;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ayman on 2017-09-28.
 */

public class RetrofitClass {
    // Trailing slash is needed
    public static final String BASE_URL = "http://takeawayapi.afshat.com";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
             .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
