package com.example.parktaeim.seoulwithyou.Network;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
        Call<JsonObject> logIn(@Field("id") String id , @Field("pw") String pw);

        @FormUrlEncoded
        @POST(APIUrl.SIGN_UP_URL)
        Call<Void> signUp(@Field("name") String name,@Field("id") String id,@Field("pw") String pw, @Field("birth") Integer birth, @Field("sex") Boolean sex);





        @GET("/list/modern")
        Call<JsonObject> getModernCourseList();

        @GET("/list/culture")
        Call<JsonObject> getArtCourseList();

        @GET("/list/restaurant")
        Call<JsonObject> getFoodCourseList();

        @GET("list/healing")
        Call<JsonObject> getHealingCourseList();

        @GET("list/history")
        Call<JsonObject> getHistoryCourseList();




        @GET(APIUrl.MYPAGE_INFO_URL)
        Call<JsonObject> mypage_info(@Header("Authorization") String Authorization, @Path("id") String id);

        @GET(APIUrl.MYPAGE_POST)
        Call<JsonObject> mypage_post(@Header("Authorization") String Authorization,@Path("userId") String userId);

        @GET("/detail/{no}")
        Call<JsonObject> getDetail(@Path("no") int no);

        @GET("/post/comment/{no}")
        Call<JsonObject> getComment(@Header("Authorization") String Authorization, @Path("no") int no);

        @FormUrlEncoded
        @POST("/post")
        Call<Void> postList(@Header("Authorization") String Authorization,
                            @Field("title") String title,
                            @Field("content") String content,
                            @Field("itemNo") int itemNo);

        @FormUrlEncoded
        @POST("/post/comment")
        Call<Void> postComment(@Header("Authorization") String Authorization,
                               @Field("content") String content,
                               @Field("postNo") int postNo);

        @GET("/post/{postNo}")
        Call<JsonObject> getPost(@Header("Authorization") String Authorization, @Path("postNo") int no);

        @GET("/list/post/{no}")
        Call<JsonObject> getList(@Header("Authorization") String Authorization,@Path("no") int no);

        @FormUrlEncoded
        @PATCH(APIUrl.CHANGE_PW_URL)
        Call<Void> changePw(@Header("Authorization") String Authorization, @Field("pw") String pw);

        @GET("/check")
        Call<JsonObject> checkToken(@Header("Authorization") String Authorization);

    }
}
