package com.example.srccode.takeawayproject;

/**
 * Created by ayman on 2017-08-05.
 */

public class ClassTakeawayUser {

    private String ContactName ;
    private String ContactEmail ;
    private String ContactPhone ;
    private int PayTypeID ;
    private String UserName ;
    private String Password ;
    private String ConfirmPassword ;
    public ClassTakeawayUser(String ContactName, String ContactEmail,String ContactPhone ,
                             int PayTypeID, String UserName,String Password,String ConfirmPassword ) {
        ContactName = ContactName;
        ContactEmail=ContactEmail;
        ContactPhone=ContactPhone;

        PayTypeID = PayTypeID;
        UserName=UserName;
        Password=Password;
        ConfirmPassword=ConfirmPassword;

    }
    public String getContactName() {
        return ContactName;
    }
    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }


    public String getContactEmail() {
        return ContactEmail;
    }
    public void setContactEmail(String ContactEmail) {
        this.ContactEmail = ContactEmail;
    }

    public String getContactPhone() {
        return ContactPhone;
    }
    public void setContactPhone(String ContactPhone) {
        this.ContactPhone = ContactPhone;
    }

    public int getPayTypeID() {
        return PayTypeID;
    }
    public void setPayTypeID(int PayTypeID) {
        this.PayTypeID = PayTypeID;
    }


    public String getUserName() {
        return UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }
    public void setConfirmPassword(String ConfirmPassword) {
        this.ConfirmPassword = ConfirmPassword;
    }
}
