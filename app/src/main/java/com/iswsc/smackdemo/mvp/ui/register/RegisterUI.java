package com.iswsc.smackdemo.mvp.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.mvp.ui.setting.SettingUI;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenDialogUtils;
import com.iswsc.smackdemo.util.JacenUtils;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/30 23:24.
 */

public class RegisterUI extends BaseActivity implements IRegisterContract.View {

    private IRegisterContract.Presenter mPresenter;

    private TextView mAccount;
    private TextView mPassword;
    private TextView mRegister;
    private ImageView mSetting;

    private ProgressDialog mDialog;


    @Override
    public void onActivityListener(Bundle bundle) {

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_register);
        mPresenter = new RegisterPresenter(this);
        mPresenter.init();
    }

    @Override
    protected void findViewById() {
        mAccount = (TextView) findViewById(R.id.account);
        mPassword = (TextView) findViewById(R.id.password);
        mRegister = (TextView) findViewById(R.id.register);
        mSetting = (ImageView) findViewById(R.id.setting);
        setTitle(R.string.register);
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mRegister, mSetting);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register:
                mPresenter.toRegister(mAccount.getText().toString().trim(), mPassword.getText().toString().trim());
                break;
            case R.id.setting:
                mPresenter.toSetting();
                break;
        }
    }

    @Override
    public void showLoading() {
        mDialog = JacenDialogUtils.showDialog("登录中...", this);
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    @Override
    public void registerError(String message) {
        showToast(message);
    }

    @Override
    public void toSetting() {
        JacenUtils.intentUI(this, SettingUI.class, null, false);
    }

    @Override
    public void toFinish() {
        finish();
    }

}
