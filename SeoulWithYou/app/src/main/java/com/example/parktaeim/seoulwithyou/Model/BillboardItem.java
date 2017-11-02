package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-26.
 */

public class BillboardItem {

    private int postNo;
    private String pic;
    private String title;
    private String date;
    private String name;
    private String userId;
    private boolean gender;
    private int age;

    public BillboardItem(int postNo, String pic, String title, String date, String name, String userId, boolean gender, int age) {
        this.postNo = postNo;
        this.pic = pic;
        this.title = title;
        this.date = date;
        this.name = name;
        this.userId = userId;
        this.gender = gender;
        this.age = age;
    }

    public BillboardItem(int postNo, String pic, String title, String date, String name, String userId) {
        this.postNo = postNo;
        this.pic = pic;
        this.title = title;
        this.date = date;
        this.name = name;
        this.userId = userId;
    }

    public int getPostNo() {
        return postNo;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
