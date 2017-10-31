package com.example.parktaeim.seoulwithyou.Network;

/**
 * Created by parktaeim on 2017. 10. 24..
 */

public class APIUrl {
    public static final String API_BASE_URL = "https://seoul-with-u.herokuapp.com";

    public static final String LOGIN_URL = "/login";
    public static final String SIGN_UP_URL = "/register";
    public static final String LOGOUT_URL = "/logout";
    public static final String GET_MODER_COURSE = "/list/restaurant";
    public static final String MYPAGE_MY_URL = "/mypage";
    public static final String MYPAGE_URL_ANYONE = "/mypage/{id}";
    public static final String MYPAGE_POST = "/mypage/post/:{id}";
}
