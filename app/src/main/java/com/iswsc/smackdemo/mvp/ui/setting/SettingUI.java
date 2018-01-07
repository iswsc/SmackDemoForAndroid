package com.iswsc.smackdemo.mvp.ui.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.MySP;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jacen on 2017/7/28 17:04.
 * jacen@iswsc.com
 */

public class SettingUI extends BaseActivity implements ISettingContract.View{

    private ISettingContract.Presenter mPresenter;


    private TextView mHost;
    private TextView mPort;
    private TextView mServiceName;
    private TextView mResource;


    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_setting);
        mPresenter = new SettingPresenter(this);
    }

    @Override
    protected void findViewById() {
        mHost = (TextView) findViewById(R.id.host);
        mPort = (TextView) findViewById(R.id.port);
        mServiceName = (TextView) findViewById(R.id.servicename);
        mResource = (TextView) findViewById(R.id.resource);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        setTitle("服务器设置");
        mPresenter.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String host = mHost.getText().toString().trim();
        String port = mPort.getText().toString().trim();
        String serviceName = mServiceName.getText().toString().trim();
        String resource = mResource.getText().toString().trim();
        mPresenter.toSaveSetting(host,port,serviceName,resource);

    }

    @Override
    public void showSetting(String host, String port, String serviceName, String resource) {
        mHost.setText(host);
        mPort.setText(port);
        mServiceName.setText(serviceName);
        mResource.setText(resource);
    }
}
