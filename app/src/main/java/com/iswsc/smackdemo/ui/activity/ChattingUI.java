package com.iswsc.smackdemo.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.icu.util.JapaneseCalendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smackx.filetransfer.IBBTransferNegotiator;

/**
 * Created by Jacen on 2017/10/19 18:02.
 * jacen@iswsc.com
 */

public class ChattingUI extends BaseActivity {

    private TextView mSendBtn;
    private EditText mConetnt;

    private ContactVo mContactVo;

    private ChatBroadcastReceiver mChatBroadcastReceiver;

    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_chatting);
    }

    @Override
    protected void findViewById() {
        mConetnt = (EditText) findViewById(R.id.content);
        mSendBtn = (TextView) findViewById(R.id.send);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mSendBtn);
        mConetnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mSendBtn.setEnabled(false);
                } else {
                    mSendBtn.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mContactVo = (ContactVo) getIntent().getSerializableExtra("vo");
        setTitle(mContactVo.getShowName());
        mChatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE + "_" + mContactVo.getFullJid());
        JacenUtils.registerLocalBroadcastReceiver(this, mChatBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JacenUtils.unRegisterLocalBroadcastReceiver(this,mChatBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send://发送信息
                String content = mConetnt.getText().toString().trim();
                boolean result = XmppUtils.getInstance().sendMessage(mContactVo.getFullJid(),content);
                if(result){
                    mConetnt.setText("");
                }
                break;
        }
    }

    class ChatBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessageVo vo = (ChatMessageVo) intent.getSerializableExtra("chat");
            showToast(vo.getContent());
        }
    }
}
