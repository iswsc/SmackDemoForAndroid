package com.iswsc.smackdemo.mvp.ui.main.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.MainContactsAdapter;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.ui.activity.AddFriendUI;
import com.iswsc.smackdemo.ui.activity.ChattingUI;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ContactVo;

import java.util.ArrayList;

/**
 * Created by Jacen on 2017/8/22 13:39.
 * jacen@iswsc.com
 */

public class MainContactsFragment extends BaseFragment implements OnItemClickListener, IContactsContract.View {

    private IContactsContract.Presenter mPresenter;

    private LinearLayout mSearchLinear;
    private RecyclerView mRecyclerView;
    private MainContactsAdapter mAdapter;

    @Override
    protected void setContentView() {
        setContentView(R.layout.ui_main_contacts);
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mSearchLinear = (LinearLayout) findViewById(R.id.search_linear);
        mPresenter = new MainContactsPresenter(this);
        mPresenter.init();
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mSearchLinear);
    }

    @Override
    protected void initData() {
        setTitle(R.string.friends);
        setBackViewGone();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainContactsAdapter(getActivity(), null, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_linear:
                JacenUtils.intentUI(getActivity(), AddFriendUI.class, null, false);
                getActivity().overridePendingTransition(R.anim.act_open_enter_bottom_top, R.anim.act_motionless);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.toChattingUI(mAdapter.getItem(position));
    }

    @Override
    public void toUpdateList(ArrayList<ContactVo> mContactList) {
        mAdapter.updateList(mContactList);
    }

    @Override
    public void toChattingUI(Bundle bundle) {
        JacenUtils.intentUI(getActivity(), ChattingUI.class, bundle, false);
    }
}
