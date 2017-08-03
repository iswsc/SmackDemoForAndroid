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
import com.iswsc.smackdemo.base.BaseActivity;
import com.iswsc.smackdemo.service.XmppService;
import com.iswsc.smackdemo.util.JacenDialogUtils;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/30 23:24.
 */

public class RegisterUI extends BaseActivity {

    private TextView mAccount;
    private TextView mPassword;
    private TextView mRegister;
    private TextView mSetting;

    private RegisterBroadcastReceiver mRegisterBroadcastReceiver;
    private IntentFilter mIntentFilter;

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
        mRegisterBroadcastReceiver = new RegisterBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_SUCCESS);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_CONFLICT);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_NOT_AUTHORIZED);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_UNKNOWNHOST);
        mIntentFilter.addAction(XmppAction.ACTION_SERVICE_ERROR);
        JacenUtils.registerLocalBroadcastReceiver(this, mRegisterBroadcastReceiver, mIntentFilter);
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
                String password = mPassword.getText().toString().trim();
                if (checkInfo(account, password)) {
                    JSONObject jObj = new JSONObject();
                    try {
                        jObj.put("account", account);
                        jObj.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MySP.write(this, MySP.FILE_APPLICATION, MySP.KEY_USERINFO, jObj.toString());

                    Bundle bundle = new Bundle();
                    bundle.putString("account", account);
                    bundle.putString("password", password);
                    JacenUtils.intentService(this, XmppService.class, XmppAction.ACTION_REGISTER, bundle);
                    JacenDialogUtils.showDialog(this, "正在注册");
                }
                break;
            case R.id.setting:
                JacenUtils.intentUI(this, SettingUI.class, null, false);
                break;
        }
    }

    private boolean checkInfo(String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            showToast("请输入账号密码");
            return false;
        }
        String serverInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (TextUtils.isEmpty(serverInfo)) {
            JacenUtils.intentUI(this, SettingUI.class, null, false);
            return false;
        }
        return true;
    }

    class RegisterBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String action = intent.getAction();
                JacenDialogUtils.dismissDialog();
                if(XmppAction.ACTION_REGISTER_SUCCESS.equals(action)){
                    showToast("注册成功");
                    finish();
                }else if(XmppAction.ACTION_REGISTER_ERROR_NOT_AUTHORIZED.equals(action)){
//                    showToast("密码错误");
                }else if(XmppAction.ACTION_REGISTER_ERROR_CONFLICT.equals(action)){
//                    showToast("账号已登录，无法重复登录");
                }else if(XmppAction.ACTION_REGISTER_ERROR_UNKNOWNHOST.equals(action)){
//                    showToast("无法连接到服务器: 不可达的主机名或地址.");
                }else if(XmppAction.ACTION_REGISTER_ERROR.equals(action)){
//                    showToast("登录失败");
                }else if(XmppAction.ACTION_SERVICE_ERROR.equals(action)){
//                    showToast("登录失败");
                }
            }
        }
    }

}
