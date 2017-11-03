package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.R;

import java.util.Collection;

/**
 * Created by parktaeim on 2017. 10. 13..
 */

public class SplashActivity extends AppCompatActivity {
    ImageView backgroundImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences sharedPreferences = getSharedPreferences("myId",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Collection<?> collection = sharedPreferences.getAll().values();
        Log.d("before clear pef===",collection.toString());

        SharedPreferences tokenPref = getSharedPreferences("tokenPref",MODE_PRIVATE);
        SharedPreferences.Editor tokenEditor = tokenPref.edit();

        SharedPreferences myInfoPref = getSharedPreferences("myInfoPref",MODE_PRIVATE);
        SharedPreferences.Editor myInfoEditor = myInfoPref.edit();

        editor.clear();
        editor.commit();
        tokenEditor.clear();
        tokenEditor.commit();
        myInfoEditor.clear();
        myInfoEditor.commit();


        backgroundImg = (ImageView) findViewById(R.id.splashImage);
        Glide.with(this).load(R.drawable.img_splash_background).into(backgroundImg);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("myId",MODE_PRIVATE);
                Collection<?> collection = pref.getAll().values();

                SharedPreferences tokenPref = getSharedPreferences("tokenPref",MODE_PRIVATE);
                Collection<?> tokenCollection = tokenPref.getAll().values();
                Log.d("splash pref ==",collection.toString());

                if(collection.toString().equals("[]") == false && tokenCollection.toString().equals("[]")==false){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }else if(collection.toString().equals("[]") || tokenCollection.toString().equals("[]")) {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },3000);
    }
}
