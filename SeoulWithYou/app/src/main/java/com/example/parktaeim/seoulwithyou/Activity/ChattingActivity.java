package com.example.parktaeim.seoulwithyou.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaeim.seoulwithyou.Model.ChatModel;
import com.example.parktaeim.seoulwithyou.R;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by parktaeim on 2017. 10. 17..
 */

public class ChattingActivity extends AppCompatActivity {

    private ChatView mChatView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("message");

    private String myName;
    private TextView titleYourName;
    private EditText messageEditText;
    private Button sendMessageBtn;
    String myId = "ti5356";
    String yourId = "mb5356";
    String chatRoomId;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        recyclerView = (RecyclerView) findViewById(R.id.chatMessageRecyclerView);
        setView();

        yourId = getIntent().getStringExtra("yourId");

        startChat();
        sendMessage();

    }

    private void sendMessage() {
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String msgTime = dayTime.format(new Date(time));

                ChatModel chatModel = new ChatModel(messageEditText.getText().toString(), myId, msgTime);
                Map<String,Object> comment = chatModel.toMap();
                FirebaseDatabase.getInstance().getReference().child("chat room").child("comments").push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });
    }

    private void startChat() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("chat rooms");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 상대방 ID 있는지 체크
                // 있으면 채팅데이터 불러오기 / 없으면 채팅방 새로 생성
//                FirebaseDatabase.getInstance().getReference().child("chat room").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                FirebaseDatabase.getInstance().getReference().child("chat room").push().setValue(yourId).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setView() {
        ImageView backIcon = (ImageView) findViewById(R.id.chatting_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleYourName = (TextView) findViewById(R.id.nameTextView);
        titleYourName.setText(getIntent().getStringExtra("yourName"));

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageBtn = (Button) findViewById(R.id.sendMessageBtn);


    }
}

//    private void setView() {
//        ImageView backIcon = (ImageView) findViewById(R.id.chatting_back_icon);
//        backIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        titleYourName = (TextView) findViewById(R.id.nameTextView);
//        titleYourName.setText(getIntent().getStringExtra("yourName"));
//
//        messageEditText = (EditText) findViewById(R.id.messageEditText);
//        sendMessageBtn = (Button) findViewById(R.id.sendMessageBtn);
//
//        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ChatModel chatModel = new ChatModel();
//
//                chatModel.users.put(myId, true);
//                chatModel.users.put(yourId, true);
//
//                if (chatRoomId == null) {
//                    sendMessageBtn.setEnabled(false);
//                    FirebaseDatabase.getInstance().getReference().child("chat room").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            checkChatRoom();
//                        }
//                    });
//                } else {
//                    ChatModel.Comment comment = new ChatModel.Comment();
//                    comment.myId = myId;
//                    comment.message = messageEditText.getText().toString();
//                    FirebaseDatabase.getInstance().getReference().child("chat room").child("comments").push().setValue(comment);
//                }
//            }
//        });
//
//        checkChatRoom();
//    }
//
//    void checkChatRoom() {
//        FirebaseDatabase.getInstance().getReference().child("chat room").orderByChild("users/" + myId).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot item : dataSnapshot.getChildren()) {
//                    ChatModel chatModel = item.getValue(ChatModel.class);
//                    if (chatModel.users.containsKey(yourId)) {
//                        chatRoomId = yourId;
//                        sendMessageBtn.setEnabled(true);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(ChattingActivity.this));
//                        recyclerView.setAdapter(new ChatMessageRecyclerViewAdapter());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

//    class ChatMessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//        List<ChatModel.Comment> comments;
//
//        public ChatMessageRecyclerViewAdapter() {
//            comments = new ArrayList<>();
//
//            FirebaseDatabase.getInstance().getReference().child("chat room").child(chatRoomId).child("comments").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    comments.clear();
//
//                    for (DataSnapshot item : dataSnapshot.getChildren()) {
//                        comments.add(item.getValue(ChatModel.Comment.class));
//                    }
//                    notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
//            return new MessageViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ((MessageViewHolder) holder).messageTextView.setText(comments.get(position).message);
//        }
//
//        @Override
//        public int getItemCount() {
//            Log.d("comments size", String.valueOf(comments.size()));
//            return comments.size();
//        }
//
//        private class MessageViewHolder extends RecyclerView.ViewHolder {
//            public TextView messageTextView;
//
//            public MessageViewHolder(View itemView) {
//                super(itemView);
//                messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
//            }
//        }
//    }
//}
//
