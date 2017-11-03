package com.example.parktaeim.seoulwithyou.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.parktaeim.seoulwithyou.Dialog.BillboardAddDialog;
import com.example.parktaeim.seoulwithyou.Dialog.SearchDetailDialog;
import com.example.parktaeim.seoulwithyou.Model.BillboardItem;
import com.example.parktaeim.seoulwithyou.MyLayoutManager;
import com.example.parktaeim.seoulwithyou.MyScrollView;
import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.RecyclerViewClickListener;
import com.example.parktaeim.seoulwithyou.ScrollViewListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-10-26.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class SearchCompanionActivity extends AppCompatActivity implements ScrollViewListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    private String picture, title, distance;
    private int itemNo;
    private ImageView coverPicture;
    private TextView courseTitle, courstDistance;
    private ImageButton xBtn, addBtn;
    private MyScrollView scrollView;
    private RelativeLayout container;
    private RecyclerViewClickListener listener;
    private SearchDetailDialog dialog;
    private BillboardAddDialog addDialog;
    private ArrayList<BillboardItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_companion);

        Intent intent = getIntent();
        picture = intent.getStringExtra("picture");
        title = intent.getStringExtra("title");
        //코스 아이디
        itemNo = intent.getIntExtra("id", 0);
        Log.d("---log", String.valueOf(itemNo));

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
        int height = (int) ((float) MainActivity.screenHeight * 0.6);
        params.height = height;
        recyclerView.setLayoutParams(params);
        recyclerView.setNestedScrollingEnabled(false);

        listener = (view, position, location) -> {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 10);
            recyclerView.smoothScrollToPosition(position);

//            RelativeLayout.LayoutParams containerParams =  (RelativeLayout.LayoutParams) container.getLayoutParams();
//            containerParams.setMargins(0, MainActivity.screenHeight - height, 0, 0);
//            container.setLayoutParams(containerParams);

            dialog = new SearchDetailDialog(SearchCompanionActivity.this, location,
                    items.get(position).getPostNo(),
                    items.get(position).getName(),
                    items.get(position).getDate(),
                    items.get(position).getPic(),
                    items.get(position).getTitle());
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
                    addBtn.setImageResource(R.drawable.add);
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
                addDialog = new BillboardAddDialog(SearchCompanionActivity.this);
                addDialog.show();

                addDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (addDialog.getsContent() == null || addDialog.getsTitle() == null || addDialog.getsTitle().isEmpty() || addDialog.getsContent().isEmpty()) {
                        } else if(addDialog.getsContent() != null || addDialog.getsTitle() != null) {
                            postBillboard(addDialog.getsTitle(), addDialog.getsContent());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    //게시글 보내기
    public void postBillboard(String title, String content) {
        SharedPreferences tokenPref = getSharedPreferences("tokenPref",MODE_PRIVATE);
        String authorization = tokenPref.getString("token", "null");
        Service.getRetrofit(getApplicationContext())
                .postList(authorization, title, content, itemNo)
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    setData();
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("--postBillboardLog", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //게시글 가져오기
    public void setData() {
        items = new ArrayList<>();
        SharedPreferences tokenPref = getSharedPreferences("tokenPref",MODE_PRIVATE);
        String authorization = tokenPref.getString("token", "null");
        Service.getRetrofit(getApplicationContext()).getList(authorization, itemNo).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    JsonArray jsonElements = jsonArray.getAsJsonArray();
                    items = getBillboardArray(jsonElements);
                    adapter = new BillboardRecyclerViewAdapter(getApplicationContext(), items, listener);
                    recyclerView.setAdapter(adapter);
                } else {
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });


        adapter = new BillboardRecyclerViewAdapter(getApplicationContext(), items, listener);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<BillboardItem> getBillboardArray(JsonArray jsonElements) {
        ArrayList<BillboardItem> billboardItems = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);

            boolean gender = jsonObject.getAsJsonPrimitive("userSex").getAsBoolean();
            String title = jsonObject.getAsJsonPrimitive("title").getAsString();
            String userName = jsonObject.getAsJsonPrimitive("userName").getAsString();
            String userId = jsonObject.getAsJsonPrimitive("userId").getAsString();
            int userAge = jsonObject.getAsJsonPrimitive("userAge").getAsInt();
            int postNo = jsonObject.getAsJsonPrimitive("postNo").getAsInt();
//            String createdAt = jsonObject.getAsJsonPrimitive("createAt").getAsString();
            String createdAt = "date";
            String sUserImg = null;
            try {
                sUserImg = jsonObject.getAsJsonPrimitive("userImage").getAsString();
            } catch (ClassCastException e) {
                sUserImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwQzkGr6zOpQD7nG8YtZIIzlutO7kOL1NkG88BOH5fNVBqkwWc";
            }
            billboardItems.add(new BillboardItem(postNo, sUserImg, title, createdAt, userName, userId, gender, userAge));
        }

        return billboardItems;
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
