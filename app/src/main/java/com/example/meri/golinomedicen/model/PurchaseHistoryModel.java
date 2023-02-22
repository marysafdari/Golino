package com.example.meri.golinomedicen.model;

public class PurchaseHistoryModel {

    private String date;

    private String time;

    private String pharmacyName;

    private String price;

    private String imageUrl;

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(final String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "PurchaseHistoryModel{" + "date='" + date + '\'' + ", time='" + time + '\'' + ", " +
                "pharmacyName='" + pharmacyName + '\'' + ", price=" + price + ", imageUrl='" + imageUrl +
                '\'' + '}';
    }
}
