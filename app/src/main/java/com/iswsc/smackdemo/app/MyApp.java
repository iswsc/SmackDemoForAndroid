package com.iswsc.smackdemo.app;

import android.app.Application;
import android.content.Context;

import com.iswsc.smackdemo.vo.ContactVo;
import com.squareup.leakcanary.LeakCanary;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
