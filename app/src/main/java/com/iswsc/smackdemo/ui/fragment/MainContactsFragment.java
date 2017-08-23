package com.iswsc.smackdemo.ui.fragment;

import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Jacen on 2017/8/22 13:39.
 * jacen@iswsc.com
 */

public class MainContactsFragment extends BaseFragment {

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
        setTitle("好友");
        setBackViewGone();

    }

    @Override
    public void onClick(View v) {

    }
}
