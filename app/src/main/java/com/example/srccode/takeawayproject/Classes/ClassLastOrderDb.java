package com.example.srccode.takeawayproject.Classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ayman on 2017-06-12.
 */
@Table(name ="lastorderdb")
public class ClassLastOrderDb extends Model {

    @Column(name = "orderid")
    private String orderid ;

    @Column(name = "OrderName")
    private String Ordername ;
    //
    @Column(name = "OrderPrice")
    private String Orderprice ;

    @Column(name = "OrderImage")
    private String Orderimage ;

    @Column(name = "quantity")
    private String Orderquantity;


    @Column(name = "additionname")
    private String additionName;

    @Column(name = "additionid")
    private String additionID;

    @Column(name = "drinksName")
    private String drinksName;

    @Column(name = "drinksid")
    private String drinksID;
    @Column(name = "itemofferorderedid")
    private int itemofferorderedID;
    @Column(name = "CookingCatorderedid")
    private int CookingCatorderedID;
    @Column(name = "SelectedResturantid")
    private int SelectedResturantID;
    public ClassLastOrderDb( ) {}

    public ClassLastOrderDb(String orderid, String Ordername, String Orderprice ,String Orderimage,String Orderquantity
            ,String drinksID,String drinksName,String additionID,String additionName,int itemofferorderedID,int CookingCatorderedID,int SelectedResturantID) {
        this.orderid = orderid;
        this.Ordername = Ordername;
        this.Orderprice = Orderprice;
        this.Orderimage = Orderimage;
        this.Orderquantity = Orderquantity;
        this.additionID = additionID;
        this.additionName = additionName;
        this.drinksID = drinksID;
        this.drinksName = drinksName;
        this.itemofferorderedID = itemofferorderedID;
        this.CookingCatorderedID = CookingCatorderedID;
        this.SelectedResturantID = SelectedResturantID;

    }

    public String getorderid() {return orderid;}
    public void setorderid(String orderid) { this.orderid = orderid; }

    public String getOrderprice() {
        return Orderprice;
    }
    public void setOrderprice(String Orderprice) { this.Orderprice = Orderprice;}

    public String getOrdername() {
        return Ordername;
    }
    public void setOrdername(String Ordername) { this.Ordername = Ordername;}

    public String getOrderimage() {
        return Orderimage;
    }
    public void setOrderimage(String Orderimage) {
        this.Orderimage = Orderimage;
    }


    public String getOrderquantity() {
        return Orderquantity;
    }
    public void setOrderquantity(String Orderquantity) {
        this.Orderquantity = Orderquantity;
    }


    public String getadditionID() {
        return additionID;
    }
    public void setadditionID(String additionID) {
        this.additionID = additionID;
    }

    public String getadditionName() {
        return additionName;
    }
    public void setadditionName(String additionName) {
        this.additionName = additionName;
    }

    public String getdrinksID() {
        return drinksID;
    }
    public void setdrinksID(String drinksID) {
        this.drinksID = drinksID;
    }

    public String getdrinksName() {
        return drinksName;
    }
    public void setdrinksName(String drinksName) {
        this.drinksName = drinksName;
    }

    public int getitemofferorderedID() {
        return itemofferorderedID;
    }
    public void setitemofferorderedID(int itemofferorderedID) {
        this.itemofferorderedID = itemofferorderedID;
    }

    public int getCookingCatorderedID() {
        return CookingCatorderedID;
    }
    public void setCookingCatorderedID(int CookingCatorderedID) {
        this.CookingCatorderedID = CookingCatorderedID;
    }

    public int getSelectedResturantID() {
        return SelectedResturantID;
    }
    public void setSelectedResturantID(int SelectedResturantID) {
        this.SelectedResturantID = SelectedResturantID;
    }
}
