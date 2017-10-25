package com.example.parktaeim.seoulwithyou.Fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Adapter.CourseDetailRecycerViewAdapter;
import com.example.parktaeim.seoulwithyou.Adapter.CourseRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CourseDetailItem;
import com.example.parktaeim.seoulwithyou.Model.CourseItem;
import com.example.parktaeim.seoulwithyou.R;
import com.stone.pile.libs.PileLayout;

import java.util.ArrayList;

import util.Utils;

/**
 * Created by user on 2017-10-11.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ModernFragment extends Fragment implements RecyclerView.OnScrollChangeListener{

    private RecyclerView detailRecyclerView;
    private RecyclerView.Adapter detailAdapter;
    private RecyclerView.LayoutManager detailManger;
    private ArrayList<CourseItem> courseItems;
    private ObjectAnimator transitionAnimator;
    private ImageButton companionBtn;
    private Animator.AnimatorListener animatorListener;
    private PileLayout pileLayout;

    private int lastDisplay = -1;
    private float transitionValue;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modern, container, false);

//        fab = (FloatingActionButton) view.findViewById(R.id.companionBtn);
        companionBtn = (ImageButton) view.findViewById(R.id.companionBtn);

        detailRecyclerView = (RecyclerView) view.findViewById(R.id.detailRecyclerView);
        detailManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        detailRecyclerView.hasFixedSize();
        detailRecyclerView.setLayoutManager(detailManger);
        pileLayout = (PileLayout) view.findViewById(R.id.pileLayout);

        detailRecyclerView.setOnScrollChangeListener(this);

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        dataSet2();
        initDtalist();

        pileLayout.setAdapter(new PileLayout.Adapter() {
            @Override
            public int getLayoutId() {
                return R.layout.item_layout;
            }

            @Override
            public int getItemCount() {
                return courseItems.size();
            }

            @Override
            public void bindView(View view, int position) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                if (viewHolder == null) {
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                    view.setTag(viewHolder);
                }
                Glide.with(getContext()).load(courseItems.get(position).getPicUrl()).into(viewHolder.imageView);
            }

            @Override
            public void displaying(int position) {
                if (lastDisplay < 0) {
                    initSecene(position);
                    lastDisplay = 0;
                } else if (lastDisplay != position) {
                    transitionSecene(position);
                    lastDisplay = position;
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
            }
        });

        return view;
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            companionBtn.setVisibility(View.VISIBLE);
                            companionBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.show));
                        }
                    }, 1000);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if ((dy > 0) || (dy < 0)) {
                    companionBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.hide));
                    companionBtn.setVisibility(View.INVISIBLE);
                }
            }

        });
    }

    private void initSecene(int position) {
    }

    private void transitionSecene(int position) {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);
    }

    private void initDtalist() {
        courseItems = new ArrayList<>();
        CourseItem item1 = new CourseItem("http://img.hb.aicdn.com/10dd7b6eb9ca02a55e915a068924058e72f7b3353a40d-ZkO3ko_fw658", "palace", "far");
        courseItems.add(item1);
        CourseItem item2 = new CourseItem("http://img.hb.aicdn.com/a3a995b26bd7d58ccc164eafc6ab902601984728a3101-S2H0lQ_fw658", "dessert", "as well");
        courseItems.add(item2);
        CourseItem item3 = new CourseItem("http://pic4.nipic.com/20091124/3789537_153149003980_2.jpg", "kingdom", "near");
        courseItems.add(item3);
    }

    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
    }

    public float getTransitionValue() {
        return transitionValue;
    }

    class ViewHolder {
        ImageView imageView;
    }}
