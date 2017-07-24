package com.iswsc.smackdemo.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JacenUtils {

    public static String getLineString(InputStream in) {
        try {
            StringBuffer sb = new StringBuffer();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String temp;
            try {
                while ((temp = read.readLine()) != null) {
                    sb.append(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } catch (Error e) {
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static View findView(View view, int resId) {
        if (view == null) return null;
        return view.findViewById(resId);
    }

    public static View findView(Activity act, int resId) {
        if (act == null) return null;
        return act.findViewById(resId);
    }

    public static void setViewGone(View... view) {
        if (view == null) return;
        for (int i = 0; i < view.length; i++) {
            if (view[i] != null) {
                view[i].setVisibility(View.GONE);
            }
        }
    }

    public static void setViewVisible(View... view) {
        if (view == null) return;
        for (int i = 0; i < view.length; i++) {
            if (view[i] != null) {
                view[i].setVisibility(View.VISIBLE);
            }
        }
    }

    public static void setViewInvisible(View... view) {
        if (view == null) return;
        for (int i = 0; i < view.length; i++) {
            if (view[i] != null) {
                view[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void setViewOnClickListener(View.OnClickListener l, View... view) {
        if (view == null || l == null) return;
        for (int i = 0; i < view.length; i++) {
            if (view[i] != null) {
                view[i].setOnClickListener(l);
            }
        }
    }


    /**
     * 保存文件
     *
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-9-6上午11:34:06
     */
    public static void writeToFile(String saveString, String filePath, String fileName) {
        if (TextUtils.isEmpty(saveString) || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            return;
        }
        try {//保存到本地
            File jsonFile = new File(filePath + File.separator + fileName);
            File dirs = new File(jsonFile.getParent());
            dirs.mkdirs();
            jsonFile.createNewFile();
            FileOutputStream fileOutS = new FileOutputStream(jsonFile);
            byte[] buf = saveString.getBytes();
            fileOutS.write(buf);
            fileOutS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机IMEI号
     *
     * @param mContext
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime 2014年4月15日 下午5:47:05
     */
    public static String getIMEI(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 跳转页面
     *
     * @param act      Activity
     * @param clazz    需要跳转的类
     * @param bundle   需要传递的数据
     * @param isFinish 是否关闭当前页面
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-11-2下午8:27:00
     */
    public static void intentUI(Activity act, Class clazz, Bundle bundle, boolean isFinish) {
        if (act == null || clazz == null) return;
        Intent intent = new Intent(act, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        act.startActivity(intent);
        if (isFinish) {
            act.finish();
        }
    }

    /**
     * 跳转页面
     *
     * @param act      Activity
     * @param clazz    需要跳转的类
     * @param bundle   需要传递的数据
     * @param isFinish 是否关闭当前页面
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-11-2下午8:27:00
     */
    public static void intentUI(Activity act, Class clazz, int requestCode, Bundle bundle, boolean isFinish) {
        if (act == null || clazz == null) return;
        Intent intent = new Intent(act, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        act.startActivityForResult(intent, requestCode);
        if (isFinish) {
            act.finish();
        }
    }

    /**
     * 跳转页面
     *
     * @param act    Activity
     * @param clazz  需要跳转的类
     * @param bundle 需要传递的数据
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-11-2下午8:27:00
     */
    public static void intentUI(Fragment fragment, Activity act, Class clazz, int requestCode, Bundle bundle) {
        if (act == null || clazz == null) return;
        Intent intent = new Intent(act, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转页面
     *
     * @param clazz  需要跳转的类
     * @param bundle 需要传递的数据
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-11-2下午8:27:00
     */
    public static void intentUI4NewTask(Context c, Class clazz, Bundle bundle) {
        if (c == null || clazz == null) return;
        Intent intent = new Intent(c, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(intent);
    }

    /**
     * 数据加密 不能有特殊字符
     *
     * @param text
     * @return
     * @author by_wsc
     * @email wscnydx@gmail.com
     * @date 日期：2014-1-15 下午3:15:58
     */
    public static String stringEncode(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        String encode = Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encode.length(); i++) {
            char oldChar = encode.charAt(i);
            char newChar = (char) (oldChar * 2 + 1);
            sb.append(newChar);
        }
        return sb.toString();
    }

    /**
     * 数据解密 不能有特殊字符
     *
     * @param text
     * @return
     * @author by_wsc
     * @email wscnydx@gmail.com
     * @date 日期：2014-1-15 下午3:18:56
     */
    public static String stringDecode(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.toString().length(); i++) {
            char oldChar = text.toString().charAt(i);
            char newChar = (char) ((oldChar - 1) / 2);
            sb.append(newChar);
        }
        return new String(Base64.decode(sb.toString(), Base64.DEFAULT));
    }


    /**
     * 发送本地广播 接收该广播需要用V4包下的LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, intentFilter)注册
     * <br/>取值 直接getExtras()
     *
     * @param mContext
     * @param action
     * @param bundle
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-12-17下午6:30:35
     */
    public static final void intentLocalBroadcastReceiver(Context mContext, String action, Bundle bundle) {
        if (mContext == null || TextUtils.isEmpty(action)) {
            return;
        }
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    public static void intentService(Context mContext, Class serviceClass, String serviceAction, Bundle bundle) {
        Intent service = new Intent(serviceAction);
        ComponentName mComponentName = new ComponentName(mContext, serviceClass);
        service.setComponent(mComponentName);
        if (bundle != null) {//传递参数
            service.putExtras(bundle);
        }
        mContext.startService(service);
    }

    /**
     * 注册本地广播
     *
     * @param mContext
     * @param receiver
     * @param intentFilter
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-12-17下午6:34:09
     */
    public static final void registerLocalBroadcastReceiver(Context mContext, BroadcastReceiver receiver, IntentFilter intentFilter) {
        if (mContext == null) {
            return;
        }
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, intentFilter);
    }


    /**
     * 注销本地广播
     *
     * @param mContext
     * @param receiver
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2015-12-17下午6:34:09
     */
    public static final void unRegisterLocalBroadcastReceiver(Context mContext, BroadcastReceiver receiver) {
        if (mContext == null) {
            return;
        }
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);
    }

    /**
     * 十六进制转二进制
     *
     * @param hexString
     * @return
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2016-7-5下午5:52:45
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 二进制转十六进制
     *
     * @param bString
     * @return
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2016-7-5下午5:54:38
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
            return null;
        }
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * 转秒为时 00:00:00
     *
     * @param time
     * @return
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2016-9-21下午8:00:59
     */
    public static String formatSecond2Time(int time) {
        StringBuffer sb = new StringBuffer();
        int hour = 0;
        int min = 0;
        int second = 0;
        String ___ = ":";
        if (time < 60) {//秒
            sb.append("00:00:").append(unitFormat(time));
        } else if (time < 3600) {//分
            min = time / 60;
            second = time - min * 60;
            sb.append("00").append(___).append(unitFormat(min)).append(___).append(unitFormat(second));
        } else {//时
            hour = time / 3600;
            min = (time - hour * 3600) / 60;
            second = time - min * 60 - hour * 3600;
            sb.append(hour).append(___).append(min).append(___).append(second);
        }
        return sb.toString();
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 根据时间算年龄
     *
     * @param time
     * @return
     */
    public static int getAge4Time(long time) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(new Date(time));
        int curYear = calendar2.get(Calendar.YEAR);
        int seleYear = calendar1.get(Calendar.YEAR);
        int curMonth = calendar2.get(Calendar.MONTH) - 1;
        int seleMonth = calendar1.get(Calendar.MONTH) - 1;
        int curDate = calendar2.get(Calendar.DATE);
        int seleDate = calendar1.get(Calendar.DATE);
        int age = 0;
        age = curYear - seleYear;
        if (curMonth - seleMonth < 0) {//生日没过
            age--;
        } else if (curMonth - seleMonth == 0) {
            age -= (curDate - seleDate) > -1 ? 0 : 1;
        }
        return age;
    }

    /**
     * 检验电话号码
     *
     * @param phone
     * @return
     * @author Jacen
     * @email jacen@wscnydx.com
     * @datetime @2016-10-21 14:32
     */
    public static boolean checkPhone(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher mp = p.matcher(phone);
        return mp.matches();
    }

    /**
     * 高斯模糊 推荐在线程里面处理 比较耗时 如果不是一次性显示的 建议保存下来 方便下次直接读取文件
     *
     * @param bmp
     * @param hRadius    水平方向模糊度
     * @param vRadius    竖直方向模糊度
     * @param iterations 模糊迭代度
     *                   高斯模糊
     * @return
     */
    public static Drawable boxBlurFilter(Bitmap bmp, float hRadius, float vRadius, int iterations) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < iterations; i++) {
            blur(inPixels, outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }
        blurFractional(inPixels, outPixels, width, height, hRadius);
        blurFractional(outPixels, inPixels, height, width, vRadius);
        bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    private static void blur(int[] in, int[] out, int width, int height,
                             float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];

        for (int i = 0; i < 256 * tableSize; i++)
            divide[i] = i / tableSize;

        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
                        | (divide[tg] << 8) | divide[tb];

                int i1 = x + r + 1;
                if (i1 > widthMinus1)
                    i1 = widthMinus1;
                int i2 = x - r;
                if (i2 < 0)
                    i2 = 0;
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];

                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }

    private static void blurFractional(int[] in, int[] out, int width,
                                       int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;

            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];

                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;
        }
    }

    private static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    /**
     * 隐藏手机号中间4位
     *
     * @param mobile
     * @return
     */
    public static String parsePhoneGone4(String mobile) {
        if (TextUtils.isEmpty(mobile)) return "";
        if (mobile.length() != 11) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
