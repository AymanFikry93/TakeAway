package com.example.srccode.takeawayproject.Classes;

import android.content.Context;

import com.example.srccode.takeawayproject.Activities.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ayman on 2017-09-28.
 */

public class RetofitResturantClass {
    @SerializedName("RestID")
    @Expose
    private String Id ;
    @SerializedName("RestDataID")
    @Expose
    private int ResturantDataId ;

    @SerializedName("RestDatanameAr")//R.string.   RestNameAr
    @Expose
    private String name ;
    @SerializedName("RestDatanameEn")//R.string.   RestNameAr
    @Expose
    private String engname ;
    @SerializedName("OpenorCloseEn")
    @Expose
    private String openandclose ;
    @SerializedName("MinimumOrderPrice")
    @Expose
    private String mincharge ;
//    @SerializedName("Restrating")
//    @Expose
    private int rating ;
    @SerializedName("RestImg")
    @Expose
    private String image ;
    @SerializedName("DeliveryValue")
    @Expose
    private double FeeDeliveryValue ;
    @SerializedName("OfferID")
    @Expose
    private int offerID ;
    @SerializedName("OfferValue")
    @Expose
    private double offerValue ;
    @SerializedName("OfferFeeTypeId")
    @Expose
    private int OfferFeeTypeId ;

    public RetofitResturantClass( ) {

    }

    //, double rating
    public RetofitResturantClass(String Id, int ResturantDataId,String name, String openandclose, String mincharge, int rating,String image,double offerValue ,int OfferFeeTypeId
   ,int offerID,double FeeDeliveryValue ) {
        this.Id = Id;
        this.ResturantDataId = ResturantDataId;
        this.name = name;
        this.openandclose = openandclose;
        this.mincharge = mincharge;
        this.rating = rating;
        this.image = image;
        this.offerValue = offerValue;
        this.offerID = offerID;
        this.OfferFeeTypeId = OfferFeeTypeId;
        this.FeeDeliveryValue = FeeDeliveryValue;


    }

    public String getId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }

    public int getresturantDataId() {
        return ResturantDataId;
    }
    public void setresturantDataId(int ResturantDataId) {
        this.ResturantDataId = ResturantDataId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEngName() {
        return engname;
    }
    public void setENGName(String engname) {
        this.engname = engname;
    }

    public String getopenandclose() {
        return openandclose;
    }
    public String getmincharge() {
        return mincharge;
    }

    public int getRating() { return rating; }



    public void setopenandclose(String openandclose) {
        this.openandclose = openandclose;
    }
    public void setmincharge(String mincharge) {
        this.mincharge = mincharge;
    }

    public void setRating(int rating) {  this.rating = rating;}

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public double getFeeDeliveryValue() {
        return FeeDeliveryValue;
    }
    public void setFeeDeliveryValue(double FeeDeliveryValue) {
        this.FeeDeliveryValue = FeeDeliveryValue;
    }


    public int getofferID() {
        return offerID;
    }
    public void setofferID(int offerID) {
        this.offerID = offerID;
    }


    public int getOfferFeeTypeId() {
        return OfferFeeTypeId;
    }
    public void setOfferFeeTypeId(int OfferFeeTypeId) {
        this.OfferFeeTypeId = OfferFeeTypeId;
    }



    public double getofferValue() {
        return offerValue;
    }
    public void setofferValue(double offerValue) {
        this.offerValue = offerValue;
    }






}
