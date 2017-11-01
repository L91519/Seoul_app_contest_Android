package com.example.parktaeim.seoulwithyou.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;

/**
 * Created by user on 2017-10-30.
 */

public class BillboardAddDialog extends Dialog {

    private Button noBtn, yesBtn;
    private TextInputEditText title, content;
    private String sTitle, sContent;

    public BillboardAddDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_billboard);

        title = (TextInputEditText) findViewById(R.id.dialogTitle);
        content = (TextInputEditText) findViewById(R.id.dialogContent);
        noBtn = (Button) findViewById(R.id.noBtn);
        yesBtn = (Button) findViewById(R.id.okBtn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(null);
                setsContent(null);
                dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTitle = title.getText().toString();
                sContent = content.getText().toString();

                setsTitle(sTitle);
                setsContent(sContent);

                dismiss();
            }
        });
    }


    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }
}
