package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by parktaeim on 2017. 10. 30..
 */

public class MyPagePostItem {
    private String imgUrl;
    private String courseName;
    private String writeDate;
    private String postTitle;
    private String postContent;

    public MyPagePostItem(String imgUrl, String courseName,  String postTitle) {
        this.imgUrl = imgUrl;
        this.courseName = courseName;
        this.postTitle = postTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}
