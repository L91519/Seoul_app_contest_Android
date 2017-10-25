package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.parktaeim.seoulwithyou.Model.BillboardItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-26.
 */

public class BillboardRecyclerViewAdapter extends RecyclerView.Adapter<BillboardRecyclerViewAdapter.ViewHolder> {

    private Context context;
    ArrayList<BillboardItem> items;

    public BillboardRecyclerViewAdapter(Context context, ArrayList<BillboardItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public BillboardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billboard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BillboardRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position).getPic())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.profilPic);
        holder.date.setText(items.get(position).getDate());
        holder.name.setText(items.get(position).getName());
        holder.title.setText(items.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilPic;
        TextView name;
        TextView date;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            profilPic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.nameText);
            date = itemView.findViewById(R.id.billboardDate);
            title = itemView.findViewById(R.id.titleText);
        }
    }
}
