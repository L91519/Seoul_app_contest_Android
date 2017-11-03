package com.example.parktaeim.seoulwithyou.Holder;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.RecyclerViewClickListener;

public class BillboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private RecyclerViewClickListener mListener;

    public ImageView profilPic;
    public TextView name, date, title;
    public ImageButton button;

    public BillboardViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);

        mListener = listener;

        profilPic = itemView.findViewById(R.id.billboard_profilePic);
        name = itemView.findViewById(R.id.nameText);
        date = itemView.findViewById(R.id.billboardDate);
        title = itemView.findViewById(R.id.titleText);
        button = itemView.findViewById(R.id.drawerBtn);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        int[] location = new int[2];
//        int x, y;
//        itemView.getLocationInWindow(location);
//        x = location[0];
//        y = location[1];


        int[] location;

        int top;
        int bottom;
        int left;
        int right;

        Rect r = new Rect();
        itemView.getGlobalVisibleRect(r);

        top = r.top;
        bottom = r.bottom;
        left = r.left;
        right = r.right;

        location = new int[]{top, bottom, left, right};

        mListener.onClick(view, getAdapterPosition(), location);
    }
}
