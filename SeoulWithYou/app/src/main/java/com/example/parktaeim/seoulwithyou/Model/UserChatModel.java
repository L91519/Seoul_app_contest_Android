package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class UserChatModel {
    public String userName;
    public String profileImageUrl;
    public String userId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
