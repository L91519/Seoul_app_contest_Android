package com.example.parktaeim.seoulwithyou.Activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.parktaeim.seoulwithyou.Adapter.MyPagePostRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.MyPagePostItem;
import com.example.parktaeim.seoulwithyou.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class MyPageDialogActivity extends Activity implements View.OnClickListener{
    private TextView goChatBtn;

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CircleImageView changeProfileIcon;
    private CircleImageView mypage_profileImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mypage);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView mypage_backImg = (ImageView) findViewById(R.id.mypage_backImg);
        Glide.with(this).load(R.drawable.img_login_background).into(mypage_backImg);
        LinearLayout clipLayout = (LinearLayout) findViewById(R.id.clipLayout);
        clipLayout.setClipToOutline(true);

        setProfile();
        setOnClick();
        setRecyclerView();
        setChangeProfile();
    }

    private void setChangeProfile() {
        changeProfileIcon = (CircleImageView) findViewById(R.id.changeProfileIcon);
        mypage_profileImg = (CircleImageView) findViewById(R.id.mypage_profileImg);


    }

    @Override
    public void onClick(View view) {

    }

    private void setProfile() {
        Intent intent = getIntent();
        String myPage_id = intent.getStringExtra("myPage_id");
        Log.d("mypage id get =======",myPage_id);

    }

    private void setRecyclerView() {
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.myPagePostRecyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("myId",MODE_PRIVATE);
        Log.d("mypage pref === ",sharedPreferences.getString("myId","null"));

        // 다이얼로그에 작성 글 세팅
        SharedPreferences tokenPref = getSharedPreferences("tokenPref",MODE_PRIVATE);
        com.example.parktaeim.seoulwithyou.Network.Service.getRetrofit(context).mypage_post(tokenPref.getString("token","null"),sharedPreferences.getString("myId","null")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("mypage response code ==",String.valueOf(response.code()));
                if(response.code() == 200){
                    JsonArray infoArray = response.body().getAsJsonArray("data");
                    Log.d("mypage infoArray ==",infoArray.toString());

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        items.add(new MyPagePostItem("http://img.hb.aicdn.com/03d474bbe20efb7df9aed4541ace70b53b53c70bdfe3-8djYVv_fw658","경복궁과 함께라면","2017-09-22","나랑 같이 놀러갈래?","아이러브유"));

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RequestManager requestManager = Glide.with(getApplicationContext());
        adapter = new MyPagePostRecyclerViewAdapter(getApplicationContext(),items,requestManager);
        recyclerView.setAdapter(adapter);

    }

    private void setOnClick() {
        ImageView cancelIcon = (ImageView) findViewById(R.id.cancel_Btn);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        goChatBtn = (TextView) findViewById(R.id.goChatButton);
//        goChatBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyPageDialogActivity.this,ChattingActivity.class);
//                startActivity(intent);
//            }
//        });
    }


}
