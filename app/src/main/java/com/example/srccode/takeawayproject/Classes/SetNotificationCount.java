package com.example.srccode.takeawayproject.Classes;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.UI.BadgeDrawable;

/**
 * Created by ayman on 2017-08-19.
 */

public class SetNotificationCount {
    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);//action_cart ic_badge
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;

        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(String.valueOf(2));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
}
