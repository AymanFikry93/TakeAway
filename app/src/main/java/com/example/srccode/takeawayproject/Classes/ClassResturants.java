package com.example.srccode.takeawayproject.Classes;

/**
 * Created by ayman on 2017-05-14.
 */

public class ClassResturants {
    private String Id ;
    private int ResturantDataId ;

    private String name ;
    private String openandclose ;
    private String mincharge ;
   private int rating ;

  private String image ;
    private double FeeDeliveryValue ;
    private int offerID ;
    private double offerValue ;
    private int OfferFeeTypeId ;
    private double Longitude ;
    private double Latitude ;

    public ClassResturants( ) {

    }

                                           //, double rating
    public ClassResturants(String Id, int ResturantDataId,String name, String openandclose, String mincharge, int rating,String image,double offerValue
            ,int OfferFeeTypeId,double FeeDeliveryValue,int offerID
                       ) {
        this.Id = Id;
        this.ResturantDataId = ResturantDataId;
        this.name = name;
        this.openandclose = openandclose;
        this.mincharge = mincharge;
        this.rating = rating;
        this.image = image;
        this.offerValue = offerValue;
        this.OfferFeeTypeId = OfferFeeTypeId;
        this.offerID = offerID;
        this.FeeDeliveryValue = FeeDeliveryValue;



    }

    public String getId() {
        return Id;
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



    public double getLongitude() {
        return Longitude;
    }
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }
    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }



}
