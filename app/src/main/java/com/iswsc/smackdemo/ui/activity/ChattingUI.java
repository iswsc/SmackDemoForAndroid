package com.iswsc.smackdemo.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ContactVo;

/**
 * Created by Jacen on 2017/10/19 18:02.
 * jacen@iswsc.com
 */

public class ChattingUI extends BaseActivity {

    private TextView mSendBtn;
    private EditText mConetnt;

    private ContactVo mContactVo;


    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_chatting);
    }

    @Override
    protected void findViewById() {
        mConetnt = (EditText) findViewById(R.id.content);
        mSendBtn = (TextView) findViewById(R.id.send);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mSendBtn);
        mConetnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mSendBtn.setEnabled(false);
                } else {
                    mSendBtn.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mContactVo = (ContactVo) getIntent().getSerializableExtra("vo");
        setTitle(mContactVo.getShowName());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send://发送信息
                String content = mConetnt.getText().toString().trim();

                break;
        }
    }
}
