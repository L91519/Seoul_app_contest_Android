package com.example.parktaeim.seoulwithyou.Dialog;

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
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-28.
 */

public class SearchDetailDialog extends Dialog {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;

    private String name, date, picture, title;
    private TextView nameText, dateText, titleText;
    private ImageView pictureImage;
    private ImageButton xBtn;
    private TextInputEditText commentText;
    private TextView enterBtn;
    private RelativeLayout commentBtn, dialogContainer;

    private int[] position;

    public SearchDetailDialog(@NonNull Context context, int[] position) {
        super(context);
        this.position = position;
//        location = new int[]{top, bottom, left, right};

    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_detail);


        recyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        manager.hasFocus();
        recyclerView.setLayoutManager(manager);
        setData();

        nameText = (TextView) findViewById(R.id.nameText);
        dateText = (TextView) findViewById(R.id.billboardDate);
        titleText = (TextView) findViewById(R.id.titleText);
        pictureImage = (ImageView) findViewById(R.id.profilePic);
        xBtn = (ImageButton) findViewById(R.id.xBtn);
        commentText = (TextInputEditText) findViewById(R.id.commentText);
        enterBtn = (TextView) findViewById(R.id.enterBtn);
        commentBtn = (RelativeLayout) findViewById(R.id.commentBtn);
        dialogContainer = (RelativeLayout) findViewById(R.id.dialogContainer);

        nameText.setText(name);
        dateText.setText(date);
        titleText.setText(title);
        Glide.with(getContext()).load(picture).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(pictureImage);

        int height = (int) ((float) MainActivity.screenHeight * 0.6);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, height);

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
                Toast.makeText(getContext(), comment, Toast.LENGTH_SHORT).show();
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

    public void setData() {

        ArrayList<CommentItem> items = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            CommentItem item1 = new CommentItem("name" + i, "male" + i, "age" + i, "내가 가겠습니당~~ 만나용가리!" + i, " " );
            items.add(item1);
        }

        adapter = new CommentRecyclerViewAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
    }
}
