package com.iswsc.smackdemo.mvp.ui.main.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.iswsc.smackdemo.app.MyApp;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.xmpp.XmppAction;
import com.iswsc.smackdemo.vo.ChatMessageVo;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2018/1/8 0:37.
 */

public class MainMessagePresenter implements IMessageContract.Presenter {

    private IMessageContract.View mMessageView;

    private MessageBroadcastReceiver mMessageBroadcastReceiver;

    public MainMessagePresenter(IMessageContract.View mContactsView) {
        this.mMessageView = mContactsView;
    }

    @Override
    public void init() {
        mMessageBroadcastReceiver = new MessageBroadcastReceiver(mMessageView);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE);
        JacenUtils.registerLocalBroadcastReceiver(MyApp.mContext, mMessageBroadcastReceiver, intentFilter);

    }

    @Override
    public void destroy() {
        JacenUtils.unRegisterLocalBroadcastReceiver(MyApp.mContext, mMessageBroadcastReceiver);
        mMessageView = null;
    }

    @Override
    public void toChattingUI(ChatMessageVo msg,int position) {
        Bundle bundle = new Bundle();
        bundle.putString("nickName",msg.getChatJid());
        bundle.putString("chatJid",msg.getChatJid());
        mMessageView.toChattingUI(bundle,position);
    }


    class MessageBroadcastReceiver extends BroadcastReceiver {

        private IMessageContract.View mMessageView;

        public MessageBroadcastReceiver(IMessageContract.View mMessageView) {
            this.mMessageView = mMessageView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mMessageView.toUpdateMessage((ChatMessageVo) intent.getSerializableExtra("chat"));
            ;
        }
    }
}
