package com.iswsc.smackdemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.base.BaseActivity;
import com.iswsc.smackdemo.util.MySP;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jacen on 2017/7/28 17:04.
 * jacen@iswsc.com
 */

public class SettingUI extends BaseActivity {

    private TextView mHost;
    private TextView mPort;
    private TextView mServerName;
    private TextView mResource;

    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_setting);
    }

    @Override
    protected void findViewById() {
        mHost = (TextView) findViewById(R.id.host);
        mPort = (TextView) findViewById(R.id.port);
        mServerName = (TextView) findViewById(R.id.servername);
        mResource = (TextView) findViewById(R.id.resource);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        String serverInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (!TextUtils.isEmpty(serverInfo)) {
            try {
                JSONObject jObj = new JSONObject(serverInfo);
                mHost.setText(jObj.optString("host"));
                mPort.setText(jObj.optString("port"));
                mServerName.setText(jObj.optString("serverName"));
                mResource.setText(jObj.optString("resource"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String host = mHost.getText().toString().trim();
        String port = mPort.getText().toString().trim();
        String serverName = mServerName.getText().toString().trim();
        String resource = mResource.getText().toString().trim();

        JSONObject jObj = new JSONObject();
        try {
            jObj.put("host", host);
            jObj.put("port", port);
            jObj.put("serverName", serverName);
            jObj.put("resource", resource);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MySP.write(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER, jObj.toString());
    }
}
