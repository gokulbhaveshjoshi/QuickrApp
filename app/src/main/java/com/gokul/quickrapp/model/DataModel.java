package com.gokul.quickrapp.model;

public class DataModel {
    private String name;
    private String description;
    private String image;
    private String category;
    private String image1, image2, image3, image4;
    private String productPrice;


    public String getProductPrice(){
        return productPrice;
    }
    public void setProductPrice(String productPrice){
        this.productPrice = productPrice;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public String getImage1(){
        return image1;
    }
    public void setImage1(String image1){
        this.image1 = image1;
    }

    public String getImage2(){
        return image2;
    }
    public void setImage2(String image2){
        this.image2 = image2;
    }

    public String getImage3(){
        return image3;
    }
    public void setImage3(String image3){
        this.image3 = image3;
    }

    public String getImage4(){
        return image4;
    }
    public void setImage4(String image4){
        this.image4 = image4;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
