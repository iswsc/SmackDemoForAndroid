package com.iswsc.smackdemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.enums.MainTab;
import com.iswsc.smackdemo.ui.base.BaseFragmentActivity;
import com.iswsc.smackdemo.ui.fragment.MainContactsFragment;
import com.iswsc.smackdemo.ui.fragment.MainMessageFragment;
import com.iswsc.smackdemo.ui.fragment.MainMineFragment;
import com.iswsc.smackdemo.util.JacenUtils;

public class MainUI extends BaseFragmentActivity {

    private RelativeLayout mTabMessageRela;
    private RelativeLayout mTabContactsRela;
    private RelativeLayout mTabMeRela;
    private TextView mTabMessage;
    private TextView mTabContact;
    private TextView mTabMine;

//    private MainTab mainTab = MainTab.message;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_main);
    }

    @Override
    protected void findViewById() {
        mTabMessageRela = (RelativeLayout) findViewById(R.id.main_tab_message_rela);
        mTabContactsRela = (RelativeLayout) findViewById(R.id.main_tab_contact_rela);
        mTabMeRela = (RelativeLayout) findViewById(R.id.main_tab_mine_rela);

        mTabMessage = (TextView) findViewById(R.id.main_tab_message);
        mTabContact = (TextView) findViewById(R.id.main_tab_contact);
        mTabMine = (TextView) findViewById(R.id.main_tab_mine);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mTabMessageRela, mTabContactsRela, mTabMeRela);
    }

    @Override
    protected void initData() {
        setTab(MainTab.message);
//        setTitle(R.string.app_name);
//        setBackViewGone();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_tab_message_rela://消息
                setTab(MainTab.message);
                break;
            case R.id.main_tab_contact_rela://好友
                setTab(MainTab.contacts);
                break;
            case R.id.main_tab_mine_rela://我
                setTab(MainTab.mine);
                break;
        }
    }

    void setTab(MainTab tab) {
        if (MainTab.contacts.equals(tab)) {
            showFragment(MainContactsFragment.class);
            mTabMessage.setEnabled(true);
            mTabContact.setEnabled(false);
            mTabMine.setEnabled(true);
        } else if (MainTab.mine.equals(tab)) {
            showFragment(MainMineFragment.class);
            mTabMessage.setEnabled(true);
            mTabContact.setEnabled(true);
            mTabMine.setEnabled(false);
        } else {//MainMessage
            showFragment(MainMessageFragment.class);
            mTabMessage.setEnabled(false);
            mTabContact.setEnabled(true);
            mTabMine.setEnabled(true);
        }
    }
}
