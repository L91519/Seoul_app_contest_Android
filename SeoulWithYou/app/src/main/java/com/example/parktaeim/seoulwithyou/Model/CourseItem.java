package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-11.
 */

public class CourseItem {
    private String picUrl;
    private String placeName;
    private String placeDistance;

    public CourseItem() {
    }

    public CourseItem(String picUrl, String placeName, String placeDistance) {
        this.picUrl = picUrl;
        this.placeName = placeName;
        this.placeDistance = placeDistance;
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
