package com.iswsc.smackdemo.ui.fragment;

import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.xmpp.XmppUtils;

/**
 * Created by Jacen on 2017/8/22 13:39.
 * jacen@iswsc.com
 */

public class MainMineFragment extends BaseFragment {

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
        try {
            setTitle(getString(R.string.mine) + XmppUtils.getInstance().getConnection().getUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBackViewGone();
    }

    @Override
    public void onClick(View v) {
    }
}
