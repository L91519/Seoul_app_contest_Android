package com.example.parktaeim.seoulwithyou.Activity;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parktaeim.seoulwithyou.Adapter.ViewPagerAdapter;
import com.example.parktaeim.seoulwithyou.CustomViewPager;
import com.example.parktaeim.seoulwithyou.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    private Toolbar toolbar;
    private ImageView drawerBtn;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout.addTab(tabLayout.newTab().setText("음식"));
        tabLayout.addTab(tabLayout.newTab().setText("전통/역사"));
        tabLayout.addTab(tabLayout.newTab().setText("현대"));
        tabLayout.addTab(tabLayout.newTab().setText("예술/문화"));
        tabLayout.addTab(tabLayout.newTab().setText("힐링"));


        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setPagingEnabled(false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
