package com.example.parktaeim.seoulwithyou.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Adapter.BillboardRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.BillboardItem;
import com.example.parktaeim.seoulwithyou.MyLayoutManager;
import com.example.parktaeim.seoulwithyou.MyScrollView;
import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.RecyclerViewClickListener;
import com.example.parktaeim.seoulwithyou.ScrollViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-26.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class SearchCompanionActivity extends AppCompatActivity implements ScrollViewListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    private String picture, title, distance, id;
    private ImageView coverPicture;
    private TextView courseTitle, courstDistance;
    private ImageButton xBtn;
    private MyScrollView scrollView;

    private RecyclerViewClickListener listener;

    private ArrayList<BillboardItem> items;
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
        xBtn = (ImageButton) findViewById(R.id.xBtn);
        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.billboardRecyclerView);
        recyclerView.hasFixedSize();
//        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        manager = new MyLayoutManager(getApplicationContext());
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        int height = (int) ((float)MainActivity.screenHeight * 0.6);
        Log.d("---height", String.valueOf(height));
        params.height = height;
        recyclerView.setLayoutParams(params);
        recyclerView.setNestedScrollingEnabled(false);

        listener = (view, position, location) -> {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 10);
            recyclerView.smoothScrollToPosition(position);

            Intent dialogIntent = new Intent(getApplicationContext(), SearchDetailDialogActivity.class);
            dialogIntent.putExtra("location", location);
            dialogIntent.putExtra("pic", items.get(position).getPic());
            dialogIntent.putExtra("title", items.get(position).getTitle());
            dialogIntent.putExtra("name", items.get(position).getName());
            dialogIntent.putExtra("date", items.get(position).getDate());
            startActivity(dialogIntent);
        };


        Glide.with(this).load(picture).into(coverPicture);
        courseTitle.setText(title);
        courstDistance.setText(distance);
        setData();

        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchCompanionActivity.this.finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


    }

    public void setData() {
        items = new ArrayList<>();

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

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            recyclerView.setNestedScrollingEnabled(false);
        }
    }
}
