package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return courseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coursePic;
        TextView courseName;
        TextView courseDistance;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);

            coursePic = (ImageView) itemView.findViewById(R.id.coursePic);
            courseName = (TextView) itemView.findViewById(R.id.courseNameText);
            courseDistance = (TextView) itemView.findViewById(R.id.courseDistanceText);
            view = (CardView) itemView.findViewById(R.id.courseItem);
        }
    }
}
