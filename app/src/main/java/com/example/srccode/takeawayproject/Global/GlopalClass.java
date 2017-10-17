package com.example.srccode.takeawayproject.Global;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.srccode.takeawayproject.Adapters.AdapterClassCategoryExpandList;
import com.example.srccode.takeawayproject.Adapters.AdapterClassItemExpandList;
import com.example.srccode.takeawayproject.Adapters.AdapterClassPlaceOrder;
import com.example.srccode.takeawayproject.Adapters.AdapterClassRestOffer;
import com.example.srccode.takeawayproject.Adapters.AdapterClassResturant;
import com.example.srccode.takeawayproject.Adapters.AdapterClientAdress;
import com.example.srccode.takeawayproject.Adapters.AdapterLastOrder;
import com.example.srccode.takeawayproject.ClassPlaceOrder;
import com.example.srccode.takeawayproject.Classes.ClassArea;
import com.example.srccode.takeawayproject.Classes.ClassCategory;
import com.example.srccode.takeawayproject.Classes.ClassCity;
import com.example.srccode.takeawayproject.Classes.ClassClientInformation;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;
import com.example.srccode.takeawayproject.Classes.ClassCountries;
import com.example.srccode.takeawayproject.Classes.ClassItemFood;
import com.example.srccode.takeawayproject.Classes.ClassItemFoodDetails;
import com.example.srccode.takeawayproject.Classes.ClassLastOrderDb;
import com.example.srccode.takeawayproject.Classes.ClassOrderStatus;
import com.example.srccode.takeawayproject.Classes.ClassResturants;
import com.example.srccode.takeawayproject.Classes.ClassViewOrderDb;
import com.example.srccode.takeawayproject.Classes.RetofitResturantClass;
import com.example.srccode.takeawayproject.service.AdapterResturantRetrofit;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayman on 2017-08-12.
 */

public class GlopalClass {
    ////   home
    public static String languagetype;
 public static int notificationCountCart = 0;
 //splash
 public static  ArrayList<ClassCookingDB> classeslistcooking= new ArrayList<ClassCookingDB>();
    public static ArrayList<String> cookingvalueslist ;//= new ArrayList<>();

    public static ArrayList<ClassCookingDB> classcookcatList;
 public static List<ClassCookingDB> tablecookingDB;

 public static ArrayList<ClassOrderStatus> classorderstatusList;

    public static int  restfragment=0;
 // for  main fragment
   public static ArrayAdapter<String> customadapter;
    public static List<ClassCountries> tableCountryDB;
    public static ArrayList<ClassCountries> classeslistcountry;
    public static List<ClassCity> tableCityDB;
    public static ArrayList<ClassCity> classeslistCity;
    public static List<ClassArea> tableRegionDB;
    public static ArrayList<ClassArea> streetclasslist;
    public static ArrayList<String> countryvalueslist = new ArrayList<>();
    public static ArrayList<String> cityvalueslist = new ArrayList<>();
    public static ArrayList<String> streetvalues = new ArrayList<>();
    public static int countrySelectedId;
    public static int citySelectedID;
    public static  String streetSelected = "";
    public static String _regionname;
    public static Typeface typeface;

    public static Spinner cityspinner, streetspinner, countryspinner;

    public static ImageView homeimgback;
    public static CountDownTimer cTimer = null;
    public static int resturantmapflag=0;


    //for resturant
    public static int resturantofferFlag=0;
    public static int findresturantmapflag=0;
    public static int  regionfoundbymap=0;
    public static int MapRegionID;

    public static ArrayList<ClassResturants> classResturantsList;
    public static ArrayList<ClassResturants> originalList;

    public static List<RetofitResturantClass>  retofitResturantClass;
    public static List<RetofitResturantClass> retofitoriginalList;
    public static AdapterResturantRetrofit adapterResturantRetrofit;


    public static AdapterClassResturant adapterClassResturant;
    public static List<ClassViewOrderDb> tableviewOrderDb;

    public static ListView listView;
    public static  SearchView mSearchView;
    public static int FeeTypeid;

    public static  int resturantDataId;

    public static double mincharge;

