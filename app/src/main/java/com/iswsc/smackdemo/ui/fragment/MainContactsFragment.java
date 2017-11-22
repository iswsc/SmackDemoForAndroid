package com.iswsc.smackdemo.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.MainContactsAdapter;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.service.XmppService;
import com.iswsc.smackdemo.ui.activity.ChattingUI;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jacen on 2017/8/22 13:39.
 * jacen@iswsc.com
 */

public class MainContactsFragment extends BaseFragment implements OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ArrayList<ContactVo> mContactList;
    private MainContactsAdapter mAdapter;

    private MyContactBroadcastReveiver myContactBroadcastReveiver;

    @Override

    protected void setContentView() {
        setContentView(R.layout.include_recyclerview);
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        setTitle(R.string.friends);
        setRightText(R.string.add);
        setBackViewGone();
//        mContactList = XmppUtils.getInstance().getContactList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainContactsAdapter(getActivity(), mContactList, this);
        mRecyclerView.setAdapter(mAdapter);
        registerContactBroadcastReceiver();
        JacenUtils.intentService(getActivity(), XmppService.class,XmppAction.ACTION_USER_CONTACT,null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JacenUtils.unRegisterLocalBroadcastReceiver(getActivity(), myContactBroadcastReveiver);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {
        ContactVo vo = mContactList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vo", vo);
        JacenUtils.intentUI(getActivity(), ChattingUI.class, bundle, false);
    }


    private void registerContactBroadcastReceiver() {
        myContactBroadcastReveiver = new MyContactBroadcastReveiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_USER_CONTACT);
        JacenUtils.registerLocalBroadcastReceiver(getActivity(), myContactBroadcastReveiver, intentFilter);
    }

    class MyContactBroadcastReveiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mContactList = (ArrayList<ContactVo>) intent.getSerializableExtra("contactVoList");
            mAdapter.updateList(mContactList);
        }
    }
}
