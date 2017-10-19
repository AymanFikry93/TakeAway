package com.example.srccode.takeawayproject.Classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.MenuItem;

import com.example.srccode.takeawayproject.Activities.R;

/**
 * Created by ayman on 2017-08-19.
 */

public class NotificationCountSetClass extends Activity {
    private static LayerDrawable icon;
    public NotificationCountSetClass() {
        //constructor
    }

    public static void setAddToCart(Context context, MenuItem item, int numMessages) {
//        icon = (LayerDrawable) item.getIcon();
//        icon= R.drawable.cart_white;
        SetNotificationCount.setBadgeCount(context, icon, NotificationCountSetClass.setNotifyCount(numMessages));

    }

    public static int setNotifyCount(int numMessages) {
        int count=numMessages;
        return count;

    }


}
