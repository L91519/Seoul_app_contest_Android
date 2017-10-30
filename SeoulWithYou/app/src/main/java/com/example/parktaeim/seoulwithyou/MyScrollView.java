package com.example.parktaeim.seoulwithyou;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by user on 2017-10-30.
 */

public class MyScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
