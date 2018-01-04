package com.iswsc.smackdemo.mvp.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.iswsc.smackdemo.app.MyApp;
import com.iswsc.smackdemo.service.XmppService;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppAction;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2018/1/1 22:15.
 */

public class LoginPresenter implements ILoginContract.Presenter {

    private ILoginContract.View mLoginView;

    private LoginBroadcastReceiver mLoginBroadcastReceiver;
    private IntentFilter mIntentFilter;

    public LoginPresenter(ILoginContract.View mLoginView) {
        this.mLoginView = mLoginView;
    }

    @Override
    public void init() {
        mLoginBroadcastReceiver = new LoginBroadcastReceiver(mLoginView);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(XmppAction.ACTION_LOGIN_SUCCESS);
        mIntentFilter.addAction(XmppAction.ACTION_LOGIN_ERROR);
        mIntentFilter.addAction(XmppAction.ACTION_LOGIN_ERROR_CONFLICT);
        mIntentFilter.addAction(XmppAction.ACTION_LOGIN_ERROR_NOT_AUTHORIZED);
        mIntentFilter.addAction(XmppAction.ACTION_LOGIN_ERROR_UNKNOWNHOST);
        mIntentFilter.addAction(XmppAction.ACTION_SERVICE_ERROR);
        JacenUtils.registerLocalBroadcastReceiver(MyApp.mContext, mLoginBroadcastReceiver, mIntentFilter);
    }

    @Override
    public void destroy() {
        JacenUtils.unRegisterLocalBroadcastReceiver(MyApp.mContext, mLoginBroadcastReceiver);
        mLoginView = null;
    }

    @Override
    public void toLogin(String account, String password) {
        if (checkInfo(account, password)) {
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("account", account);
                jObj.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MySP.write(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_USERINFO, jObj.toString());

            Bundle bundle = new Bundle();
            bundle.putString("account", account);
            bundle.putString("password", password);
            JacenUtils.intentService(MyApp.mContext, XmppService.class, XmppAction.ACTION_LOGIN, bundle);
            mLoginView.showLoading();
        }
    }

    @Override
    public void toLoginSuccess() {
        mLoginView.toMainUI();
    }

    @Override
    public void initAccount() {
        String userInfo = MySP.readString(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_USERINFO);
        if (!TextUtils.isEmpty(userInfo)) {
            try {
                JSONObject jObj = new JSONObject(userInfo);
                mLoginView.initAccount(jObj.optString("account"),jObj.optString("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkInfo(String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            mLoginView.loginError("请输入账号密码");
            return false;
        }
        String serverInfo = MySP.readString(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (TextUtils.isEmpty(serverInfo)) {
            mLoginView.toSetting();
            return false;
        }
        return true;
    }

    @Override
    public void toRegister() {
        mLoginView.toRegister();
    }

    @Override
    public void toSetting() {
        mLoginView.toSetting();
    }

    class LoginBroadcastReceiver extends BroadcastReceiver {
        private ILoginContract.View mLoginView;

        public LoginBroadcastReceiver(ILoginContract.View mLoginView) {
            this.mLoginView = mLoginView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                mLoginView.hideLoading();
                if (XmppAction.ACTION_LOGIN_SUCCESS.equals(action)) {
                    mLoginView.toMainUI();
                } else if (XmppAction.ACTION_LOGIN_ERROR_NOT_AUTHORIZED.equals(action)) {
                    mLoginView.loginError("账号或密码错误");
                } else if (XmppAction.ACTION_LOGIN_ERROR_CONFLICT.equals(action)) {
                    mLoginView.loginError("账号已登录，无法重复登录");
                } else if (XmppAction.ACTION_LOGIN_ERROR_UNKNOWNHOST.equals(action)) {
                    mLoginView.loginError("无法连接到服务器: 不可达的主机名或地址");
                } else if (XmppAction.ACTION_LOGIN_ERROR.equals(action)) {
                    mLoginView.loginError("登录失败");
                } else if (XmppAction.ACTION_SERVICE_ERROR.equals(action)) {
                    mLoginView.loginError("登录失败");
                }
            }
        }
    }

}
