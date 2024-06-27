package com.example.kimsearappfinal;

import java.util.List;

public class MyDataModel {
    private String title;
    private String description;
    private List<Integer> imageResIds;
    private float price;

    public MyDataModel(String title, String description, List<Integer> imageResIds, float price) {
        this.title = title;
        this.description = description;
        this.imageResIds = imageResIds;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getImageResIds() {
        return imageResIds;
    }

    public float getPrice() {
        return price;
    }
}
