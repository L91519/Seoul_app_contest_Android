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

import com.example.parktaeim.seoulwithyou.Model.ChatData;
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

import java.lang.reflect.InvocationTargetException;
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
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().getRoot();

    private String myName;
    private TextView titleYourName;
    private EditText messageEditText;
    private Button sendMessageBtn;
    String myId = "ti5356";
    String yourId = "mb5356";
    String chatRoomId;
    private RecyclerView recyclerView;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(myId).child(yourId);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        recyclerView = (RecyclerView) findViewById(R.id.chatMessageRecyclerView);
        setView();

        yourId = getIntent().getStringExtra("yourId");

//        startChat();
//        sendMessage();
        setChatView();

    }

    private void setChatView() {
        //User id
        int meId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "Michael";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = "Emily";

        final User me = new User(meId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);

        mChatView = (ChatView) findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                FirebaseDatabase.getInstance().getReference().setValue(myId);
//                rootRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        rootRef.setValue(myId);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

                //new message
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
                //Reset edit text

                //Receive message
                final Message receivedMessage = new Message.Builder()
                        .setUser(you)
                        .setRightMessage(false)
                        .setMessageText(ChatBot.talk(me.getName(), message.getMessageText()))
                        .build();

                // This is a demo bot
                // Return within 3 seconds
                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.receive(receivedMessage);
                    }
                }, sendDelay);


//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        databaseReference.setValue("dlkfjs");
//                        databaseReference.setValue("dlkfjs");
//                        databaseReference.setValue("dlkfjs");
//                        databaseReference.setValue("dlkfjs");
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

                ChatData chatData = new ChatData(String.valueOf(myId),mChatView.getInputText(),"2017-04-23");
                databaseReference.child("message").push().setValue(chatData);

                mChatView.setInputText("");

//                databaseReference.push();
//                Map<String, Object> map = new HashMap<String, Object>();
//                String key = databaseReference.push().getKey();
//                Log.d("map======", map.toString());
////
//                databaseReference.updateChildren(map);
//
//                DatabaseReference root = databaseReference.child(key);
//                Map<String, Object> objectMap = new HashMap<String, Object>();
//
//                Log.d("message", message.toString());
//                objectMap.put("name", myId);
//                objectMap.put("message", message);
//
//                Log.d("object map ===", objectMap.toString());
//
//                //                root.updateChildren(objectMap);
//                databaseReference.setValue(objectMap);

            }

        });


    }
//
//    private void sendMessage() {
//        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Map<String, Object> map = new HashMap<String, Object>();
//                String key = reference.push().getKey();
//
//                rootRef.updateChildren(map);
//
//                DatabaseReference root = rootRef.child(key);
//
//                Map<String, Object> objectMap = new HashMap<String, Object>();
//
//                objectMap.put("name", yourId);
//                objectMap.put("message", messageEditText.getText().toString());
//
//                root.updateChildren(objectMap);
//
//                messageEditText.setText("");
//
//
//
////                long time = System.currentTimeMillis();
////                SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
////                String msgTime = dayTime.format(new Date(time));
////
////                ChatModel chatModel = new ChatModel(messageEditText.getText().toString(), myId, msgTime);
////                Map<String, Object> comment = chatModel.toMap();
////                FirebaseDatabase.getInstance().getReference().child("chat room").child("comments").push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
////                    @Override
////                    public void onSuccess(Void aVoid) {
////
////                    }
////                });
//            }
//        });
//    }

//    private void startChat() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//
////        databaseReference.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////
////
////                // 상대방 ID 있는지 체크
////                // 있으면 채팅데이터 불러오기 / 없으면 채팅방 새로 생성
//////                FirebaseDatabase.getInstance().getReference().child("chat room").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
////                FirebaseDatabase.getInstance().getReference().child("chat room").push().setValue(yourId).addOnSuccessListener(new OnSuccessListener<Void>() {
////                    @Override
////                    public void onSuccess(Void aVoid) {
////
////                    }
////                });
////
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
//
//    }

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
