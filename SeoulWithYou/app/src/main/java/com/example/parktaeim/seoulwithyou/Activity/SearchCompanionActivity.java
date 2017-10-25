package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by user on 2017-10-26.
 */

public class SearchCompanionActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_companion);

        Intent intent = getIntent();
        position = intent.getIntExtra("currentPosition", 0);


    }
}
