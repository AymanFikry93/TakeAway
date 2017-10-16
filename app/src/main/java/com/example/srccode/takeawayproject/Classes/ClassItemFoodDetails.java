package com.example.srccode.takeawayproject.Classes;

/**
 * Created by ayman on 2017-05-21.
 */

public class ClassItemFoodDetails {

    private String Id;
    private String ItemFoodId;
    private String CategoryTypeID;
    private String Name;
    private String Image;

    public String getId() {return Id;}

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getItemFoodId() {return ItemFoodId;}

    public void setItemFoodId(String ItemFoodId) {
        this.ItemFoodId = ItemFoodId;
    }
    public String getCategoryTypeID() {
        return CategoryTypeID;
    }
    public void setCategoryTypeID(String CategoryTypeID) {
        this.CategoryTypeID = CategoryTypeID;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
