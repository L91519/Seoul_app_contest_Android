package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Network.APIUrl;
import com.example.parktaeim.seoulwithyou.Network.RestAPI;
import com.example.parktaeim.seoulwithyou.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 10. 11..
 */

public class LoginActivity extends AppCompatActivity {

    private ImageView loginBtn ;
    private TextView registTextView;
    private TextView findIdPwTextView;
    MaterialEditText input_id;
    MaterialEditText input_pw;

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

        input_id = (MaterialEditText) findViewById(R.id.idEditText);
        input_pw = (MaterialEditText) findViewById(R.id.pwEditText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = input_id.getText().toString();
                String pw = input_pw.getText().toString();

                //id,pw가 입력되지 않았으면
                if(input_id==null || input_id.length() ==0 ){
                    Toast.makeText(LoginActivity.this,"아이디를 입력해주세요!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(input_pw == null || input_pw.length()==0){
                    Toast.makeText(LoginActivity.this,"비밀번호를 입력해주세요!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit builder = new Retrofit.Builder()
                        .baseUrl(APIUrl.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestAPI restAPI = builder.create(RestAPI.class);

                Call<Void> call = restAPI.logIn(id,pw);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if(response.code() == 400){
                            Toast.makeText(LoginActivity.this,"로그인 실패!",Toast.LENGTH_SHORT);
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("login Failure === ",t.toString());
                    }
                });
            }
        });


    }
}
