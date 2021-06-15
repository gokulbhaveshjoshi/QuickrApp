package com.gokul.quickrapp.model;

public class UploadProduct {
    private String mName;
    private String mDescription;
    private String mCategory;
    private String mImageUrl;
    public UploadProduct() {
        //empty constructor needed
    }
    public UploadProduct(String name,String description, String category, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        if (description.trim().equals("")) {
            description = "No Name";
        }
        if (category.trim().equals("")) {
            category = "No Name";
        }
        mName = name;
        mCategory = category;
        mDescription = description;
        mImageUrl = imageUrl;
    }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    public String getDescription() {
        return mDescription;
    }
    public void setDescription(String description) {
        mDescription = description;
    }
    public String getCategory() {
        return mCategory;
    }
    public void setCategory(String category) {
        mCategory = category;
    }

}
