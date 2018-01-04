package com.iswsc.smackdemo.mvp.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.mvp.ui.register.RegisterUI;
import com.iswsc.smackdemo.ui.activity.MainUI;
import com.iswsc.smackdemo.ui.activity.SettingUI;
import com.iswsc.smackdemo.ui.base.BaseActivity;
import com.iswsc.smackdemo.util.JacenDialogUtils;
import com.iswsc.smackdemo.util.JacenUtils;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2018/1/1 17:11.
 */

public class LoginUI extends BaseActivity implements ILoginContract.View {

    private ILoginContract.Presenter mPresenter;

    private EditText mAccount;

    private EditText mPassword;

    private TextView mLogin;

    private TextView mRegister;

    private ImageView mSetting;

    private ProgressDialog mDialog;

    @Override
    public void onActivityListener(Bundle bundle) {

    }


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.ui_login);
        mPresenter = new LoginPresenter(this);
        mPresenter.init();
    }

    @Override
    protected void findViewById() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (TextView) findViewById(R.id.login);
        mRegister = (TextView) findViewById(R.id.register);
        mSetting = (ImageView) findViewById(R.id.setting);
        setTitle(R.string.login);
        setBackViewGone();
    }

    @Override
    protected void setListener() {
        JacenUtils.setViewOnClickListener(this, mLogin, mRegister, mSetting);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login:
                String account = mAccount.getText().toString().trim();
                String password = mPassword.getText().toString();
                mPresenter.toLogin(account,password);
                break;
            case R.id.register:
                mPresenter.toRegister();
                break;
            case R.id.setting:
                mPresenter.toSetting();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.initAccount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        mAccount.setEnabled(false);
        mPassword.setEnabled(false);
    }

    @Override
    public void initAccount(String account, String password) {
        mAccount.setText(account);
        mPassword.setText(password);
    }

    @Override
    public void showLoading() {
        mDialog =  JacenDialogUtils.showDialog("登录中...",this);
    }

    @Override
    public void hideLoading() {
        if(mDialog != null){
            mDialog.dismiss();
        }
        mDialog = null;
    }

    @Override
    public void loginError(String message) {
        showToast(message);
    }

    @Override
    public void toRegister() {
        JacenUtils.intentUI(this, RegisterUI.class, null, false);
    }

    @Override
    public void toSetting() {
        JacenUtils.intentUI(this, SettingUI.class, null, false);
    }

    @Override
    public void toMainUI() {
        JacenUtils.intentUI(this, MainUI.class, null, true);
    }

}
