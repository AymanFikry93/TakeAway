package com.example.srccode.takeawayproject.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.srccode.takeawayproject.Adapters.ViewPagerAdapter;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.NotificationCountSetClass;
import com.example.srccode.takeawayproject.WebServices.MyApplication;
import java.util.List;

import static com.example.srccode.takeawayproject.Global.GlopalClass.FeeDeliveryValue;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restimage;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restname;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restopening;
import static com.example.srccode.takeawayproject.Global.GlopalClass.Restrating;
import static com.example.srccode.takeawayproject.Global.GlopalClass.languagetype;
import static com.example.srccode.takeawayproject.Global.GlopalClass.mincharge;
import static com.example.srccode.takeawayproject.Global.GlopalClass.notificationCountCart;
import static com.example.srccode.takeawayproject.Global.GlopalClass.typeface;

public class PagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

        TextView restName =(TextView)findViewById(R.id.RestName);
        restName.setText(Restname);
        restName.setTypeface(typeface);

        TextView minchargeName =(TextView)findViewById(R.id.minchargeid);
        minchargeName.setText(mincharge+"");
        TextView restdeliveryName =(TextView)findViewById(R.id.restdeliveryid);
        restdeliveryName.setText(FeeDeliveryValue+"");
        restdeliveryName.setTypeface(typeface);

        restName.setTypeface(typeface);
        TextView restopened =(TextView)findViewById(R.id.Restopening);
        restopened.setText(Restopening);
        restopened.setTypeface(typeface);

         RatingBar restRating=(RatingBar)findViewById(R.id.Restrating_bar);
        restRating.setRating(Restrating);

        LayerDrawable stars = (LayerDrawable) restRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255,131,66), PorterDuff.Mode.SRC_ATOP);
        ImageView restimageView= (ImageView)findViewById(R.id.Resturantlogo);
          Glide.with(PagerActivity.this).load(Restimage).apply(RequestOptions.circleCropTransform()).into(restimageView);


        mViewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
       mViewPager.setCurrentItem(0);
       tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {

//            tabLayout.getTabAt(0).setIcon(R.drawable.hotels_select_blue);
//            tabLayout.getTabAt(1).setIcon(R.drawable.rating);
//            tabLayout.getTabAt(2).setIcon(R.drawable.orders1_select_blue);

//            tabLayout.getTabAt(0).setText(getString(R.string.Menu));
            tabLayout.getTabAt(0).setText(getString(R.string.Reviews));
            tabLayout.getTabAt(1).setText(getString(R.string.ResturantInformation));
        }

        ImageButton imageButton=(ImageButton)findViewById(R.id.backtoresturantbtn);

        if(languagetype.equals("ar")){
            Drawable d = ResourcesCompat.getDrawable(getResources(),R.drawable.next,null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageButton.setBackground(d);
            }
        }else
        {
            Drawable d = ResourcesCompat.getDrawable(getResources(),R.drawable.back_arrow,null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageButton.setBackground(d);
            }

        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityCategory.class);
                startActivity(ResturantsIntent);
            }
        });

//        Button cartimageButton=(Button)findViewById(R.id.backtovieworderbtn);

//        ActiveAndroid.initialize(this);
//        List<ClassViewOrderDb> tableviewOrderDb;
//        tableviewOrderDb =new Select().from(ClassViewOrderDb.class).execute();
//
//        if(tableviewOrderDb.size()==0){//&&!Restopening.equals("Opening")
//            cartimageButton.setVisibility(View.GONE);
//        }else {
//            cartimageButton.setVisibility(View.VISIBLE);
//            String viewOrderDbCount= String.valueOf(tableviewOrderDb.size());
//            cartimageButton.setText(viewOrderDbCount);
//        }
//        cartimageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityViewOrder.class);
//                startActivity(ResturantsIntent);
//            }
//        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_cart);
        NotificationCountSetClass.setAddToCart(PagerActivity.this, item,notificationCountCart);
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.chartmenu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_cart:
//                Intent ResturantsIntent = new Intent(getApplicationContext(), ActivityViewOrder.class);
//                startActivity(ResturantsIntent);
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
