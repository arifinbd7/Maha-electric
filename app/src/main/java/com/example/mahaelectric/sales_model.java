package com.example.mahaelectric;

public class sales_model {

    String date;
    String desc;
    String profit;
    String month;
    String sale;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public sales_model(String date, String month, String profit,String desc, String sale) {
        this.date = date;
        this.month = month;
        this.desc = desc;
        this.profit = profit;
        this.sale = sale;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
