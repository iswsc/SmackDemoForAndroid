package com.iswsc.smackdemo.app;

import android.app.Application;
import android.content.Context;

import com.iswsc.smackdemo.vo.ContactVo;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/8/3 1:13.
 */

public class MyApp extends Application {
    public static Context mContext = null;
    public static ContactVo mContactVo;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
