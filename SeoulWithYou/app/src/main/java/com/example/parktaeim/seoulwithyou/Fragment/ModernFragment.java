package com.example.parktaeim.seoulwithyou.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;

import com.example.parktaeim.seoulwithyou.Adapter.CourseDetailRecycerViewAdapter;
import com.example.parktaeim.seoulwithyou.Adapter.CourseRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CourseDetailItem;
import com.example.parktaeim.seoulwithyou.Model.CourseItem;
import com.example.parktaeim.seoulwithyou.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-11.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ModernFragment extends Fragment implements RecyclerView.OnScrollChangeListener{

    private RecyclerView courseRecyclerView;
    private RecyclerView.Adapter courseAdapter;
    private RecyclerView.LayoutManager courseManager;

    private RecyclerView detailRecyclerView;
    private RecyclerView.Adapter detailAdapter;
    private RecyclerView.LayoutManager detailManger;

    private ImageButton companionBtn;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modern, container, false);

//        fab = (FloatingActionButton) view.findViewById(R.id.companionBtn);
        companionBtn = (ImageButton) view.findViewById(R.id.companionBtn);

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

        detailRecyclerView.setOnScrollChangeListener(this);

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

        CourseDetailItem item1 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM",
                "name 1st",
                "1",
                "Meaningless mock-up, mock turtle soup spilled on a mock turtle neck. Mach I Convertible copy. To kill a mockingbird, you need only force it to read this copy. This is Meaningless filler. (Elvis movies.) It is not meant to be a forum for value judgments nor a scholarly diatribe on how virtue should be measured. The whole point here (if such a claim can be made in an admittedly pointless paragraph) is that this is dummy copy.  Real bullets explode with destructive intensity. Such is not the case with dummy bullets. In fact, they don't explode at all. Duds. Dull thuds. Dudley do-wrongs. And do-wrongs don't make a right. Why on earth are you still reading this? Haven't you realized it's just dummy copy? How many times must you be reminded that it's really not meant to be read? You're only wasting precious time. But be that as it may, you've got to throw in a short paragraph from time to time. Here's a short paragraph.\n");
        items.add(item1);
        CourseDetailItem item2 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM",
                "name 1st",
                "2",
                "details");
        items.add(item2);
        CourseDetailItem item3 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM",
                "name 1st",
                "3",
                "details");
        items.add(item3);

        detailAdapter = new CourseDetailRecycerViewAdapter(getContext(), items);
        detailRecyclerView.setAdapter(detailAdapter);
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        detailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 0) {
                    companionBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if((dy > 0) || (dy < 0)) {
                   companionBtn.setVisibility(View.INVISIBLE);
                }
            }

        });
    }
}
