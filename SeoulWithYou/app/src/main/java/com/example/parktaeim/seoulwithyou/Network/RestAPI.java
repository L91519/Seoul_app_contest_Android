package com.example.parktaeim.seoulwithyou.Network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by parktaeim on 2017. 10. 24..
 */

public interface RestAPI {
    @FormUrlEncoded
    @POST(APIUrl.LOGIN_URL)
    Call<Void> logIn(@Field("id") String id , @Field("pw") String pw);

    @FormUrlEncoded
    @POST(APIUrl.SIGN_UP_URL)
    Call<Void> signUp(@Field("id") String id,@Field("pw") String pw, @Field("birth") Integer birth, @Field("sex") Boolean sex);


}
