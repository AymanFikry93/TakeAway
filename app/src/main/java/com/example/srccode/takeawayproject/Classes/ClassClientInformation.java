package com.example.srccode.takeawayproject.Classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ayman on 2017-06-04.
 */
@Table(name = "ClientInfromation2")
public class ClassClientInformation extends Model {


    @Column(name = "adress")
    private String adress ;
    @Column(name = "regionId")
    private int regionId ;
    @Column(name = "regionName")
    private String regionname ;

    @Column(name = "mapLatitude")

    private double mapLatitude ;
    @Column(name = "mapLongtitude")

    private double mapLongtitude ;

    public ClassClientInformation( ) {

    }

    public ClassClientInformation( String adress,int regionId,String regionname,double mapLatitude,double mapLongtitude) {

        this.adress = adress;
        this.regionId = regionId;
        this.regionname = regionname;

        this.mapLatitude = mapLatitude;
        this.mapLongtitude = mapLongtitude;

    }



    public String getadress() {
        return adress;
    }
    public void setadress(String adress) {
        this.adress = adress;
    }
    public int getregionId() {
        return regionId;
    }
    public void setregionId(int regionId) {
        this.regionId = regionId;
    }

    public String getregionname() {
        return regionname;
    }
    public void setregionname(String regionname) {
        this.regionname = regionname;
    }

    public double getregionmapLatitude() {
        return mapLatitude;
    }
    public void setregionmapLatitude(double mapLatitude) {
        this.mapLatitude = mapLatitude;
    }

    public double getregionmapLongtitude() {
        return mapLongtitude;
    }
    public void setregionmapLongtitude(double mapLongtitude) {
        this.mapLongtitude = mapLongtitude;
    }





}
