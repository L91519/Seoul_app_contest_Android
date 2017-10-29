package com.example.parktaeim.seoulwithyou.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by user on 2017-10-28.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView name, gender, age, content;
    public ImageView profilePic;

    public CommentViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.nameText);
        gender = itemView.findViewById(R.id.gender);
        age = itemView.findViewById(R.id.age);
        content = itemView.findViewById(R.id.content);
        profilePic = itemView.findViewById(R.id.profilePic);
    }
}
