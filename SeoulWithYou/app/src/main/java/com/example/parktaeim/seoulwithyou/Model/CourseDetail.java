package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-11.
 */

public class CourseDetail {
    private String number;
    private String name;
    private String detail;
    private String picUrl;

    public CourseDetail(String number, String name, String detail, String picUrl) {
        this.number = number;
        this.name = name;
        this.detail = detail;
        this.picUrl = picUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
