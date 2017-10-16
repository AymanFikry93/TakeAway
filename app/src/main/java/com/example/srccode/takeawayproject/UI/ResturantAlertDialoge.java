package com.example.srccode.takeawayproject.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.example.srccode.takeawayproject.Activities.PagerActivity;
import com.example.srccode.takeawayproject.Activities.R;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;

import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;
import static com.example.srccode.takeawayproject.Global.GlopalClass.classResturantsList;
import static com.example.srccode.takeawayproject.Global.GlopalClass.currentusedresturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.resturantId;
import static com.example.srccode.takeawayproject.Global.GlopalClass.tableviewOrderDb;

/**
 * Created by ayman on 2017-08-12.
 */

public class ResturantAlertDialoge extends AppCompatActivity {

    Context mcontext;
    int position;
    public  ResturantAlertDialoge(Context context,int mposition){
        mcontext=context;
        position=mposition;



    if (tableviewOrderDb.size()!=0 && currentusedresturantId!=Integer.parseInt(classResturantsList.get(position).getId())){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mcontext.getApplicationContext());

        // Setting Dialog Title
        alertDialog.setTitle("Warning...");

        // Setting Dialog Message
        alertDialog.setMessage("this order from another resturant,your order will be lost");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.cleararea);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                new Delete().from(ClassViewOrderDb.class).execute();

                // Write your code here to invoke YES event
                Toast.makeText(mcontext, "You cleared your order", Toast.LENGTH_SHORT).show();
                Toast.makeText(mcontext, classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
                Intent ResturantsIntent = new Intent(mcontext, PagerActivity.class);
                resturantId = Integer.parseInt(classResturantsList.get(position).getId());
                Restname = classResturantsList.get(position).getName();
                Restimage = classResturantsList.get(position).getImage();
                Restrating = classResturantsList.get(position).getRating();
                Restopening = classResturantsList.get(position).getopenandclose();
                currentusedresturantId=resturantId;
               // startActivity(ResturantsIntent);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mcontext, "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();

                return ;
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
    else{
        Toast.makeText(mcontext, classResturantsList.get(position).getName(), Toast.LENGTH_LONG).show();
        Intent ResturantsIntent = new Intent(mcontext, PagerActivity.class);
        resturantId = Integer.parseInt(classResturantsList.get(position).getId());
        Restname = classResturantsList.get(position).getName();
        Restimage = classResturantsList.get(position).getImage();
        Restrating = classResturantsList.get(position).getRating();
        Restopening = classResturantsList.get(position).getopenandclose();

        currentusedresturantId =resturantId;

        ResturantsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.getApplicationContext().startActivity(ResturantsIntent);

    }
}
}