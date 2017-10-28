package com.example.parktaeim.seoulwithyou.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Adapter.BillboardRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Dialog.SearchDetailDialog;
import com.example.parktaeim.seoulwithyou.Model.BillboardItem;
import com.example.parktaeim.seoulwithyou.MyLayoutManager;
import com.example.parktaeim.seoulwithyou.MyRecyclerView;
import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-26.
 */

public class SearchCompanionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    private String picture, title, distance, id;
    private ImageView coverPicture;
    private TextView courseTitle, courstDistance;

    private RecyclerViewClickListener listener;

    private SearchDetailDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_companion);

        Intent intent = getIntent();
        picture = intent.getStringExtra("picture");
        title = intent.getStringExtra("title");
        distance = intent.getStringExtra("distance");
        id = intent.getStringExtra("id");

        coverPicture = (ImageView) findViewById(R.id.picture);
        courseTitle = (TextView) findViewById(R.id.title);
        courstDistance = (TextView) findViewById(R.id.distance);

        recyclerView = (RecyclerView) findViewById(R.id.billboardRecyclerView);
        recyclerView.hasFixedSize();
//        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        manager = new MyLayoutManager(getApplicationContext());
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);

        listener = (view, position, location) -> {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 10);
            recyclerView.smoothScrollToPosition(position);

            dialog = new SearchDetailDialog(SearchCompanionActivity.this, location[0], location[1]);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

            dialog.show();
            dialog.getWindow().setLayout(MainActivity.screenWidth, MainActivity.screenHeight);
        };


        Glide.with(this).load(picture).into(coverPicture);
        courseTitle.setText(title);
        courstDistance.setText(distance);
        setData();
    }

    public void setData() {
        ArrayList<BillboardItem> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            BillboardItem item = new BillboardItem(
                    "http://img.hb.aicdn.com/03d474bbe20efb7df9aed4541ace70b53b53c70bdfe3-8djYVv_fw658",
                    "title" + i,
                    "date" + i,
                    "name" + i);

            items.add(item);
        }

        adapter = new BillboardRecyclerViewAdapter(getApplicationContext(), items, listener);
        recyclerView.setAdapter(adapter);
    }
}
