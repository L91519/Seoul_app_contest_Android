package com.example.parktaeim.seoulwithyou.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.RecyclerViewClickListener;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private RecyclerViewClickListener mListener;

    public ImageView profilPic;
    public TextView name, date, title;
    public ImageButton button;

    public ViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);

        mListener = listener;

        profilPic = itemView.findViewById(R.id.profilePic);
        name = itemView.findViewById(R.id.nameText);
        date = itemView.findViewById(R.id.billboardDate);
        title = itemView.findViewById(R.id.titleText);
        button = itemView.findViewById(R.id.drawerBtn);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int[] location = new int[2];
        int x, y;

        mListener.onClick(view, getAdapterPosition());
        button.setImageResource(R.drawable.close);
        itemView.getLocationInWindow(location);

        x = location[0];
        y = location[1];
    }
}
