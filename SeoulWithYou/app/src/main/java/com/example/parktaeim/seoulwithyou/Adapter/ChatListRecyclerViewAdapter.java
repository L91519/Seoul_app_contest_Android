package com.example.parktaeim.seoulwithyou.Adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.parktaeim.seoulwithyou.Activity.ChattingActivity;
import com.example.parktaeim.seoulwithyou.Model.ChatListItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by parktaeim on 2017. 10. 25..
 */

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatListItem> items = new ArrayList<>();
    private RequestManager requestManager;
    String yourId;


    public ChatListRecyclerViewAdapter(Context context, ArrayList<ChatListItem> items, RequestManager requestManager) {
        this.context = context;
        this.items = items;
        this.requestManager = requestManager;
    }

    @Override
    public ChatListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatlist,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChatListRecyclerViewAdapter.ViewHolder holder, final int position) {
        requestManager.load(items.get(position).getYourProfile()).into(holder.profileImg);
        holder.yourName.setText(items.get(position).getYourName());
        holder.lastMessage.setText(items.get(position).getLastMessage());
        holder.time.setText(items.get(position).getTime());

//        final String yourName = items.get(position).get();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChattingActivity.class);
                intent.putExtra("yourName",items.get(position).getYourId());
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.fromright,R.anim.toleft);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent,activityOptions.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImg;
        TextView yourName;
        TextView lastMessage;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImg = (ImageView) itemView.findViewById(R.id.profile_img);
            yourName = (TextView) itemView.findViewById(R.id.yourNameTextView);
            lastMessage = (TextView) itemView.findViewById(R.id.lastMessageTextView);
            time = (TextView) itemView.findViewById(R.id.timeTextView);
        }
    }
}
