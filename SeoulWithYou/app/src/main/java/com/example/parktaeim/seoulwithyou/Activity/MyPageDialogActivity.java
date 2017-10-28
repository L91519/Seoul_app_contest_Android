package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.R;


/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class MyPageDialogActivity extends AppCompatActivity {
    private TextView goChatBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mypage);

        ImageView backImg = (ImageView) findViewById(R.id.backImg);
        Glide.with(this).load(R.drawable.img_login_background).into(backImg);

        setOnClick();

    }

    private void setOnClick() {
        goChatBtn = (TextView) findViewById(R.id.goChatButton);
        goChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageDialogActivity.this,ChattingActivity.class);
                startActivity(intent);
            }
        });
    }
}
