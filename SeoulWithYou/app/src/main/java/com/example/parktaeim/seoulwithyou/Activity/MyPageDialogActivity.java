package com.example.parktaeim.seoulwithyou.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class MyPageDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mypage);

        ImageView backImg = (ImageView) findViewById(R.id.backImg);
        Glide.with(this).load(R.drawable.img_login_background).into(backImg);

    }
}
