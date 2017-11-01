package com.example.parktaeim.seoulwithyou.Network;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by user on 2017-10-29.
 */

public class Service extends APIAdapter{

    public static RestApi getRetrofit (Context context) {
        return (RestApi) retrofit(context, RestApi.class);
    }

    public interface RestApi {
        @FormUrlEncoded
        @POST(APIUrl.LOGIN_URL)
        Call<Void> logIn(@Field("id") String id , @Field("pw") String pw);

        @FormUrlEncoded
        @POST(APIUrl.SIGN_UP_URL)
        Call<Void> signUp(@Field("name") String name,@Field("id") String id,@Field("pw") String pw, @Field("birth") Integer birth, @Field("sex") Boolean sex);

        @GET(APIUrl.GET_MODERN_COURSE)
        Call<JsonObject> getModernCourseList();

        @GET(APIUrl.MYPAGE_URL_ANYONE)
        Call<JsonObject> mypage_info(@Path("id") String id);

        @GET(APIUrl.MYPAGE_POST)
        Call<JsonObject> mypage_post(@Path("id") String id);

        @GET(APIUrl.GET_ART_COURSE)
        Call<JsonObject> getArtCourseList();

        @FormUrlEncoded
        @PATCH(APIUrl.CHANGE_PW_URL)
        Call<Void> changePw(@Field("pw") String pw);

    }
}
