package com.example.srccode.takeawayproject.Activities;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;
import com.example.srccode.takeawayproject.Classes.ClassCountries;
import com.example.srccode.takeawayproject.WebServices.AreaJSONAsync;
import com.example.srccode.takeawayproject.WebServices.CookingJSONAsync;
import java.util.ArrayList;
import java.util.Locale;
import static com.example.srccode.takeawayproject.Global.GlopalClass.HostName;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classcookcatList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistcooking;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classeslistcountry;
import static com.example.srccode.takeawayproject.Global.GlopalClass.cookingvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.countryvalueslist;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableCountryDB;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tablecookingDB;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class SplashActivity extends AppCompatActivity {

    boolean isnetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ActiveAndroid.initialize(this);
        AssetManager am = getApplicationContext().getAssets();
        typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
        try{
            isnetwork=isNetworkConnected();


            if(isnetwork){
                StartAnimations();
                loadData();
            }
                   else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Need Network");
                builder.setMessage("This app needs to access network.");
                builder.show();
                StartAnimations();

//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG);
//                        .setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Snackbar snackbar1 = Snackbar.make(getApplicationContext(), "Need Network", Snackbar.LENGTH_SHORT);
//                                snackbar1.show();
//                            }
//                        });
               // snackbar.show();
            }
        }
        catch (Exception e){
            e.getMessage();
        }
    }
    public  void loadData(){

        try {

            tablecookingDB = new Select().from(ClassCookingDB.class).execute();

            if (tablecookingDB.size() == 0) {
                classcookcatList = new ArrayList<ClassCookingDB>();
                new CookingJSONAsync(getApplicationContext()).execute("http://"+ HostName+"/api/CookingCategories");
            }else {

                if (tablecookingDB.size() != 0) {
                    cookingvalueslist = new ArrayList<>();
                    for (int i = 0; i < tablecookingDB.size(); i++) {
                        classeslistcooking.add(tablecookingDB.get(i));
                        cookingvalueslist.add(i, classeslistcooking.get(i).getCookname());
                    }

                }
            }
            classeslistcountry = new ArrayList<ClassCountries>();
            tableCountryDB = new Select().from(ClassCountries.class).execute();
            if (tableCountryDB.size() != 0) {

                for (int i = 0; i < tableCountryDB.size(); i++) {
                    classeslistcountry.add(tableCountryDB.get(i));
                    countryvalueslist.add(i, classeslistcountry.get(i).getCountryname());
                }
            } else {
                new AreaJSONAsync(getApplicationContext()).execute("http://"+ HostName+"/api/Countries");
            }
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        anim.reset();
        //RelativeLayout l=(RelativeLayout) findViewById(R.id.activity_splash);
//        ImageView iv1 = (ImageView) findViewById(R.id.imageView);
//        iv1.clearAnimation();
//        iv1.startAnimation(anim);

         anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.imageView2);
        iv.clearAnimation();
        iv.startAnimation(anim);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                    finish();
                    if(isnetwork){

                        Intent intent = new Intent(getApplicationContext(), Home_MainActivity.class);//Home_MainActivity ActivityClientView
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                    }
                    else {

                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }



                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                }

            }
        };
        splashTread.start();

    }


}
