package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.parktaeim.seoulwithyou.Adapter.ChatListRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.ChatListItem;
import com.example.parktaeim.seoulwithyou.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by parktaeim on 2017. 10. 17..
 */

public class ChatListActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot();
    private ArrayList<String> chatRoomList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        setRecyclerView();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                chatRoomList.clear();
                chatRoomList.addAll(set);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setRecyclerView() {
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.chatListRecyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();
        items.add(new ChatListItem("data:image/jpeg;base64,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",
                "소영", "도깨비 안보면 죽빵^^", "오후 12:22", null, "sy5356"));
        items.add(new ChatListItem("http://img.insight.co.kr/static/2017/01/23/700/9J925O2P1HMFX7XLOU0V.jpg",
                "엄마", "도깨비 안보면 죽빵^^", "오후 12:22", null, "yh5356"));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RequestManager requestManager = Glide.with(getApplicationContext());
        adapter = new ChatListRecyclerViewAdapter(context, items, requestManager);
        recyclerView.setAdapter(adapter);

        //구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
