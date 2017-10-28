package com.example.parktaeim.seoulwithyou.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parktaeim.seoulwithyou.Adapter.CommentRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CommentItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-28.
 */

public class SearchDetailDialogActivity extends AppCompatActivity {

    private CardView contentVIew, commentView;
    private RelativeLayout layout;
    private int layoutParamX, layoutParamY;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_detail);

        recyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);
        setData();



    }

    public void setData() {

        ArrayList<CommentItem> items = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            CommentItem item1 = new CommentItem("name" + i, "male" + i, "age" + i, "내가 가겠습니당~~ 만나용가리!" + i, " " );
            items.add(item1);
        }

        adapter = new CommentRecyclerViewAdapter(getApplicationContext(), items);
        recyclerView.setAdapter(adapter);
    }
}
