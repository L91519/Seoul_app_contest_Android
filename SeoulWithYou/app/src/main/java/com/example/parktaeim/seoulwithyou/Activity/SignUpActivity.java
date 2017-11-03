package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Network.APIUrl;
import com.example.parktaeim.seoulwithyou.Network.RestAPI;
import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.regex.Pattern;

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
    private ImageView noSelectGender_man;
    private ImageView noSelectGender_woman;
    private ImageView signupBtn;
    private boolean isSelectMan = false;
    private boolean isSelectWoman = false;
    private String gender = "null";
    private boolean realGender;
    private String name;
    private String id;
    private String pw;
    private int birth;
    private String year, month, date;

    private static final String[] YEAR_BIRTH = new String[108];

    private static final String[] MONTH_BIRTH = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
    };

    private static final String[] DATE_BIRTH = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setView();
    }


    protected InputFilter filter= new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,

                                   Spanned dest, int dstart, int dend) {



            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if (!ps.matcher(source).matches()) {

                return "";

            }

            return null;

        }

    };
    private void setView() {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        nameExitText = (MaterialEditText) findViewById(R.id.nameEditText);
        idEditText = (MaterialEditText) findViewById(R.id.idEditText);
        pwEditText = (MaterialEditText) findViewById(R.id.pwEditText);
        pwCheckEditText = (MaterialEditText) findViewById(R.id.pwCheckEditText);
        gender_man = (ImageView) findViewById(R.id.selectGender_man);
        gender_woman = (ImageView) findViewById(R.id.selectGender_woman);
        noSelectGender_man = (ImageView) findViewById(R.id.noSelectGender_man);
        noSelectGender_woman = (ImageView) findViewById(R.id.noSelectGender_woman);
        signupBtn = (ImageView) findViewById(R.id.signupBtn);
        ImageView backImg= (ImageView) findViewById(R.id.signup_backgroundImg);

        Glide.with(this).load(R.drawable.img_login_background).into(backImg);

        idEditText.setFilters(new InputFilter[] {filter});
        pwEditText.setFilters(new InputFilter[]{filter});
        pwCheckEditText.setFilters(new InputFilter[]{filter});

        //Setting Select Gender
        noSelectGender_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Man";
                gender_man.setVisibility(View.VISIBLE);
                noSelectGender_man.setVisibility(View.INVISIBLE);
                if (gender_woman.getVisibility() == View.VISIBLE) {
                    gender_woman.setVisibility(View.INVISIBLE);
                    noSelectGender_woman.setVisibility(View.VISIBLE);
                }
            }
        });

        noSelectGender_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Woman";
                gender_woman.setVisibility(View.VISIBLE);
                noSelectGender_woman.setVisibility(View.INVISIBLE);
                if (gender_man.getVisibility() == View.VISIBLE) {
                    gender_man.setVisibility(View.INVISIBLE);
                    noSelectGender_man.setVisibility(View.VISIBLE);
                }
            }
        });

        // Settin Birth Spinner
        int j = 0;
        for (int i = 1910; i <= 2017; i++) {
            YEAR_BIRTH[j] = String.valueOf(i);
            Log.d("YEAR_BIRTH[" + j + "] == ", YEAR_BIRTH[j]);
            j++;
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, YEAR_BIRTH);
        MaterialBetterSpinner yearSpinner = (MaterialBetterSpinner)
                findViewById(R.id.birthYearSpinner);
        yearSpinner.setAdapter(yearAdapter);


        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, MONTH_BIRTH);
        final MaterialBetterSpinner monthSpinner = (MaterialBetterSpinner)
                findViewById(R.id.birthMonthSpinner);
        monthSpinner.setAdapter(monthAdapter);

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, DATE_BIRTH);
        MaterialBetterSpinner dateSpinner = (MaterialBetterSpinner)
                findViewById(R.id.birthDateSpinner);
        dateSpinner.setAdapter(dateAdapter);

        yearSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                year = adapterView.getItemAtPosition(position).toString();
                Log.d("spinner year Listener==", year);
            }
        });

        monthSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                month = adapterView.getItemAtPosition(position).toString();
                Log.d("spinner month Listener=", month);
            }
        });

        dateSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                date = adapterView.getItemAtPosition(position).toString();
                Log.d("spinner date Listener==", date);
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        signupBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                id = idEditText.getText().toString();
                pw = pwEditText.getText().toString();
                String pwCheck = pwCheckEditText.getText().toString();
                name = nameExitText.getText().toString();

                if (name == null || name.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gender.equals("null")) {
                    Toast.makeText(SignUpActivity.this, "성별을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (year == null || month == null || date == null) {
                    Toast.makeText(SignUpActivity.this, "생년월일을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (id == null || id.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pw == null || pw.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pwCheck == null || pwCheck.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 한번 더 체크해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (id.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "아이디는 6자리 이상이어야 합니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pw.length() < 8) {
                    Toast.makeText(SignUpActivity.this, "비밀번호는 8자리 이상이어야 합니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (id.length() > 16) {
                    Toast.makeText(SignUpActivity.this, "아이디는 16자리 이하여야 합니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pw.length() > 16) {
                    Toast.makeText(SignUpActivity.this, "비밀번호는 16자리 이하여야 합니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                } else if () {
//                    Toast.makeText(SignUpActivity.this, "생년월일을 선택해주세요!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                birth = Integer.valueOf(year + month + date);
                Log.d("birth Int", String.valueOf(birth));

                if (gender == "Man") {
                    realGender = true;
                } else if (gender == "Woman") {
                    realGender = false;
                }

                if (pw.equals(pwCheck)) {


                    Retrofit builder = new Retrofit.Builder()
                            .baseUrl(APIUrl.API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RestAPI restAPI = builder.create(RestAPI.class);

                    Call<Void> call = restAPI.signUp(name, id, pw, birth, realGender);
                    Log.d("retrofit start ===", "yeah~~");

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("reponse ===", String.valueOf(response.code()));

                            if (response.code() == 200) {
                                Log.d("response ===", "200");

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (response.code() == 400) {
                                Toast.makeText(SignUpActivity.this, "회원가입 실패!", Toast.LENGTH_SHORT);
                                return;
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("login Failure === ", t.toString());
                        }
                    });


//                    Service.getRetrofit(getApplicationContext()).
//                            signUp(name,id, pw, birth, realGender).
//                            enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call, Response<Void> response) {
//                                    Log.d("reponse ===", String.valueOf(response.code()));
//
//                                    if (response.code() == 200) {
//                                        Log.d("response ===", "200");
//
//                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else if (response.code() == 400) {
//                                        Toast.makeText(SignUpActivity.this, "회원가입 실패!", Toast.LENGTH_SHORT);
//                                        return;
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call, Throwable t) {
//                                    Log.d("login Failure === ", t.toString());
//                                }
//                            });
//                } else{
//                    Toast.makeText(SignUpActivity.this, "비밀번호와 비밀번호 체크 값이 다릅니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                }
            }

        });
    }
}