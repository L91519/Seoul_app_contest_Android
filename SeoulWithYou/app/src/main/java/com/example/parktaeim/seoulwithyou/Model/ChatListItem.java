package com.example.parktaeim.seoulwithyou.Model;

/**
 * Created by parktaeim on 2017. 10. 25..
 */

public class ChatListItem {
    private String yourProfile;
    private String yourName;
    private String lastMessage;
    private String time;
    private String countUnsightMessage;

    public ChatListItem(String yourProfile, String yourName, String lastMessage, String time, String countUnsightMessage) {
        this.yourProfile = yourProfile;
        this.yourName = yourName;
        this.lastMessage = lastMessage;
        this.time = time;
        this.countUnsightMessage = countUnsightMessage;
    }

    public String getYourProfile() {
        return yourProfile;
    }

    public void setYourProfile(String yourProfile) {
        this.yourProfile = yourProfile;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCountUnsightMessage() {
        return countUnsightMessage;
    }

    public void setCountUnsightMessage(String countUnsightMessage) {
        this.countUnsightMessage = countUnsightMessage;
    }
}
