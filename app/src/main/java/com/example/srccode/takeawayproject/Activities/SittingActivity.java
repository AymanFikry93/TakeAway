package com.example.srccode.takeawayproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class SittingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        setSupportActionBar(mToolbar);
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.action_settings);
        ImageButton imageButton=(ImageButton)findViewById(R.id.next_btn_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), Home_MainActivity.class);
                startActivity(homeIntent);
            }
        });
        RadioGroup languagegroup=(RadioGroup)findViewById(R.id.langgroup);
        String lang = Locale.getDefault().getDisplayLanguage();

        if (lang.equals("English")){
            languagegroup.check(R.id.englanguage);

        }else {

            languagegroup.check(R.id.arlanguague);

        }
        languagegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.englanguage:

                        String languageToLoad = "en"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getApplicationContext().getResources().updateConfiguration(config,
                                getApplicationContext().getResources().getDisplayMetrics());
                        SharedPreferences sharedPreferences_save= getApplicationContext().getSharedPreferences("languagedata", MODE_PRIVATE);
                        SharedPreferences.Editor editor_save=sharedPreferences_save.edit();
                        editor_save.putString("languagetype",languageToLoad);  // to sava the data
                        editor_save.commit();

                        break;
                    case R.id.arlanguague:

                        languageToLoad = "ar"; // your language
                        locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        config = new Configuration();
                        config.locale = locale;
                        getApplicationContext().getResources().updateConfiguration(config,
                                getApplicationContext().getResources().getDisplayMetrics());
                        sharedPreferences_save= getApplicationContext().getSharedPreferences("languagedata", MODE_PRIVATE);
                        editor_save=sharedPreferences_save.edit();
                        editor_save.putString("languagetype",languageToLoad);  // to sava the data
                        editor_save.commit();

                        break;
                    default:
                        break;
                }
            }
        });

//        SharedPreferences sharedPreferences_notify_show=getApplicationContext().getSharedPreferences("notifydata", Context.MODE_PRIVATE);// to get the contetnt of the data base
//        boolean notify =sharedPreferences_notify_show.getBoolean("notifytype",false);
//        final Switch notifySwitch=(Switch) view.findViewById(R.id.notify_switch);
//        notifySwitch.setChecked(notify);
//        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                SharedPreferences sharedPreferences_save_notify= getApplicationContext().getSharedPreferences("notifydata", MODE_PRIVATE);
//                SharedPreferences.Editor editor_save_notify=sharedPreferences_save_notify.edit();
//                editor_save_notify.putBoolean("notifytype",isChecked);  // to sava the data
//                editor_save_notify.commit();
//
//                Intent HomeActivity = new Intent(getApplicationContext(),Home_MainActivity.class);
//                startActivity(HomeActivity);
//            }
//        });


    }

}
