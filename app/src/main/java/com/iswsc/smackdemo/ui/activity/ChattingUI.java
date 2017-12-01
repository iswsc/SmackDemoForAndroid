package com.iswsc.smackdemo.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.ChatAdapter;
import com.iswsc.smackdemo.adapter.MainMessageAdapter;
import com.iswsc.smackdemo.db.ChatMessageDataBase;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;
import com.iswsc.smackdemo.vo.ContactVo;

import java.util.List;

/**
 * Created by Jacen on 2017/10/19 18:02.
 * jacen@iswsc.com
 */

public class ChattingUI extends BaseActivity implements OnItemClickListener {

    private TextView mSendBtn;
    private EditText mConetnt;

    //    private ContactVo mContactVo;
    private String chatJid;
    private String nickName;

    private ChatAdapter mAdapter;
    private RecyclerView mRecyclerView;

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
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

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
        nickName = getIntent().getStringExtra("nickName");
        chatJid = getIntent().getStringExtra("chatJid");
        setTitle(nickName);
        mChatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE + "_" + chatJid);
        JacenUtils.registerLocalBroadcastReceiver(this, mChatBroadcastReceiver, intentFilter);
        ChatMessageDataBase.getInstance().clearUnReadByJid(chatJid);
        List<ChatMessageVo> mList = ChatMessageDataBase.getInstance().getChatMessageListByChatJid(chatJid);

        mAdapter = new ChatAdapter(this, mList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JacenUtils.unRegisterLocalBroadcastReceiver(this, mChatBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send://发送信息
                String content = mConetnt.getText().toString().trim();
                ChatMessageVo vo = XmppUtils.getInstance().sendMessage(chatJid, content);
                if (vo != null) {
                    mConetnt.setText("");
                    mAdapter.addChatMessage(vo);
                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                    ChatMessageDataBase.getInstance().saveChatMessage(vo);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    class ChatBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessageVo vo = (ChatMessageVo) intent.getSerializableExtra("chat");
            showToast(vo.getContent());
        }
    }
}
