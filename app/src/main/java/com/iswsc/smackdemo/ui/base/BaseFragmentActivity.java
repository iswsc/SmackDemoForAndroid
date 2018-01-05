package com.iswsc.smackdemo.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.listener.ActivityListener;
import com.iswsc.smackdemo.ui.activity.AddFriendUI;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Jacen on 2017/8/14 13:12.
 * jacen@iswsc.com
 */

public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener {

    protected final String TAG = getClass().getSimpleName();

    private Map<String, BaseFragment> map = new HashMap<String, BaseFragment>();
    private Stack<String> mStack = new Stack<String>();


    private ImageView mBackView;
    private TextView mTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        init(savedInstanceState);
        showLogV("onCreate");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setTitle(String title) {
        if (mTitleName != null) {
            mTitleName.setText(title);
        }
    }

    public void setTitle(int resId) {
        if (mTitleName != null) {
            mTitleName.setText(resId);
        }
    }

    public void setTitleColor(int resId) {
        if (mTitleName != null) {
            mTitleName.setTextColor(getResources().getColor(resId));
        }
    }

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.app_back);
        mTitleName = (TextView) findViewById(R.id.app_title);
        JacenUtils.setViewOnClickListener(this, mBackView);
    }

    public void setBackViewGone() {
        if (mBackView != null) {
            mBackView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        showLogV("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        showLogV("onResume");
        super.onResume();

    }

    @Override
    protected void onPause() {
        showLogV("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        showLogV("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        showLogV("onDestroy");
        super.onDestroy();
    }

    private void init(Bundle savedInstanceState) {
        setContentView(savedInstanceState);
        initView();
        findViewById();
        setListener();
        initData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_back:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.act_close_enter, R.anim.act_close_exit);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.act_open_enter, R.anim.act_open_exit);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.act_open_enter, R.anim.act_open_exit);
    }

    protected abstract void setContentView(Bundle savedInstanceState);

    protected abstract void findViewById();

    protected abstract void setListener();

    protected abstract void initData();

    protected void showLogV(String msg) {
        Log.v(TAG, msg);
    }

    protected void showLogD(String msg) {
        Log.d(TAG, msg);
    }

    protected void showLogI(String msg) {
        Log.i(TAG, msg);
    }

    protected void showLogW(String msg) {
        Log.w(TAG, msg);
    }

    protected void showLogE(String msg) {
        Log.e(TAG, msg);
    }

    protected void showToast(String msg) {
        MyToast.showToastShort(this, msg);
    }

    protected void showFragment(Class<? extends BaseFragment> c) {
        if (c == null) {
            return;
        }
        showFragment(c, "");
    }

    /**
     * 显示新的Fragment
     *
     * @param c
     * @param type
     */
    private void showFragment(Class<? extends BaseFragment> c, String type) {
        try {
            BaseFragment fragment;
            // 如果mStack大于0，则隐藏
            if (mStack.size() > 0) {
                getSupportFragmentManager().beginTransaction()
                        .hide(map.get(mStack.pop())).commitAllowingStateLoss();
            }
            mStack.add(c.getSimpleName());
            // 如果已经存在，则显示之前的，如果不存在，则创建新的
            if (map.containsKey(c.getSimpleName())) {
                fragment = map.get(c.getSimpleName());
//                if (!TextUtils.isEmpty(type)) {
//                    Bundle bundle = new Bundle();
////                    fragment.setBundle(bundle);
//                    fragment.setArguments(bundle);
//                }
                getSupportFragmentManager().beginTransaction().show(fragment)
                        .commitAllowingStateLoss();
            } else {
                fragment = c.newInstance();
                map.put(c.getSimpleName(), fragment);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_frame, fragment, c.getSimpleName())
                        .commitAllowingStateLoss();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
