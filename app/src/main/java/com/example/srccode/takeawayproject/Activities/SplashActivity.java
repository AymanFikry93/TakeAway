package com.example.srccode.takeawayproject.Activities;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;
import com.example.srccode.takeawayproject.Classes.ClassCountries;
import com.example.srccode.takeawayproject.WebServices.AreaJSONAsync;
import com.example.srccode.takeawayproject.WebServices.CookingJSONAsync;
import com.example.srccode.takeawayproject.WebServices.NotificationClass;

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

    ProgressBar bar;
    boolean isnetwork;
//    int PERMISSION_CALLBACK_CONSTANT = 100;
//    int REQUEST_PERMISSION_SETTING = 101;
//    String[] permissionsRequired = new String[]{
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION};
//    private SharedPreferences permissionStatus;
//    private boolean sentToSettings = false;

    boolean gps_enabled = false;
    boolean network_enabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        bar = (ProgressBar) findViewById(R.id.progressBar);
//        textView = (TextView) findViewById(R.id.textView);
        bar.setProgress(0);
        bar.setMax(100);
        ActiveAndroid.initialize(this);
        AssetManager am = getApplicationContext().getAssets();
        typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "Fonts/%s", "GESSTwoLight.otf"));
//        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
//
//        if(ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
//                ){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[0])
//                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[1])
//                    ){
//                //Show Information about why you need the permission
//                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//                builder.setTitle("Need Multiple Permissions");
//                builder.setMessage("This app needs Camera and Location permissions.");
//                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        ActivityCompat.requestPermissions(SplashActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();
//            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
//                //Previously Permission Request was cancelled with 'Dont Ask Again',
//                // Redirect to Settings after showing Information about why you need the permission
//                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//                builder.setTitle("Need Multiple Permissions");
//                builder.setMessage("This app needs Camera and Location permissions.");
//                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        sentToSettings = true;
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
//                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();
//            }  else {
//                //just request the permission
//                SharedPreferences.Editor editor = permissionStatus.edit();
//                editor.putBoolean(permissionsRequired[0],true);
//                editor.commit();
//                ActivityCompat.requestPermissions(SplashActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
//            }
////          permision required
//
//        } else {
//            //You already have the permission, just go ahead.
//            Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
//        }







//        int a=ActivityCompat.checkSelfPermission(SplashActivity.this,Manifest.permission.ACCESS_NETWORK_STATE);
//        if(ActivityCompat.checkSelfPermission(SplashActivity.this,Manifest.permission.ACCESS_NETWORK_STATE)
//                != PackageManager.PERMISSION_GRANTED)
//        {
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
//            // Setting Dialog Title
//            alertDialog.setTitle("Network  settings");
//
//            // Setting Dialog Message
//            alertDialog.setMessage("Network is not enabled. Do you want to go to Network menu?");
//
//            // On pressing Settings button
//            alertDialog.setPositiveButton("Network", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog,int which) {
//                    Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
//                    startActivity(intent);
//                }
//            });
//
//            // on pressing cancel button
//            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//
//            // Showing Alert Message
//            alertDialog.show();
//        }
//        else{
//            ActivityCompat.requestPermissions(SplashActivity.this,new String[]{
//                    Manifest.permission.ACCESS_NETWORK_STATE
//            },1);
//
//            new ProgressTask().execute();
//            loadData();
//        }

//"http://"+ HostName+"/api/GetNotificationMsgsForUserCategory?UserCategory=1"
       // new NotificationClass(getApplicationContext()).execute("http://192.168.1.65:7742/api/GetNotificationMsgsForUserCategory?UserCategory=1", ""); //http://192.168.1.65:7742/api   takeawayapi.afshat.com/api

       isNetworkConnected();
       // new ProgressTask().execute();
       // loadData();
//        api/GetNotificationMsgsForUserCategory?UserCategory=1

//        try{
//            isnetwork=isNetworkConnected();
//            if(isnetwork){
//               // StartAnimations();
//                new ProgressTask().execute();
//                loadData();
//            }
//                   else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Need Network");
//                builder.setMessage("This app needs to access network.");
//                builder.show();
//                StartAnimations();
//                new ProgressTask().execute();
//            }
//        }
//        catch (Exception e){
//            e.getMessage();
//        }
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
//            classeslistcountry = new ArrayList<ClassCountries>();
//            tableCountryDB = new Select().from(ClassCountries.class).execute();
//            if (tableCountryDB.size() != 0) {
//
//                for (int i = 0; i < tableCountryDB.size(); i++) {
//                    classeslistcountry.add(tableCountryDB.get(i));
//                    countryvalueslist.add(i, classeslistcountry.get(i).getCountryname());
//                }
//            } else {
//                new AreaJSONAsync(getApplicationContext()).execute("http://"+ HostName+"/api/Countries");
//            }
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_PERMISSION_SETTING) {
//            if (ActivityCompat.checkSelfPermission(MultiplePermissionsActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
//                //Got Permission
//                proceedAfterPermission();
//            }
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
//            //check if all permissions are granted
//            boolean allgranted = false;
//            for(int i=0;i<grantResults.length;i++){
//                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
//                    allgranted = true;
//                } else {
//                    allgranted = false;
//                    break;
//                }
//            }
//
//            if(allgranted){
//                Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
//
//            } else if(ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[0])
//                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[1])
//                    ){
//                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//                builder.setTitle("Need Multiple Permissions");
//                builder.setMessage("This app needs Camera and Location permissions.");
//                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        ActivityCompat.requestPermissions(SplashActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();
//            } else {
//                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
    private void isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       // String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//        if (locationProviders == null || locationProviders.equals("")) {
//            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("gps_network_not_enabled");
            dialog.setPositiveButton("open_location_settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    try {
                        wait(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //get gps
                    if(gps_enabled && network_enabled) {
                        new ProgressTask().execute();
                        loadData();
                    }
                    else {
                      //  isNetworkConnected();
                        finish();
                        startActivity(getIntent());
                    }


                }
            });
            dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
//                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                    finish();
                startActivity(getIntent());
                }
            });
            dialog.show();
        }
        else{
            new ProgressTask().execute();
            loadData();

        }
       // return cm.getActiveNetworkInfo() != null;
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        anim.reset();
        //RelativeLayout l=(RelativeLayout) findViewById(R.id.activity_splash);
//        ImageView iv1 = (ImageView) findViewById(R.id.imageView);
//        iv1.clearAnimation();
//        iv1.startAnimation(anim);
//         anim = AnimationUtils.lo adAnimation(this, R.anim.translate);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.imageView2);
//        iv.clearAnimation();
//        iv.startAnimation(anim);

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

    class ProgressTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // code will executed before task start (main thread)
        }

        @Override
        protected String doInBackground(String... params) {
            // task will done in background

            for (int i = 0; i < 100; i++) {
                try {
                    // sleep 100 millisecond every loop so progress will not finished fast with out see it
                    Thread.sleep(100);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // code executed after task finish hide progress and change text
            bar.animate().alpha(0).setDuration(2000).start();
//            textView.setText("Don't wait any thing Do it Now");
          //  if(isnetwork){
                Intent intent = new Intent(getApplicationContext(), Home_MainActivity.class);//Home_MainActivity ActivityClientView
                startActivity(intent);
//            }else {
//                Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
//                    startActivity(intent);
//                finish();
//                startActivity(getIntent());
//            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // progress come as array maybe there is mote than one value or progress update so i put [0]
            bar.setProgress(values[0]);


        }


    }

}
