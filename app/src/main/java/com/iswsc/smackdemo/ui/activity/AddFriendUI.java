package com.iswsc.smackdemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;

import java.io.IOException;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/28 13:39.
 */

public class AddFriendUI extends BaseActivity {


    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_add_friend);

    }

    @Override
    protected void findViewById() {
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this,findViewById(R.id.cancel));
    }

    @Override
    protected void initData() {
        setTitle(R.string.add_friend);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mSearchContent.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
        }
    }
}
