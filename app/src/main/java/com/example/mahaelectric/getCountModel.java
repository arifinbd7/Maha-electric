package com.example.mahaelectric;

public class getCountModel {
    String title;
    String productCount;
    public getCountModel(String title, String productCount) {
        this.title = title;
        this.productCount = productCount;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }



}
