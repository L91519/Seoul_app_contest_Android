package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parktaeim.seoulwithyou.Network.APIUrl;
import com.example.parktaeim.seoulwithyou.Network.RestAPI;
import com.example.parktaeim.seoulwithyou.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 10. 11..
 */

public class SignUpActivity extends AppCompatActivity {
    private ImageView backIcon;
    private MaterialEditText nameExitText;
    private MaterialEditText pwEditText;
    private MaterialEditText pwCheckEditText;
    private MaterialEditText idEditText;
    private ImageView gender_man;
    private ImageView gender_woman;
    private ImageView signupBtn;
    private boolean isSelectMan = false;
    private boolean isSelectWoman = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setView();
    }

    private void setView() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        nameExitText = (MaterialEditText) findViewById(R.id.nameEditText);
        idEditText = (MaterialEditText) findViewById(R.id.idEditText);
        pwEditText = (MaterialEditText) findViewById(R.id.pwEditText);
        pwCheckEditText = (MaterialEditText) findViewById(R.id.pwCheckEditText);
        gender_man = (ImageView) findViewById(R.id.selectGender_man);
        gender_woman = (ImageView) findViewById(R.id.selectGender_woman);
        signupBtn = (ImageView) findViewById(R.id.signupBtn);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gender_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectWoman == true){
                    isSelectWoman = false;
                    isSelectMan = true;

                }
            }
        });

        gender_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);

                Retrofit builder = new Retrofit.Builder()
                        .baseUrl(APIUrl.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RestAPI restAPI = builder.create(RestAPI.class);

                Call<Void> call = restAPI.signUp("abc","abc123",20001215,false);

                Log.d("retrofit start ===","yeah~~");

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("reponse ===",String.valueOf(response.code()));

                        if(response.code() == 200){
                            Log.d("response ===","200");

                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else if(response.code() == 400){
                            Toast.makeText(SignUpActivity.this,"회원가입 실패!",Toast.LENGTH_SHORT);
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
