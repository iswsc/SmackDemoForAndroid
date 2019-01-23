package com.iswsc.smackdemo.mvp.ui.main.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.MainMessageAdapter;
import com.iswsc.smackdemo.db.ChatMessageDataBase;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.ui.activity.ChattingUI;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;

import java.util.ArrayList;

/**
 * Created by Jacen on 2017/8/22 13:37.
 * jacen@iswsc.com
 */

public class MainMessageFragment extends BaseFragment implements OnItemClickListener ,IMessageContract.View {

    private IMessageContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private ArrayList<ChatMessageVo> mChatList;
    private MainMessageAdapter mAdapter;

    @Override
    protected void setContentView() {
        setContentView(R.layout.include_recyclerview);
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mPresenter = new MainMessagePresenter(this);
        mPresenter.init();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        setTitle(R.string.message);
        setBackViewGone();

        mChatList = ChatMessageDataBase.getInstance().getChatMessageListEvent();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainMessageAdapter(getActivity(), mChatList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.toChattingUI(mAdapter.getItem(position),position);
    }

    @Override
    public void toUpdateMessage(ChatMessageVo vo) {
        mAdapter.updateChatMessage(vo);
    }

    @Override
    public void toChattingUI(Bundle bundle,int position) {
        JacenUtils.intentUI(getActivity(),ChattingUI.class,bundle,false);
        mAdapter.updateUnRead(position);
    }
}
