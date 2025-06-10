package com.subhrashaw.ParivrajakBackend.model;

import java.util.List;

public class ProductResponse {
    private int id;
    private String title;
    private String place;
    private double price;

    public ProductResponse(int id, String title, String place, double price) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
