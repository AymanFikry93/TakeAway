package com.example.srccode.takeawayproject.Classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ayman on 2017-05-22.
 */
@Table(name = "CountryDB")
public class ClassCountries extends Model {
    @Column(name = "countryname")
    private String Countryname ;
    @Column(name = "countryId")
    private String Id ;

    public ClassCountries() {


    }
    public ClassCountries(String _Countryname,String _Id) {
        Countryname = _Countryname;

        Id=_Id;

    }
    public String getCountryname() {
        return Countryname;
    }
    public void setCountryname(String Countryname) {
        this.Countryname = Countryname;
    }



    public String getcountryId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }





}
