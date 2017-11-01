package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-26.
 */

public class BillboardItem {

    private int no;
    private String pic;
    private String title;
    private String date;
    private String name;
    private String userId;

    public BillboardItem(int no, String pic, String title, String date, String name, String userId) {
        this.no = no;
        this.pic = pic;
        this.title = title;
        this.date = date;
        this.name = name;
        this.userId = userId;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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
