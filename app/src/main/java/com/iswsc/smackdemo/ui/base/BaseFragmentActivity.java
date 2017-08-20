package com.iswsc.smackdemo.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.iswsc.smackdemo.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Jacen on 2017/8/14 13:12.
 * jacen@iswsc.com
 */

public class BaseFragmentActivity extends FragmentActivity {

    private Map<String, BaseFragment> map = new HashMap<String, BaseFragment>();
    private Stack<String> mStack = new Stack<String>();

    private void showFragment(Class<? extends BaseFragment> c) {
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
