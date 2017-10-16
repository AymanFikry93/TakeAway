package com.example.srccode.takeawayproject.Classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ayman on 2017-05-18.
 */
@Table(name = "CityDB1")
public class ClassCity extends Model {
    @Column(name = "cityName")
    private String Cityname ;
    @Column(name = "cityid")
    private String Id ;


    public ClassCity() {


    }
    public ClassCity(String _Cityname, String _Id) {
        Cityname = _Cityname;

        Id=_Id;

    }
    public String getCityname() {
        return Cityname;
    }
    public void setCityname(String Cityname) {
        this.Cityname = Cityname;
    }



    public String getcityId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }


}