 public static int resturantId;
    public static String Restname;
    public static String Restimage;
    public static int Restrating;
    public static String Restopening;
    public static boolean iscooking;

    public static int currentusedresturantId;

 //resturant offer
 public static AdapterClassRestOffer adapterClassRestOffer;
 public static int offerID;
    public static int GlobalRegionID;

    ///// fragment category

    public static ProgressDialog PD;
    public static AdapterClassCategoryExpandList ExpAdapter;
    public static  ExpandableListView ExpandList;
    public static ArrayList<ClassCategory> grouplist;
    public static ArrayList<ClassItemFood> ch_list;
    public static int catid;

    public static String url ;

    public static int itemofferid;
    public static double itemOfferValue;
    public static int itemOfferFeeTypeId;

    /// item details


    //public static  ArrayAdapter<String> customadapter;

    public static  int quantity = 1;
    public static AdapterClassItemExpandList ExpitemAdapter;
    public static  ExpandableListView ExpanditemList;
    public static  ArrayList<ClassItemFood> groupitemlist;
    public static  ArrayList<ClassItemFoodDetails> ch_item_list;
    public static ArrayList<ClassItemFoodDetails> ch_list2;
    public static   boolean open=false;
    public static String itemfoodname = "chicken";
    public static  double itemfoodprice= 20.0;
    public static  String itemfoodImage= "https://aymanfikryeng.000webhostapp.com/Salads/salads2.png";
    public static  String itemid= "3";
//    String IDAddition= "1";



    public static int drinks;
    public static int additions;
    public static String drinksname;
    public static String additionsname;

    public static int catId;


    ////////////////////////resturant information

   public static TextView restregion,deliveryWay,feeType,feeTypeValue,openOrClose,payWay;
    public static int RegionId;
    public static String Regionname;


    public static String PayWay;
    public static String DeliveryWay;
    public static String FeeType;
    public static double FeeDeliveryValue;
    public static String OpenOrClose;
    public static double OfferResturantValue;
    public static int OfferFeeTypeId;
    public static double ResturantLatitude;
    public static double  ResturantLongitude;

    ///////////////pager  review
    public static  TextView reviewprice,reviewdelivery,reviewquality;
    public static  String Reviewquality,Reviewdelivery ,Reviewprice;



    ///// place order


    public static ArrayList<ClassPlaceOrder> classPlaceOrders;
    public static AdapterClassPlaceOrder adapterClassPlaceOrder;
    public static List<ClassViewOrderDb> tableViewOrderDB;

    public static  double fee=0.0;
    public static  String accesstoken;

   // activity last order

    public static ListView lastorderlist;
    public static AdapterLastOrder adapterlastOrder;

    public static List<ClassLastOrderDb> tableListLastOrderDB;
    public static ArrayList<ClassLastOrderDb> classLastOrderDBs;


 public static ClassLastOrderDb tableLastOrderDB;
    ////view client address
    public static List<ClassClientInformation> tableClientInformationDB;
    public static ArrayList<ClassClientInformation>  classViewOrderDB;

    public static  ListView clientadresslist;
    public static AdapterClientAdress adapterclientaddress;

////////// map
public static GoogleMap mMap;

 //Buttons
 public static ImageButton buttonSave;
 public static  ImageButton buttonCurrent;
 //Google ApiClient
 public static GoogleApiClient googleApiClient;

    public static  double Resturantlatitude;
    public static  double  Resturantlongitude;

    ///client information
    public static  double clientlongitude;
    public static  double clientlatitude;
    public static  String currentaddressfororder;
    public static  int  currentregionIdfororder;
    public static  String  currentregionfororder;
    public static  int  ClientInformationregionId;//for post esuer address
    public static  String ClientInformationaddressname;//for post esuer address

    public static  double   currentLatitudefororder;
    public static  double  currentLongtitudefororder;

    // Cobon
    public static  double   CobonValue=0.0;
    public static  int  cobonvauetype=1;
    //
    public static RecyclerView recyclerView;


// Host name
public static  String  HostName="takeawayapi.afshat.com";//"192.168.1.65:7742"  takeawayapi.afshat.com
}
