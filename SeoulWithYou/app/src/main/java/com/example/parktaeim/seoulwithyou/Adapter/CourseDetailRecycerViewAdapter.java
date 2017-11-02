package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Model.CourseDetailItem;
import com.example.parktaeim.seoulwithyou.Model.CourseItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-11.
 */

public class CourseDetailRecycerViewAdapter extends RecyclerView.Adapter<CourseDetailRecycerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CourseDetailItem> details;

    public CourseDetailRecycerViewAdapter(Context context, ArrayList<CourseDetailItem> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = details.get(position).getPicUrl();
        Glide.with(context).load(url).into(holder.detailPic);
        holder.detailExplaination.setMovementMethod(new ScrollingMovementMethod());
        holder.detailExplaination.setText(details.get(position).getDetail());
        holder.detailName.setText(details.get(position).getName());
        holder.courseNum.setText(String.valueOf(details.get(position).getNumber() + 1));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseNum;
        TextView detailName;
        TextView detailExplaination;
        ImageView detailPic;
        View detailItem;

        public ViewHolder(final View itemView) {
            super(itemView);

            courseNum = (TextView) itemView.findViewById(R.id.courseNum);
            detailName = (TextView) itemView.findViewById(R.id.detailCourseName);
            detailExplaination = (TextView) itemView.findViewById(R.id.detailExplanation);
            detailPic = (ImageView) itemView.findViewById(R.id.detailPic);
            detailItem = (CardView) itemView.findViewById(R.id.detailItem);
        }
    }
}
