package com.example.srccode.takeawayproject.Classes;


import java.util.ArrayList;

/**
 * Created by ayman on 2017-05-17.
 */

public class ClassItemFood {

    private String Id;
    private String Name;
    private String Price;
    private String OfferType;
    private boolean IsCooking;
    private int itemofferid;
    private double itemOfferValue;
    private int itemOfferFeeTypeId;
    private String Image;

    public String getId() {
        return Id;
    }
    private ArrayList<ClassItemFoodDetails> Itemsdetails;
  //  private ClassItemFoodDetails Itemsdetails;

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getOfferType() {
        return OfferType;
    }

    public void setOfferType(String OfferType) {
        this.OfferType = OfferType;
    }



    public boolean getIsCooking() {
        return IsCooking;
    }

    public void setIsCooking(boolean IsCooking) {
        this.IsCooking = IsCooking;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public int getitemofferid() {
        return itemofferid;
    }

    public void setitemofferid(int itemofferid) {
        this.itemofferid = itemofferid;
    }
    public double getitemOfferValue() {
        return itemOfferValue;
    }

    public void setitemOfferValue(double itemOfferValue) {
        this.itemOfferValue = itemOfferValue;
    }
    public int getitemOfferFeeTypeId() {
        return itemOfferFeeTypeId;
    }

    public void setitemOfferFeeTypeId(int itemOfferFeeTypeId) {
        this.itemOfferFeeTypeId = itemOfferFeeTypeId;
    }

    public ArrayList<ClassItemFoodDetails> getItemsdetails() {
        return Itemsdetails;
    }
//public ClassItemFoodDetails getItemsdetails() {
//    return Itemsdetails;
//}
    public void setItemsdetails(ArrayList<ClassItemFoodDetails> Itemsdetails) {
        this.Itemsdetails = Itemsdetails;
    }
}

