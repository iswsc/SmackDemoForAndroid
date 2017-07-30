package com.iswsc.smackdemo.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.XmppService;
import com.iswsc.smackdemo.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenDialogUtils;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/30 23:24.
 */

public class RegisterUI extends BaseActivity {

    private TextView mAccount;
    private TextView mPassword;
    private TextView mPassword2;
    private TextView mRegister;
    private TextView mSetting;

    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_login);
    }

    @Override
    protected void findViewById() {
        mAccount = (TextView) findViewById(R.id.account);
        mPassword = (TextView) findViewById(R.id.password);
        mPassword2 = (TextView) findViewById(R.id.password2);
        mRegister = (TextView) findViewById(R.id.register);
        mSetting = (TextView) findViewById(R.id.setting);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mRegister, mSetting);
    }

    @Override
    protected void initData() {
        setTitle("注册");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register:
                String account = mAccount.getText().toString().trim();
                String password = mAccount.getText().toString().trim();
                String password2 = mAccount.getText().toString().trim();
                if (checkInfo(account, password, password2)) {
                    String serverInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
                    if (!TextUtils.isEmpty(serverInfo)) {
                        try {
                            JSONObject jObj = new JSONObject(serverInfo);
                            String host = jObj.optString("host");
                            String port = jObj.optString("port");
                            String serverName = jObj.optString("serverName");
                            String resource = jObj.optString("resource");
                            new RegisterThread(host, port, serverName, resource, account, password).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.setting:
                JacenUtils.intentUI(this, SettingUI.class, null, false);
                break;
        }
    }

    class RegisterThread extends Thread{

        private String host;
        private String port;
        private String serverName;
        private String resource;
        private String account;
        private String password;

        public RegisterThread(String host, String port, String serverName, String resource, String account, String password) {
            this.host = host;
            this.port = port;
            this.serverName = serverName;
            this.resource = resource;
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                XmppUtils.getInstance().init(host, Integer.parseInt(port), serverName, resource);
                XMPPConnection connection = XmppUtils.getInstance().createConnection(null,null);
                AccountManager accountManager = AccountManager.getInstance(connection);
                accountManager.createAccount(account,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkInfo(String account, String password, String password2) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            showToast("请输入账号密码");
            return false;
        }
        if(!TextUtils.equals(password,password2)){
            showToast("二次密码不一致");
            return false;
        }
        String serverInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (TextUtils.isEmpty(serverInfo)) {
            JacenUtils.intentUI(this, SettingUI.class, null, false);
            return false;
        }
        return true;
    }
}
