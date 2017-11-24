package com.iswsc.smackdemo.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.activity.ChattingUI;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.vo.ChatMessageVo;

/**
 * Created by Jacen on 2017/8/22 13:37.
 * jacen@iswsc.com
 */

public class MainMessageFragment extends BaseFragment {

    private ChatBroadcastReceiver mChatBroadcastReceiver;

    @Override
    protected void setContentView() {
        setContentView(R.layout.include_recyclerview);
    }

    @Override
    protected void initView() {
        mChatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE);
        JacenUtils.registerLocalBroadcastReceiver(getActivity(), mChatBroadcastReceiver, intentFilter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        setTitle(R.string.message);
        setBackViewGone();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JacenUtils.unRegisterLocalBroadcastReceiver(getActivity(),mChatBroadcastReceiver);
    }

    class ChatBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessageVo vo = (ChatMessageVo) intent.getSerializableExtra("chat");
            showToast(vo.getContent());
        }
    }

}
