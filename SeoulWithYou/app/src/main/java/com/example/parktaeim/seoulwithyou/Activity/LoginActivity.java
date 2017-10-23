package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by parktaeim on 2017. 10. 11..
 */

public class LoginActivity extends AppCompatActivity {

    private ImageView loginBtn ;
    private TextView registTextView;
    private TextView findIdPwTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView backgroundImg = (ImageView) findViewById(R.id.backgroundImg);
        loginBtn = (ImageView) findViewById(R.id.loginBtn) ;
        ImageView logoImg = (ImageView) findViewById(R.id.logo_img);
        registTextView = (TextView) findViewById(R.id.registTextView);
        findIdPwTextView = (TextView) findViewById(R.id.findIdPwTextView);


        Glide.with(this).load(R.drawable.img_login_background).into(backgroundImg);
        Glide.with(this).load(R.drawable.img_login_btn).into(loginBtn);
        Glide.with(this).load(R.drawable.icon_app).into(logoImg);

        registTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        findIdPwTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,FindIdPwActivity.class);
                startActivity(intent);
            }
        });



    }
}
