package com.iswsc.smackdemo.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MyToast;

/**
 * Created by Jacen on 2017/8/14 13:13.
 * jacen@iswsc.com
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public final String TAG = getClass().getSimpleName();
    private int contentViewId;
    private View contentView;
    private ImageView mBackView;
    private TextView mTitleName;
    private TextView mRightText;
    private ImageView mRightAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLogI("onCreate");
        setContentView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showLogI("onCreateView");
        if (contentView != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
            return contentView;
        }
        contentView = inflater.inflate(contentViewId, container, false);
        mBackView = (ImageView) findViewById(R.id.app_back);
        mTitleName = (TextView) findViewById(R.id.app_title);
        mRightText = (TextView) findViewById(R.id.app_right);
        mRightAdd = (ImageView) findViewById(R.id.app_add);
        JacenUtils.setViewOnClickListener(this, mBackView, mRightText,mRightAdd);

        initView();
        setListener();
        initData();

        return contentView;
    }

    public void setContentView(int contentViewId) {
        this.contentViewId = contentViewId;
    }

    public View findViewById(int resId) {
        if (contentView != null)
            return contentView.findViewById(resId);
        return null;
    }

    public void showAddView(){
        if (mRightAdd != null){
            mRightAdd.setVisibility(View.VISIBLE);
        }
    }

    public void setBackViewGone() {
        if (mBackView != null) {
            mBackView.setVisibility(View.GONE);
        }
    }

    public void setRightText(String text) {
        if (mRightText != null)
            mRightText.setText(text);
    }

    public void setRightText(int ids) {
        if (mRightText != null)
            mRightText.setText(ids);
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

    protected abstract void setContentView();

    protected abstract void initView();

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
        MyToast.showToastShort(getActivity(), msg);
    }


}
