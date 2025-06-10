package com.subhrashaw.ParivrajakBackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "destDetails")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String place;
    private String destImgName;
    private String destImgType;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] destImg;
    private double price;
    private int minDays;
    private int maxDays;
    @ManyToOne
    @JoinColumn
    private Organizer orgId;
    private int ratings;
    private double discount;
    @ManyToOne
    @JoinColumn
    private Hotel hotelId;
    private  List<String> destType;

    public Product(int id)
    {
        this.id=id;
    }
    public Product()
    {

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

    public byte[] getDestImg() {
        return destImg;
    }

    public void setDestImg(byte[] destImg) {
        this.destImg = destImg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    public Organizer getOrgId() {
        return orgId;
    }

    public void setOrgId(Organizer orgId) {
        this.orgId = orgId;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Hotel getHotelId() {
        return hotelId;
    }

    public void setHotelId(Hotel hotelId) {
        this.hotelId = hotelId;
    }

    public List<String> getDestType() {
        return destType;
    }

    public void setDestType(List<String> destType) {
        this.destType = destType;
    }

    public String getDestImgName() {
        return destImgName;
    }

    public void setDestImgName(String destImgName) {
        this.destImgName = destImgName;
    }

    public String getDestImgType() {
        return destImgType;
    }

    public void setDestImgType(String destImgType) {
        this.destImgType = destImgType;
    }
}
