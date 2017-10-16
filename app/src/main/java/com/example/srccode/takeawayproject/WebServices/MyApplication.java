package com.example.srccode.takeawayproject.WebServices;

/**
 * Created by ayman on 2017-05-21.
 */
import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;

public class MyApplication extends Application {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        try{


        }catch (Exception ex){
            ex.getMessage();
        }


        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getReqQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToReqQueue(Request<T> req, String tag) {

        getReqQueue().add(req);
    }

    public <T> void addToReqQueue(Request<T> req) {

        getReqQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        getReqQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new BitmapLruCache());
        }
        return this.mImageLoader;
    }

    public void cancelPendingReq(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}