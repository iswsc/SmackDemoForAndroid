package com.iswsc.smackdemo.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.MainContactsAdapter;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/28 13:39.
 */

public class AddFriendUI extends BaseActivity implements OnItemClickListener {

    private EditText mSearchContent;
    private RecyclerView mRecyclerView;
    private MainContactsAdapter mAdapter;
    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_add_friend);
    }

    @Override
    protected void findViewById() {
        mSearchContent = (EditText) findViewById(R.id.search_content);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, findViewById(R.id.cancel));
        mSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String content = mSearchContent.getText().toString().trim();
                    if(!TextUtils.isEmpty(content)){
                        ArrayList<ContactVo> contactVoList = XmppUtils.getInstance().searchUser(content);
                        mAdapter.updateList(contactVoList);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        setTitle(R.string.add_friend);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainContactsAdapter(this,null,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mSearchContent.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        String jid = mAdapter.getItem(position).getJid();
        Presence presence2 = new Presence(Presence.Type.subscribe);
        presence2.setTo(jid);
        try {
            XmppUtils.getInstance().getConnection().sendStanza(presence2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        finish();
    }
}
