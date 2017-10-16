package com.example.srccode.takeawayproject.Classes;

import com.example.srccode.takeawayproject.Classes.ClassItemFood;

import java.util.ArrayList;

/**
 * Created by ayman on 2017-05-21.
 */

public class ClassCategory {

    private String Id;
    private String Name;

        private ArrayList<ClassItemFood> Items;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
        public String getName() {
            return Name;
        }

        public void setName(String name) {
            this.Name = name;
        }

        public ArrayList<ClassItemFood> getItems() {
            return Items;
        }

        public void setItems(ArrayList<ClassItemFood> Items) {
            this.Items = Items;
        }

}