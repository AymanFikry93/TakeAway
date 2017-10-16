package com.example.srccode.takeawayproject.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ayman on 2017-09-28.
 */

public class RetofitResturantClass {
    @SerializedName("RestID")
    @Expose
    private String Id ;
    @SerializedName("RestNameAr")
    @Expose
    private String name ;
    @SerializedName("OpenorCloseEn")
    @Expose
    private String openandclose ;
    @SerializedName("MinimumOrderPrice")
    @Expose
    private String mincharge ;
//    @SerializedName("Restrating")
//    @Expose
    private int rating ;
//    @SerializedName("Restimage")
//    @Expose
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
    public RetofitResturantClass(String Id, String name, String openandclose, String mincharge, int rating,String image,double offerValue ,int OfferFeeTypeId
    ) {
        this.Id = Id;
        this.name = name;
        this.openandclose = openandclose;
        this.mincharge = mincharge;
        this.rating = rating;
        this.image = image;
        this.offerValue = offerValue;
        this.OfferFeeTypeId = OfferFeeTypeId;


    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getopenandclose() {
        return openandclose;
    }
    public String getmincharge() {
        return mincharge;
    }

    public int getRating() { return rating; }


    public void setId(String Id) {
        this.Id = Id;
    }
    public void setName(String name) {
        this.name = name;
    }

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
