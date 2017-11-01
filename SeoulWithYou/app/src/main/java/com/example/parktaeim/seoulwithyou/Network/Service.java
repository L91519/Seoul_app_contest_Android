package com.example.parktaeim.seoulwithyou.Network;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

        @GET("/list/modern")
        Call<JsonObject> getModernCourseList();

        @GET(APIUrl.GET_ART_COURSE)
        Call<JsonObject> getArtCourseList();

        @GET("/detail/{no}")
        Call<JsonObject> getDetail(@Path("no") int no);

        @GET("/post/comment/{no}")
        Call<JsonObject> getComment(@Path("no") int no);

        @FormUrlEncoded
        @POST("/post/comment")
        Call<Void> postComment(@Field("content") String content, @Field("postNo") int postNo);

        @GET("/post/{no}")
        Call<JsonObject> getPost(@Path("no") int no);

        @FormUrlEncoded
        @POST("/post")
        Call<Void> postList(@Field("title") String title, @Field("content") String content, @Field("itemNo") int itemNo);

        @GET("/list/post/{no}")
        Call<JsonObject> getList(@Path("no") int no);
    }
}
