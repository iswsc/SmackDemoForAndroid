package com.iswsc.smackdemo.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.listener.ActivityListener;
import com.iswsc.smackdemo.mvp.base.BaseView;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  * base
 * @author Administrator
 *
 */
public abstract class BaseActivity extends Activity implements ActivityListener ,OnClickListener {

    public static List<Activity> activitys = new ArrayList<Activity>();
    protected final String TAG = getClass().getSimpleName();

    public static Map<Class, ActivityListener> mListeners = new HashMap<Class, ActivityListener>();

    private ImageView mBackView;
    private TextView mTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activitys.add(this);

        setActivityListener(getClass(), this);
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

    public void setBackViewGone(){
        if(mBackView != null){
            mBackView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        showLogV("onStart");
        super.onStart();
    }

    private static void setActivityListener(Class clazz, ActivityListener listener) {
        if (clazz == null) return;
        mListeners.put(clazz, listener);
    }

    private static void removeActivityListener(Class clazz) {
        if (clazz == null) return;
        mListeners.remove(clazz);
    }

    public static void notifActivityListener(Class clazz, Bundle bundle) {
        if (clazz != null && mListeners.get(clazz) != null) {
            mListeners.get(clazz).onActivityListener(bundle);
            return;
        }
        for (Entry<Class, ActivityListener> s : mListeners.entrySet()) {
            if (s.getValue() != null) {
                s.getValue().onActivityListener(bundle);
            }
        }
    }

    public static void clearActivityListener() {
        mListeners.clear();
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
        removeActivityListener(getClass());
        activitys.remove(this);
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

    protected abstract void setContentView(Bundle savedInstanceState);

    protected abstract void findViewById();

    protected abstract void setListener();

    protected void initData(){

    }

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

    public static void exit() {

        if (activitys != null && !activitys.isEmpty()) {
            for (Activity act : activitys) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            }
            activitys.clear();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (BaseActivity.this.getCurrentFocus() != null) {
                if (BaseActivity.this.getCurrentFocus().getWindowToken() != null) {
                    return true;
                }
            }
        }
        return false;
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
}
