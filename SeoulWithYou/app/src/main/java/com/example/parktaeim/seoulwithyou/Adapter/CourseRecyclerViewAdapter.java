package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Model.CourseItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-11.
 */

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder> {

    ArrayList<CourseItem> courseItems;
    Context context;

    public CourseRecyclerViewAdapter(ArrayList<CourseItem> courseItems, Context context) {
        this.courseItems = courseItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = courseItems.get(position).getPicUrl();
        Glide.with(context).load(url).into(holder.coursePic);
        holder.courseName.setText(courseItems.get(position).getPlaceName());
        holder.courseDistance.setText(courseItems.get(position).getPlaceDistance());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return courseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnFocusChangeListener{
        ImageView coursePic;
        TextView courseName;
        TextView courseDistance;
        View view;

        public ViewHolder(final View itemView) {
            super(itemView);

            coursePic = (ImageView) itemView.findViewById(R.id.coursePic);
            courseName = (TextView) itemView.findViewById(R.id.courseNameText);
            courseDistance = (TextView) itemView.findViewById(R.id.courseDistanceText);
            view = (CardView) itemView.findViewById(R.id.courseItem);

        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus) {
                Log.d(view.toString(), "logCheck sucess");
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.current_item_bigger);
                itemView.startAnimation(anim);
                anim.setFillAfter(true);
            } else {
                Log.d(view.toString(), "logCheck fail");
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.other_item_small);
                itemView.startAnimation(anim);
                anim.setFillAfter(true);
            }
        }
    }
}
