package com.example.srccode.takeawayproject;

/**
 * Created by ayman on 2017-06-04.
 */

public class ClassPlaceOrder {


  private String Id;

   private String Name;
    private String price;
    private String quantity;
    private String additionname;
    private String additionId;
    private String drinkname;
    private String drinkId;


    public String getId() {return Id;}

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getadditionId() {
        return additionId;
    }
    public void setadditionId(String addId) {
        this.additionId = additionId;
    }
    public String getadditionname() {
        return additionname;
    }
    public void setaddtionname(String additionname) {
        this.additionname = additionname;
    }


    public String getdrinkId() {
        return drinkId;
    }
    public void setdrinkId(String drinkId) {
        this.drinkId = drinkId;
    }
    public String getdrinkname() {
        return drinkname;
    }
    public void setdrinkname(String drinkname) {
        this.drinkname = drinkname;
    }
}
