package com.example.srccode.takeawayproject.Classes;

/**
 * Created by ayman on 2017-08-09.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CookTable")
public class ClassCookingDB extends Model {
    @Column(name = "cookname")
    private String Cookname;
    @Column(name = "CookId")
    private String Cookid;

    public ClassCookingDB() {


    }
    public ClassCookingDB(String cookname,String cookid) {
        this.Cookname = cookname;

        this.Cookid=cookid;

    }
    public String getCookname() {
        return Cookname;
    }
    public void setCookname(String Cookname) {
        this.Cookname = Cookname;
    }



    public String getcookid() {
        return Cookid;
    }
    public void setcookid(String cookid) {
        this.Cookid = cookid;
    }


}
