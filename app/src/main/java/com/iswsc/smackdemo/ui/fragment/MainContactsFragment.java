package com.iswsc.smackdemo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.MainContactsAdapter;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Jacen on 2017/8/22 13:39.
 * jacen@iswsc.com
 */

public class MainContactsFragment extends BaseFragment implements OnItemClickListener {

    private RecyclerView mRecyclerView;
    private List<ContactVo> mContactList;
    private MainContactsAdapter mAdapter;

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
        setTitle("好友");
        setBackViewGone();
        mContactList = XmppUtils.getInstance().getContactList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainContactsAdapter(getActivity(),mContactList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
