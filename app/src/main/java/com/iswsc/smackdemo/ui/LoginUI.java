package com.iswsc.smackdemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.XmppService;
import com.iswsc.smackdemo.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/25 1:40.
 */

public class LoginUI extends BaseActivity {

    private TextView mHost;
    private TextView mPort;
    private TextView mServerName;
    private TextView mResource;
    private TextView mAccount;
    private TextView mPassword;
    private TextView mLogin;
    private TextView mRegister;

    private CheckBox mRemember;

    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_login);
    }

    @Override
    protected void findViewById() {
        mHost = (TextView) findViewById(R.id.host);
        mPort = (TextView) findViewById(R.id.port);
        mServerName = (TextView) findViewById(R.id.servername);
        mResource = (TextView) findViewById(R.id.resource);
        mAccount = (TextView) findViewById(R.id.account);
        mPassword = (TextView) findViewById(R.id.password);
        mLogin = (TextView) findViewById(R.id.login);
        mRegister = (TextView) findViewById(R.id.register);
        mRemember = (CheckBox) findViewById(R.id.remember_info);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mLogin, mRegister);
    }

    @Override
    protected void initData() {
        setTitle(R.string.app_name);
        initAccount();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login:
                toLogin();
                break;
            case R.id.register:
                showToast("注册");
                break;
        }
    }

    private void initAccount() {
        String userInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_USER_INFO);
        if (!TextUtils.isEmpty(userInfo)) {
            try {
                JSONObject jObj = new JSONObject(userInfo);
                mHost.setText(jObj.optString("host"));
                mPort.setText(jObj.optString("port"));
                mServerName.setText(jObj.optString("serverName"));
                mResource.setText(jObj.optString("resource"));
                mAccount.setText(jObj.optString("account"));
                mPassword.setText(jObj.optString("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void toLogin() {
        String host = mHost.getText().toString().trim();
        String port = mPort.getText().toString().trim();
        String serverName = mServerName.getText().toString().trim();
        String resource = mResource.getText().toString().trim();
        String account = mAccount.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        boolean isRemember = mRemember.isChecked();
        if (isRemember) {
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("host", host);
                jObj.put("port", port);
                jObj.put("serverName", serverName);
                jObj.put("resource", resource);
                jObj.put("account", account);
                jObj.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MySP.write(this, MySP.FILE_APPLICATION, MySP.KEY_USER_INFO, jObj.toString());
        }
        boolean check = checkInfo(host, port, serverName, resource, account, password);
        if (check) {
            JacenUtils.intentService(this, XmppService.class,XmppUtils.ACTION_LOGIN,null);
        }
    }

    private boolean checkInfo(String host, String port, String serverName, String resource, String account, String password) {
        if (TextUtils.isEmpty(host)
                || TextUtils.isEmpty(port)
                || TextUtils.isEmpty(serverName)
                || TextUtils.isEmpty(resource)
                || TextUtils.isEmpty(account)
                || TextUtils.isEmpty(password)) {
            showToast("请输入完整的资料");
            return false;
        }
        return true;
    }
}
