package com.example.parktaeim.seoulwithyou.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parktaeim on 2017. 10. 27..
 */

public class ChatModel {
    public String message;
    public String myId;
    public String time;


//    public Map<String,Boolean> users = new HashMap<>();     //채팅방 유저들
//    public Map<String,Comment> comments = new HashMap<>();
//
//    public static class Comment{
//        public String message;
//        public String myId;
//        public String time;
//    }

    public ChatModel(){

    }

    public ChatModel(String message, String myId, String time) {
        this.message = message;
        this.myId = myId;
        this.time = time;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> comment = new HashMap<>();
        comment.put("message",message);
        comment.put("myId",myId);
        comment.put("time",time);

        return comment;
    }
}
