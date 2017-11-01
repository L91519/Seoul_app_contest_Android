package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by parktaeim on 2017. 11. 1..
 */

public class ChangePwActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        ImageView changeBtn = (ImageView) findViewById(R.id.changepw_okBtn);
        Glide.with(this).load(R.drawable.img_login_btn).into(changeBtn);

        EditText currentEditText = (EditText) findViewById(R.id.currentPw_editText);
        EditText changeEditText = (EditText) findViewById(R.id.changePw_editText);
        EditText checktEditText = (EditText) findViewById(R.id.checkPw_editText);


        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPw = currentEditText.getText().toString();
                String changePw = changeEditText.getText().toString();
                String checkPw = checktEditText.getText().toString();

                if(currentPw == null || currentPw.length() ==0 || changePw ==null||changePw.length() ==0||checkPw == null || checkPw.length() == 0){
                    Toast.makeText(ChangePwActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (changePw.length() < 8) {
                    Toast.makeText(ChangePwActivity.this, "비밀번호는 8자리 이상이어야 합니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (changePw.length() > 16) {
                    Toast.makeText(ChangePwActivity.this, "비밀번호는 16자리 이하여야 합니다!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(changePw.equals(checkPw) == false){
                    Toast.makeText(ChangePwActivity.this, "비밀번호와 비밀번호 확인 값이 달라요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("myId",MODE_PRIVATE);
                String currentId = sharedPreferences.getString("myId","null");   // 현재 id
                Service.getRetrofit(getApplicationContext()).logIn(currentId,currentPw).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("chanpw login res ==",String.valueOf(response.code()));
                        if(response.code() == 200){
                            // 비밀번호 맞음
                            Service.getRetrofit(getApplicationContext()).changePw(changePw).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.d("changePw ===",changePw);
                                    Log.d("changePw real res ==",String.valueOf(response.code()));
                                    if(response.code() == 200){
                                        Toast.makeText(ChangePwActivity.this, "비밀번호가 변경되었습니다!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChangePwActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(ChangePwActivity.this, "비밀번호 변경 실패!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d("login fail(changePw)==",t.toString());
                                    Toast.makeText(ChangePwActivity.this, "비밀번호 변경 실패!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });

                        } else {
                            Toast.makeText(ChangePwActivity.this, "현재 비밀번호를 정확히 입력해주세요!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("login fail(changePw)==",t.toString());
                        Toast.makeText(ChangePwActivity.this, "현재 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });


            }
        });


    }
}
