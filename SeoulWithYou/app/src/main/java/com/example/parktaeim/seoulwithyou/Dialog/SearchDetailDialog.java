package com.example.parktaeim.seoulwithyou.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.parktaeim.seoulwithyou.Activity.MainActivity;
import com.example.parktaeim.seoulwithyou.Activity.MyPageDialogActivity;
import com.example.parktaeim.seoulwithyou.Adapter.CommentRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CommentItem;
import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2017-10-28.
 */

public class SearchDetailDialog extends Dialog {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    private String name, date, picture, title;
    private TextView nameText, dateText, titleText;
    private ImageView pictureImage, userPic;
    private ImageButton xBtn, drawerBtn;
    private TextInputEditText commentText;
    private TextView enterBtn, userName, userGender, userAge, content;
    private RelativeLayout commentBtn, dialogContainer;

    private int[] position;
    private int /*어떤 코스인지*/number, /*게시글 받고 거기서 받은 아이디*/postNo;

    private ArrayList<CommentItem> commentItems;

    public SearchDetailDialog(@NonNull Context context, int[] position, int number, String name, String date, String picture, String title) {
        super(context);
        this.position = position;
//        location = new int[]{top, bottom, left, right};
        this.number = number;
        //게시글의 넘버를 받아와야함
        this.name = name;
        this.date = date;
        this.picture = picture;
        this.title = title;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_detail);

        userName = (TextView) findViewById(R.id.userName);
        userGender = (TextView) findViewById(R.id.gender);
        userAge = (TextView) findViewById(R.id.age);
        content = (TextView) findViewById(R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);
        getContent(number);
        getData();

        nameText = (TextView) findViewById(R.id.nameText);
        dateText = (TextView) findViewById(R.id.billboardDate);
        titleText = (TextView) findViewById(R.id.titleText);
        pictureImage = (ImageView) findViewById(R.id.profilePic);
        userPic = (ImageView) findViewById(R.id.userPic);
        xBtn = (ImageButton) findViewById(R.id.xBtn);
        drawerBtn = (ImageButton) findViewById(R.id.drawerBtn);
        commentText = (TextInputEditText) findViewById(R.id.commentText);
        enterBtn = (TextView) findViewById(R.id.enterBtn);
        commentBtn = (RelativeLayout) findViewById(R.id.commentBtn);
        dialogContainer = (RelativeLayout) findViewById(R.id.dialogContainer);

        nameText.setText(name);
        dateText.setText(date);
        titleText.setText(title);

        int height = (int) ((float) MainActivity.screenHeight * 0.6);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, height);

        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentText.getText().toString();

                SharedPreferences tokenPref = getContext().getSharedPreferences("tokenPref", MODE_PRIVATE);
                String token = tokenPref.getString("token", "null");
                Service.getRetrofit(getContext()).postComment(token, comment, number).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Log.d("postCommentStatus : ", String.valueOf(response.code()));
                            getData();
                        } else {
                            Log.d("log code", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        pictureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyPageDialogActivity.class);
//                intent.putExtra("mypage_id",items.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

    }

    //게시글 가져오는 부분
    public void getContent(int no) {
        SharedPreferences tokenPref = getContext().getSharedPreferences("tokenPref", MODE_PRIVATE);
        String authorization = tokenPref.getString("token", "null");
        Service.getRetrofit(getContext()).getPost(authorization, no).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();

                postNo = jsonObject.getAsJsonPrimitive("postNo").getAsInt();
                String sContent = jsonObject.getAsJsonPrimitive("content").getAsString();
                int sItemId = jsonObject.getAsJsonPrimitive("itemNo").getAsInt(); //태임이가 쓸꺼, 어디다가 저장해두자
                String sUserName = jsonObject.getAsJsonPrimitive("userName").getAsString();
                String sCreatedAt = jsonObject.getAsJsonPrimitive("createdAt").getAsString();
//                String sCreatedAt ="date";
                String sUserId = jsonObject.getAsJsonPrimitive("userId").getAsString();
                boolean sGender = jsonObject.getAsJsonPrimitive("userSex").getAsBoolean();
                int iUserAge = jsonObject.getAsJsonPrimitive("userAge").getAsInt();
                String sUserImg = null;
                try {
                    sUserImg = jsonObject.getAsJsonPrimitive("userImage").getAsString();
                } catch (ClassCastException e) {
                    sUserImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwQzkGr6zOpQD7nG8YtZIIzlutO7kOL1NkG88BOH5fNVBqkwWc";
                }
                String sTitle = jsonObject.getAsJsonPrimitive("title").getAsString();

                //title은 다이얼로그 띄울떄 받는 값으로 다 대채함.
                userName.setText(sUserName);
                String tmpGender = null;
                if (sGender == false) {
                    tmpGender = "여자";
                } else {
                    tmpGender = "남자";
                }
                userGender.setText(tmpGender);
                String tmpAge = String.valueOf(iUserAge) + "세";
                userAge.setText(tmpAge);
                content.setText(sContent);
                Glide.with(getContext()).load(sUserImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(pictureImage);
                Glide.with(getContext()).load(sUserImg).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userPic);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    //코맨트 가져오는 부분
    public void getData() {
        SharedPreferences tokenPref = getContext().getSharedPreferences("tokenPref", MODE_PRIVATE);
        String authorization = tokenPref.getString("token", "null");
        Service.getRetrofit(getContext()).getComment(authorization, number).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonArray jsonArray = response.body().getAsJsonArray("data");
                    JsonArray jsonElements = jsonArray.getAsJsonArray();
                    commentItems = getCommentArray(jsonElements);
                    adapter = new CommentRecyclerViewAdapter(getContext(), commentItems);
                    recyclerView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public ArrayList<CommentItem> getCommentArray(JsonArray jsonElements) {
        ArrayList<CommentItem> items = new ArrayList<>();

        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);

            String name = jsonObject.getAsJsonPrimitive("userName").getAsString();
            String createdAt = jsonObject.getAsJsonPrimitive("createdAt").getAsString();
            String content = jsonObject.getAsJsonPrimitive("content").getAsString();
            String userId = jsonObject.getAsJsonPrimitive("userId").getAsString();
            boolean gender = jsonObject.getAsJsonPrimitive("userSex").getAsBoolean();
            int age = jsonObject.getAsJsonPrimitive("userAge").getAsInt();
            String sUserImg = null;
            try {
                sUserImg = jsonObject.getAsJsonPrimitive("userImage").getAsString();
            } catch (ClassCastException e) {
                sUserImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwQzkGr6zOpQD7nG8YtZIIzlutO7kOL1NkG88BOH5fNVBqkwWc";
            }

            String sGender = null;
            if(gender == false) {
                sGender = "여자";
            } else {
                sGender = "남자";
            }

            items.add(new CommentItem(name, sGender, String.valueOf(age), content, sUserImg,userId));
        }

        return items;
    }
}
