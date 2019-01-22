package com.iswsc.smackdemo.ui.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.ChatAdapter;
import com.iswsc.smackdemo.adapter.listener.IViewItem;
import com.iswsc.smackdemo.adapter.listener.impl.ChatErrorViewItemImpl;
import com.iswsc.smackdemo.adapter.listener.impl.ChatTextLeftViewItemImpl;
import com.iswsc.smackdemo.adapter.listener.impl.ChatTextRightViewItemImpl;
import com.iswsc.smackdemo.db.ChatMessageDataBase;
import com.iswsc.smackdemo.enums.ChatType;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;

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
        setTitle(chatJid);
        mChatBroadcastReceiver = new ChatBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_MESSAGE + "_" + chatJid);
        JacenUtils.registerLocalBroadcastReceiver(this, mChatBroadcastReceiver, intentFilter);
        ChatMessageDataBase.getInstance().clearUnReadByJid(chatJid);
        List<ChatMessageVo> mList = ChatMessageDataBase.getInstance().getChatMessageListByChatJid(chatJid);


        SparseArray<IViewItem> sparseArray = new SparseArray();
        sparseArray.put(ChatType.text.getLeft(),new ChatTextLeftViewItemImpl());
        sparseArray.put(ChatType.text.getRight(),new ChatTextRightViewItemImpl());
        sparseArray.put(ChatType.error.getId(),new ChatErrorViewItemImpl());
        mAdapter = new ChatAdapter(this, mList,sparseArray, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);

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

//            new Thread(){
//                @Override
//                public void run() {
//                    long a = System.currentTimeMillis();
//                    StringBuffer sb = new StringBuffer();
//                    for (int i = 0; i < 500; i++) {
//                       XmppUtils.getInstance().sendMessage(chatJid, sb.append(i).toString());
//                    }
//                    Log.i("wsc","time = " + (System.currentTimeMillis() - a));
//                }
//            }.start();
                ChatMessageVo vo = XmppUtils.getInstance().sendMessage(chatJid, content);
                if (vo != null) {
                    mConetnt.setText("");
                    mAdapter.addData(vo,mAdapter.getItemCount());
//                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
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
            mAdapter.addData(vo,mAdapter.getItemCount());
            mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        }
    }
}
