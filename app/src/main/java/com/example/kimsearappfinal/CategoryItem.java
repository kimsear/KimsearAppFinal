package com.example.kimsearappfinal;

public class CategoryItem {

    private int imageResource;
    private String categoryTitle;

    public CategoryItem(int imageResource, String categoryTitle) {
        this.imageResource = imageResource;
        this.categoryTitle = categoryTitle;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }
}
