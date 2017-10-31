package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Activity.MyPageDialogActivity;
import com.example.parktaeim.seoulwithyou.Holder.CommentViewHolder;
import com.example.parktaeim.seoulwithyou.Model.CommentItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-28.
 */

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<CommentItem> items;

    public CommentRecyclerViewAdapter(Context context, ArrayList<CommentItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder viewHolder = (CommentViewHolder) holder;

        viewHolder.name.setText(items.get(position).getName());
        viewHolder.gender.setText(items.get(position).getGender());
        viewHolder.age.setText(items.get(position).getAge());
        viewHolder.content.setText(items.get(position).getComment());
        Glide.with(context).load(items.get(position).getProfilPic()).into(viewHolder.comment_profilePic);

        viewHolder.comment_profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyPageDialogActivity.class);
//                intent.putExtra("mypage_id",items.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
