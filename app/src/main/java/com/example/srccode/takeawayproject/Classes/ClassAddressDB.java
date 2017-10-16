package com.example.srccode.takeawayproject.Classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ayman on 2017-06-07.
 */
@Table(name = "Address")
public class ClassAddressDB extends Model{


    @Column(name = "region")
    private String region ;
    @Column(name = "street")
    private String street ;

    @Column(name = "floornum")
    private String floornum ;

    @Column(name = "apartmentnum")
    private String apartmentnum ;
    //
    @Column(name = "buildingnum")
    private String buildingnum ;

    @Column(name = "mobilenum")
    private String mobilenum ;

    public ClassAddressDB( ) {}

    public ClassAddressDB(String region, String street, String floornum, String apartmentnum, String buildingnum ,String mobilenum) {
        this.region = region;
        this.street = street;
        this.floornum = floornum;
        this.apartmentnum = apartmentnum;
        this.buildingnum = buildingnum;
        this.mobilenum = mobilenum;



    }
    public String getregion() {return region;}
    public void setregion(String region) { this.region = region; }

    public String getstreet() {return street;}
    public void setstreet(String street) { this.street = street; }

    public String getfloornum() {return floornum;}
    public void setfloornum(String floornum) { this.floornum = floornum; }

    public String getapartmentnum() {
        return apartmentnum;
    }
    public void setapartmentnum(String apartmentnum) { this.apartmentnum = apartmentnum;}

    public String getbuildingnum() {
        return buildingnum;
    }
    public void setbuildingnum(String buildingnum) { this.buildingnum = buildingnum;}

    public String getmobilenum() {
        return mobilenum;
    }
    public void setmobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

}
