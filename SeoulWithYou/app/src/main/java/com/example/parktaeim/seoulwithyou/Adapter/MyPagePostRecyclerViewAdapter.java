package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.parktaeim.seoulwithyou.Activity.MyPageDialogActivity;
import com.example.parktaeim.seoulwithyou.Model.MyPagePostItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class MyPagePostRecyclerViewAdapter extends RecyclerView.Adapter<MyPagePostRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<MyPagePostItem> items = new ArrayList<>();
    private RequestManager requestManager;

    public MyPagePostRecyclerViewAdapter(Context context, ArrayList<MyPagePostItem> items, RequestManager requestManager) {
        this.context = context;
        this.items = items;
        this.requestManager = requestManager;
    }

    @Override
    public MyPagePostRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypage_post,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyPagePostRecyclerViewAdapter.ViewHolder holder, int position) {
        requestManager.load(items.get(position).getImgUrl()).into(holder.courseImg);
        holder.courseImg.setClipToOutline(true);
        Log.d(items.get(position).getImgUrl(),"glide url");
//        Glide.with(context).load(items.get(position).getImgUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.courseImg);
        holder.courseName.setText(items.get(position).getCourseName());
        holder.postTitle.setText(items.get(position).getPostTitle());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImg;
        TextView courseName;
        TextView writeDate;
        TextView postTitle;
        TextView postContent;

        public ViewHolder(View itemView) {
            super(itemView);
            courseImg = (ImageView) itemView.findViewById(R.id.courseImg);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);

        }
    }
}
