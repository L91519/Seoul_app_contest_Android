package com.example.parktaeim.seoulwithyou.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017-10-11.
 */

public class CourseItem {
    private double lon;
    private double lat;
    private String picUrl;
    private String picUrl2;
    private int no;
    private String placeName;
    private String placeDistance;
    private int id;

    public CourseItem(double lon, double lat, String picUrl, int no, String placeName) {
        this.lon = lon;
        this.lat = lat;
        this.picUrl = picUrl;
        this.no = no;
        this.placeName = placeName;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl2() {
        return picUrl2;
    }

    public void setPicUrl2(String picUrl2) {
        this.picUrl2 = picUrl2;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDistance() {
        return placeDistance;
    }

    public void setPlaceDistance(String placeDistance) {
        this.placeDistance = placeDistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
