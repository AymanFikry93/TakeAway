package com.example.srccode.takeawayproject.Classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ayman on 2017-05-18.
 */
@Table(name = "RegionDB1")
public class ClassArea extends Model {
    @Column(name = "regionName")

    private String Streetname ;
    @Column(name = "regionid")
    private String Id ;
    @Column(name = "cityId")
    private String CityId ;
    public ClassArea() {


    }
    public ClassArea(String _Streetname, String _Id,String cityId) {
        Streetname = _Streetname;

        Id=_Id;
        CityId=cityId;

    }
    public String getStreetname() {
        return Streetname;
    }
    public void setStreetname(String Streetname) {
        this.Streetname = Streetname;
    }


    public String getregionId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCityId() {
        return CityId;
    }
    public void setCityId(String CityId) {
        this.CityId = CityId;
    }



}
