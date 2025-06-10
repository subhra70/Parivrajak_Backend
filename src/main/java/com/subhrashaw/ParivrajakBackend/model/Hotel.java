package com.subhrashaw.ParivrajakBackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;
    private int ratings;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] imgData1;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] imgData2;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] imgData3;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] imgData4;

    private String imgName1;
    private String imgName2;
    private String imgName3;
    private String imgName4;
    private String imgType1;
    private String imgType2;
    private String imgType3;
    private String imgType4;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public byte[] getImgData1() {
        return imgData1;
    }

    public void setImgData1(byte[] imgData1) {
        this.imgData1 = imgData1;
    }

    public byte[] getImgData2() {
        return imgData2;
    }

    public void setImgData2(byte[] imgData2) {
        this.imgData2 = imgData2;
    }

    public byte[] getImgData3() {
        return imgData3;
    }

    public void setImgData3(byte[] imgData3) {
        this.imgData3 = imgData3;
    }

    public byte[] getImgData4() {
        return imgData4;
    }

    public void setImgData4(byte[] imgData4) {
        this.imgData4 = imgData4;
    }

    public String getImgName1() {
        return imgName1;
    }

    public void setImgName1(String imgName1) {
        this.imgName1 = imgName1;
    }

    public String getImgName2() {
        return imgName2;
    }

    public void setImgName2(String imgName2) {
        this.imgName2 = imgName2;
    }

    public String getImgName3() {
        return imgName3;
    }

    public void setImgName3(String imgName3) {
        this.imgName3 = imgName3;
    }

    public String getImgName4() {
        return imgName4;
    }

    public void setImgName4(String imgName4) {
        this.imgName4 = imgName4;
    }

    public String getImgType1() {
        return imgType1;
    }

    public void setImgType1(String imgType1) {
        this.imgType1 = imgType1;
    }

    public String getImgType2() {
        return imgType2;
    }

    public void setImgType2(String imgType2) {
        this.imgType2 = imgType2;
    }

    public String getImgType3() {
        return imgType3;
    }

    public void setImgType3(String imgType3) {
        this.imgType3 = imgType3;
    }

    public String getImgType4() {
        return imgType4;
    }

    public void setImgType4(String imgType4) {
        this.imgType4 = imgType4;
    }
}
