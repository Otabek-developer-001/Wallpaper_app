package com.example.mywallpaper.model;

public class CategoryModel {
    private String category;
    private String imageUrl;
    public CategoryModel(String category, String imageUrl) {
        this.category = category;
        this.imageUrl = imageUrl;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
