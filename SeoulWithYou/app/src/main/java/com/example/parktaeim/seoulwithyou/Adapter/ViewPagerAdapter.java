package com.example.parktaeim.seoulwithyou.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.parktaeim.seoulwithyou.Fragment.ArtFragment;
import com.example.parktaeim.seoulwithyou.Fragment.FoodFragment;
import com.example.parktaeim.seoulwithyou.Fragment.HealingFragment;
import com.example.parktaeim.seoulwithyou.Fragment.ModernFragment;
import com.example.parktaeim.seoulwithyou.Fragment.TraditionFragment;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-11.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private final int TAB_NUM = 5;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                FoodFragment food = new FoodFragment();
                return food;
            case 1 :
                TraditionFragment tradition = new TraditionFragment();
                return tradition;
            case 2 :
                ModernFragment modern = new ModernFragment();
                return modern;
            case 3 :
                ArtFragment art = new ArtFragment();
                return art;
            case 4 :
                HealingFragment healing = new HealingFragment();
                return healing;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_NUM;
    }
}
