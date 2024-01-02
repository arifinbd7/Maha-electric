package com.example.mahaelectric;

public class productModel {

    String title;
    String stock;
    String price;
    String mrp;

    public productModel(String title, String stock, String price, String mrp) {
        this.title = title;
        this.stock = stock;
        this.price = price;
        this.mrp = mrp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }
}
