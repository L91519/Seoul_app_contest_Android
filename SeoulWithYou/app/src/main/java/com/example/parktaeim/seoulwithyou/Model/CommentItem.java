package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by user on 2017-10-28.
 */

public class CommentItem {

    private String name;
    private String gender;
    private String age;
    private String comment;
    private String profilPic;
    private String id;

    public CommentItem(String name, String gender, String age, String comment, String profilPic, String id) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.comment = comment;
        this.profilPic = profilPic;
        this.id = id;
    }

    public CommentItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProfilPic() {
        return profilPic;
    }

    public void setProfilPic(String profilPic) {
        this.profilPic = profilPic;
    }
}
