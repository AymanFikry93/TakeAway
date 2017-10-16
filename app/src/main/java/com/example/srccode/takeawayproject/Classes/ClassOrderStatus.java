package com.example.srccode.takeawayproject.Classes;


/**
 * Created by ayman on 2017-08-22.
 */

public class ClassOrderStatus {
    private String orderstatusname;
    private String orderstatusid;

    public ClassOrderStatus() {


    }
    public ClassOrderStatus(String orderstatusname,String orderstatusid) {
        orderstatusname = orderstatusname;

        orderstatusid=orderstatusid;

    }
    public String getorderstatusname() {
        return orderstatusname;
    }
    public void setorderstatusname(String orderstatusname) {
        this.orderstatusname = orderstatusname;
    }



    public String getorderstatusid() {
        return orderstatusid;
    }
    public void setorderstatusid(String orderstatusid) {
        this.orderstatusid = orderstatusid;
    }


}

