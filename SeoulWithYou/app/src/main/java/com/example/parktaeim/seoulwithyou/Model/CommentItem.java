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

    public CommentItem(String name, String gender, String age, String comment, String profilPic) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.comment = comment;
        this.profilPic = profilPic;
    }

    public CommentItem() {
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
