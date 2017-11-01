package com.example.parktaeim.seoulwithyou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.parktaeim.seoulwithyou.Activity.MyPageDialogActivity;
import com.example.parktaeim.seoulwithyou.Holder.BillboardViewHolder;
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
        return new BillboardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BillboardViewHolder) {

            final BillboardViewHolder viewHolder = (BillboardViewHolder) holder;

            Glide.with(context)
                    .load(items.get(position).getPic())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolder.profilPic);
            viewHolder.date.setText(items.get(position).getDate());
            viewHolder.name.setText(items.get(position).getName());
            viewHolder.title.setText(items.get(position).getTitle());

            viewHolder.profilPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyPageDialogActivity.class);
                    intent.putExtra("mypage_id",items.get(position).getNo());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
