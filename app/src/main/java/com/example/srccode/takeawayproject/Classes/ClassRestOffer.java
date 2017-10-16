package com.example.srccode.takeawayproject.Classes;

/**
 * Created by ayman on 2017-08-02.
 */

public class ClassRestOffer {

    private String Id ;
    private String name ;
    private String openandclose ;
    private String mincharge ;
    private int rating ;

    private String image ;


    public ClassRestOffer( ) {

    }

    //, double rating
    public ClassRestOffer(String Id, String name, String openandclose, String mincharge, int rating,String image
    ) {
        this.Id = Id;
        this.name = name;
        this.openandclose = openandclose;
        this.mincharge = mincharge;

        this.rating = rating;
        this.image = image;

    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getopenandclose() {
        return openandclose;
    }
    public String getmincharge() {
        return mincharge;
    }

    public int getRating() { return rating; }


    public void setId(String Id) {
        this.Id = Id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setopenandclose(String openandclose) {
        this.openandclose = openandclose;
    }
    public void setmincharge(String mincharge) {
        this.mincharge = mincharge;
    }

    public void setRating(int rating) {  this.rating = rating;}

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }





}
