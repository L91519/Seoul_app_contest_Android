package com.example.parktaeim.seoulwithyou.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Adapter.BillboardRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Dialog.BillboardDialog;
import com.example.parktaeim.seoulwithyou.Dialog.SearchDetailDialog;
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
    private ImageButton xBtn, addBtn;
    private MyScrollView scrollView;
    private RelativeLayout container;
    private RecyclerViewClickListener listener;
    private SearchDetailDialog dialog;
    private BillboardDialog addDialog;
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
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(this);
        container = (RelativeLayout) findViewById(R.id.container);

        recyclerView = (RecyclerView) findViewById(R.id.billboardRecyclerView);
        recyclerView.hasFixedSize();
//        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        manager = new MyLayoutManager(getApplicationContext());
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        int height = (int) ((float)MainActivity.screenHeight * 0.6);
        params.height = height;
        recyclerView.setLayoutParams(params);
        recyclerView.setNestedScrollingEnabled(false);

        listener = (view, position, location) -> {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 10);
            recyclerView.smoothScrollToPosition(position);

//            RelativeLayout.LayoutParams containerParams =  (RelativeLayout.LayoutParams) container.getLayoutParams();
//            containerParams.setMargins(0, MainActivity.screenHeight - height, 0, 0);
//            container.setLayoutParams(containerParams);

            dialog = new SearchDetailDialog(SearchCompanionActivity.this, location);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                }
            });

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });

            dialog.show();
            dialog.getWindow().setLayout(MainActivity.screenWidth, MainActivity.screenHeight);
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

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDialog = new BillboardDialog(SearchCompanionActivity.this);
                addDialog.show();
                items.add(new BillboardItem(id, "picture", addDialog.getsTitle(), "date", "name"));
                adapter.notifyDataSetChanged();
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
