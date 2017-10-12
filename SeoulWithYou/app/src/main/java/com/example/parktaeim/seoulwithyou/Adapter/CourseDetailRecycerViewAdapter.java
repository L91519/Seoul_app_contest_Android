package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.parktaeim.seoulwithyou.Model.CourseDetail;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-11.
 */

public class CourseDetailRecycerViewAdapter extends RecyclerView.Adapter<CourseDetailRecycerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CourseDetail> details;

    public CourseDetailRecycerViewAdapter(Context context, ArrayList<CourseDetail> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
