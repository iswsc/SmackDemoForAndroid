package com.iswsc.smackdemo.mvp.ui.register;

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

public class RegisterPresenter implements IRegisterContract.Presenter {

    private IRegisterContract.View mRegisterView;

    private RegisterBroadcastReceiver mRegisterBroadcastReceiver;

    private IntentFilter mIntentFilter;

    public RegisterPresenter(IRegisterContract.View mRegisterView) {
        this.mRegisterView = mRegisterView;
    }

    @Override
    public void init() {
        mRegisterBroadcastReceiver = new RegisterBroadcastReceiver(mRegisterView);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_SUCCESS);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_CONFLICT);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_FORBIDDEN);
        mIntentFilter.addAction(XmppAction.ACTION_REGISTER_ERROR_JID_MALFORMED);
        mIntentFilter.addAction(XmppAction.ACTION_SERVICE_ERROR);
        JacenUtils.registerLocalBroadcastReceiver(MyApp.mContext, mRegisterBroadcastReceiver, mIntentFilter);
    }

    @Override
    public void destroy() {
        JacenUtils.unRegisterLocalBroadcastReceiver(MyApp.mContext, mRegisterBroadcastReceiver);
        mRegisterView = null;
    }


    @Override
    public void toRegister(String account, String password) {
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
            JacenUtils.intentService(MyApp.mContext, XmppService.class, XmppAction.ACTION_REGISTER, bundle);
            mRegisterView.showLoading();
        }
    }

    private boolean checkInfo(String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            mRegisterView.registerError("请输入账号密码");
            return false;
        }
        String serverInfo = MySP.readString(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (TextUtils.isEmpty(serverInfo)) {
            mRegisterView.toSetting();
            return false;
        }
        return true;
    }

    @Override
    public void toSetting() {
        mRegisterView.toSetting();
    }

    class RegisterBroadcastReceiver extends BroadcastReceiver {
        private IRegisterContract.View mRegisterView;

        public RegisterBroadcastReceiver(IRegisterContract.View mRegisterView) {
            this.mRegisterView = mRegisterView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String action = intent.getAction();
                mRegisterView.hideLoading();
                if(XmppAction.ACTION_REGISTER_SUCCESS.equals(action)){
                    mRegisterView.toFinish();
                }else if(XmppAction.ACTION_REGISTER_ERROR_FORBIDDEN.equals(action)){
                    mRegisterView.registerError("禁止注册");
                }else if(XmppAction.ACTION_REGISTER_ERROR_CONFLICT.equals(action)){
                    mRegisterView.registerError("账号已存在");
                }else if(XmppAction.ACTION_REGISTER_ERROR_JID_MALFORMED.equals(action)){
                    mRegisterView.registerError("账号格式不正确");
                }else if(XmppAction.ACTION_REGISTER_ERROR.equals(action)){
                    mRegisterView.registerError("注册失败");
                }else if(XmppAction.ACTION_SERVICE_ERROR.equals(action)){
                    mRegisterView.registerError("注册失败");
                }
            }
        }
    }

}
