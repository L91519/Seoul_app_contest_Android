package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by parktaeim on 2017. 10. 13..
 */

public class SplashActivity extends AppCompatActivity {
    ImageView backgroundImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        backgroundImg = (ImageView) findViewById(R.id.splashImage);
        Glide.with(this).load(R.drawable.img_splash_background).into(backgroundImg);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
