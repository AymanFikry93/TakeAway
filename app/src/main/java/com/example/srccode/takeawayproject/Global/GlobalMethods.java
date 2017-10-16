package com.example.srccode.takeawayproject.Global;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.activeandroid.ActiveAndroid;
import com.example.srccode.takeawayproject.Activities.PagerActivity;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassOrderStatus;
import com.example.srccode.takeawayproject.WebServices.OrderStatusJsonAsync;

import java.util.ArrayList;

import static com.example.srccode.takeawayproject.Global.GlopalClass.PD;
import static com.example.srccode.takeawayproject.Global.GlopalClass.additions;
import static com.example.srccode.takeawayproject.Global.GlopalClass.additionsname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classcookcatList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classorderstatusList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.drinks;
import static com.example.srccode.takeawayproject.Global.GlopalClass.drinksname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.groupitemlist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodImage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemfoodprice;
import static com.example.srccode.takeawayproject.Global.GlopalClass.itemid;
import static com.example.srccode.takeawayproject.Global.GlopalClass.quantity;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;

/**
 * Created by ayman on 2017-08-12.
 */

public class GlobalMethods {
    Context mcontext;
    public  GlobalMethods(Context context){
        mcontext=context;

    }


    public void alert(){
        PD = new ProgressDialog(mcontext);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

    }

    public void statusnotify(){
        classorderstatusList = new ArrayList<ClassOrderStatus>();
         new OrderStatusJsonAsync(mcontext).execute("http://192.168.1.65:7742/api/Restaurants");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mcontext)
                       // .setSmallIcon(R.drawable.hotels_select_blue)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

// Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);

//When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//

        mNotificationManager.notify(001, mBuilder.build());

    }

}
