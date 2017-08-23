package com.iswsc.smackdemo.ui.fragment;

import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseFragment;

/**
 * Created by Jacen on 2017/8/22 13:37.
 * jacen@iswsc.com
 */

public class MainMessageFragment extends BaseFragment {

    @Override
    protected void setContentView() {
        setContentView(R.layout.include_recyclerview);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        setTitle("消息");
        setBackViewGone();
    }

    @Override
    public void onClick(View v) {

    }
}
