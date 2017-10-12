package com.example.parktaeim.seoulwithyou.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parktaeim.seoulwithyou.Adapter.CourseDetailRecycerViewAdapter;
import com.example.parktaeim.seoulwithyou.Adapter.CourseRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CourseDetailItem;
import com.example.parktaeim.seoulwithyou.Model.CourseItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-11.
 */

public class ModernFragment extends Fragment{

    private RecyclerView courseRecyclerView;
    private RecyclerView.Adapter courseAdapter;
    private RecyclerView.LayoutManager courseManager;

    private RecyclerView detailRecyclerView;
    private RecyclerView.Adapter detailAdapter;
    private RecyclerView.LayoutManager detailManger;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modern, container, false);

        courseRecyclerView = (RecyclerView) view.findViewById(R.id.courseRecyclerView);
        courseManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        courseRecyclerView.hasFixedSize();
        courseRecyclerView.setLayoutManager(courseManager);

        detailRecyclerView = (RecyclerView) view.findViewById(R.id.detailRecyclerView);
        detailManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        detailRecyclerView.hasFixedSize();
        detailRecyclerView.setLayoutManager(detailManger);
        dataSet();
        dataSet2();

        return view;
    }

    public void dataSet() {
        ArrayList<CourseItem> items = new ArrayList<>();

        CourseItem item1 = new CourseItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM", "name 1st", "far");
        items.add(item1);
        CourseItem item2 = new CourseItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM", "name 2nd", "near");
        items.add(item2);
        CourseItem item3 = new CourseItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM", "name 3rd", "bear it");
        items.add(item3);

        courseAdapter = new CourseRecyclerViewAdapter(items, getContext());
        courseRecyclerView.setAdapter(courseAdapter);
    }

    public void dataSet2() {
        ArrayList<CourseDetailItem> items = new ArrayList<>();

        CourseDetailItem item1 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM", "name 1st", "far", "details");
        items.add(item1);
        CourseDetailItem item2 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM", "name 1st", "far", "details");
        items.add(item2);
        CourseDetailItem item3 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM", "name 1st", "far", "details");
        items.add(item3);

        detailAdapter = new CourseDetailRecycerViewAdapter(getContext(), items);
        detailRecyclerView.setAdapter(detailAdapter);
    }
}
