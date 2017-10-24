package com.example.srccode.takeawayproject.UI;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by ayman on 2017-10-23.
 */

public class CustomTextView extends TextView {


    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        AssetManager am = context.getAssets();

        this.setTypeface(Typeface.createFromAsset(am,
                String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf")));


    }
}
