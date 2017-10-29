package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-26.
 */

public class BillboardItem {

    private String id;
    private String pic;
    private String title;
    private String date;
    private String name;

    public BillboardItem(String id, String pic, String title, String date, String name) {
        this.id = id;
        this.pic = pic;
        this.title = title;
        this.date = date;
        this.name = name;
    }

    public BillboardItem(String pic, String title, String date, String name) {
        this.pic = pic;
        this.title = title;
        this.date = date;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
