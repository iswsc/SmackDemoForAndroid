//package com.iswsc.smackdemo.ui.fragment;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.iswsc.smackdemo.R;
//import com.iswsc.smackdemo.adapter.MainContactsAdapter;
//import com.iswsc.smackdemo.adapter.MainMessageAdapter;
//import com.iswsc.smackdemo.db.ChatMessageDataBase;
//import com.iswsc.smackdemo.listener.OnItemClickListener;
//import com.iswsc.smackdemo.ui.activity.ChattingUI;
//import com.iswsc.smackdemo.ui.base.BaseFragment;
//import com.iswsc.smackdemo.util.JacenUtils;
//import com.iswsc.smackdemo.xmpp.XmppAction;
//import com.iswsc.smackdemo.vo.ChatMessageVo;
//import com.iswsc.smackdemo.vo.ContactVo;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Jacen on 2017/8/22 13:37.
// * jacen@iswsc.com
// */
//
//public class MainMessageFragment extends BaseFragment implements OnItemClickListener {
//
//    private ChatBroadcastReceiver mChatBroadcastReceiver;
//    private RecyclerView mRecyclerView;
//    private ArrayList<ChatMessageVo> mChatList;
//    private MainMessageAdapter mAdapter;
//
//    @Override
//    protected void setContentView() {
//        setContentView(R.layout.include_recyclerview);
//    }
//
//    @Override
//    protected void initView() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//
//    }
//
//    @Override
//    protected void setListener() {
//
//    }
//
//    @Override
//    protected void initData() {
//        setTitle(R.string.message);
//        setBackViewGone();
//
//        mChatList = ChatMessageDataBase.getInstance().getChatMessageListEvent();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new MainMessageAdapter(getActivity(), mChatList, this);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mChatBroadcastReceiver = new ChatBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(XmppAction.ACTION_MESSAGE);
//        JacenUtils.registerLocalBroadcastReceiver(getActivity(), mChatBroadcastReceiver, intentFilter);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        JacenUtils.unRegisterLocalBroadcastReceiver(getActivity(),mChatBroadcastReceiver);
//    }
//
//    @Override
//    public void onItemClick(View view, int position) {
//        Bundle bundle = new Bundle();
//        ChatMessageVo msg = mAdapter.getItem(position);
//        bundle.putString("nickName",msg.getChatJid());
//        bundle.putString("chatJid",msg.getChatJid());
//        JacenUtils.intentUI(getActivity(),ChattingUI.class,bundle,false);
//        mAdapter.updateUnRead(position);
//    }
//
//    class ChatBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ChatMessageVo vo = (ChatMessageVo) intent.getSerializableExtra("chat");
//            mAdapter.updateChatMessage(vo);
//        }
//    }
//
//}
