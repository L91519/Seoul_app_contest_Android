package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.parktaeim.seoulwithyou.Holder.ViewHolder;
import com.example.parktaeim.seoulwithyou.Model.BillboardItem;
import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-26.
 */

public class BillboardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<BillboardItem> items;
    private RecyclerViewClickListener mListener;

    public BillboardRecyclerViewAdapter(Context context, ArrayList<BillboardItem> items, RecyclerViewClickListener mListener) {
        this.context = context;
        this.items = items;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billboard, parent, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {

            final ViewHolder viewHolder = (ViewHolder) holder;

            Glide.with(context)
                    .load(items.get(position).getPic())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolder.profilPic);
            viewHolder.date.setText(items.get(position).getDate());
            viewHolder.name.setText(items.get(position).getName());
            viewHolder.title.setText(items.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
