package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-11.
 */

public class CourseItem {
    private String picUrl;
    private String placeName;
    private String placeDistance;
    private int id;

    public CourseItem() {
    }

    public CourseItem(String picUrl, String placeName, String placeDistance, int id) {
        this.picUrl = picUrl;
        this.placeName = placeName;
        this.placeDistance = placeDistance;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
