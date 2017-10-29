package com.example.parktaeim.seoulwithyou.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.view.Window;
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
import com.example.parktaeim.seoulwithyou.Adapter.CommentRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CommentItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-28.
 */

public class SearchDetailDialogActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    private String name, date, picture, title;
    private TextView nameText, dateText, titleText;
    private ImageView pictureImage;
    private ImageButton xBtn;
    private TextInputEditText commentText;
    private TextView enterBtn;
    private RelativeLayout commentBtn;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_detail);

        recyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);
        setData();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        date = intent.getStringExtra("date");
        picture = intent.getStringExtra("pic");
        title = intent.getStringExtra("title");

        nameText = (TextView) findViewById(R.id.nameText);
        dateText = (TextView) findViewById(R.id.billboardDate);
        titleText = (TextView) findViewById(R.id.titleText);
        pictureImage = (ImageView) findViewById(R.id.profilePic);
        xBtn = (ImageButton) findViewById(R.id.xBtn);
        commentText = (TextInputEditText) findViewById(R.id.commentText);
        enterBtn = (TextView) findViewById(R.id.enterBtn);
        commentBtn = (RelativeLayout) findViewById(R.id.commentBtn);

        nameText.setText(name);
        dateText.setText(date);
        titleText.setText(title);
        Glide.with(getApplicationContext()).load(picture).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(pictureImage);

        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDetailDialogActivity.this.finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentText.getText().toString();
                Toast.makeText(getApplicationContext(), comment, Toast.LENGTH_SHORT).show();
            }
        });

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
