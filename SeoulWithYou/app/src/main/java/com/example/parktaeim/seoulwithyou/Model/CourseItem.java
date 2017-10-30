package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-11.
 */

public class CourseItem {
    private int lon;
    private int lat;
    private String picUrl;
    private String picUrl2;
    private int no;
    private String placeName;
    private String placeDistance;
    private String id;

    public CourseItem() {
    }

    public CourseItem(String picUrl, String placeName, String placeDistance, String id) {
        this.picUrl = picUrl;
        this.placeName = placeName;
        this.placeDistance = placeDistance;
        this.id = id;
    }

    public CourseItem(int lon, int lat, String picUrl, String placeName, String placeDistance, String id) {
        this.lon = lon;
        this.lat = lat;
        this.picUrl = picUrl;
        this.placeName = placeName;
        this.placeDistance = placeDistance;
        this.id = id;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
}
