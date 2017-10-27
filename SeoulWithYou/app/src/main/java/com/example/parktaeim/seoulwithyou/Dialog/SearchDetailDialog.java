package com.example.parktaeim.seoulwithyou.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by user on 2017-10-28.
 */

public class SearchDetailDialog extends Dialog {

    public SearchDetailDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_detail);

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wm.dimAmount = 0.8f;
        getWindow().setAttributes(wm);
    }
}
