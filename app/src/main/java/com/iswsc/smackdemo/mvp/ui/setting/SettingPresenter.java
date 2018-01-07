package com.iswsc.smackdemo.mvp.ui.setting;

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

public class SettingPresenter implements ISettingContract.Presenter {

    private ISettingContract.View mSettingView;



    public SettingPresenter(ISettingContract.View mSettingView) {
        this.mSettingView = mSettingView;
    }

    @Override
    public void init() {
        String serverInfo = MySP.readString(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (!TextUtils.isEmpty(serverInfo)) {
            try {
                JSONObject jObj = new JSONObject(serverInfo);
                mSettingView.showSetting(jObj.optString("host"),jObj.optString("port"),jObj.optString("serviceName"),jObj.optString("resource"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        mSettingView = null;
    }


    @Override
    public void toSaveSetting(String host, String port, String serviceName, String resource) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("host", host);
            jObj.put("port", port);
            jObj.put("serviceName", serviceName);
            jObj.put("resource", resource);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MySP.write(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_SERVER, jObj.toString());
    }
}
