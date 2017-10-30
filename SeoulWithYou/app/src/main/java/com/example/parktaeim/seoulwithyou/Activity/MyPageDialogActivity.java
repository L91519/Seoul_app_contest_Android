package com.example.parktaeim.seoulwithyou.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.parktaeim.seoulwithyou.Adapter.MyPagePostRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.MyPagePostItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;


/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class MyPageDialogActivity extends Activity {
    private TextView goChatBtn;

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mypage);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView backImg = (ImageView) findViewById(R.id.backImg);
        Glide.with(this).load(R.drawable.img_login_background).into(backImg);
        backImg.setClipToOutline(true);

        ImageView courseImg = (ImageView) findViewById(R.id.courseImg);
//        courseImg.setClipToOutline(true);
        setOnClick();
        setRecyclerView();
    }

    private void setRecyclerView() {
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.myPagePostRecyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();

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
