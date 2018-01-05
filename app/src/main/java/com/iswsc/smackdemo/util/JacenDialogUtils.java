package com.iswsc.smackdemo.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 显示对话框
 * <p>Title: DialogUtils.java</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>E-mail: jacen@wscnydx.com</p>
 * @author wsc
 * @date 2015-3-18 下午10:12:19
 * @version 1.0
 */
public class JacenDialogUtils {

	private static ProgressDialog mDialog;

	@Deprecated
	public static void showDialog(Context mContext, String msg) {
		dismissDialog();
		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage(msg);
		mDialog.setCancelable(false);
		mDialog.show();
	}

	public static ProgressDialog showDialog(String msg,Context mContext) {
        ProgressDialog mDialog = new ProgressDialog(mContext);
		mDialog.setMessage(msg);
		mDialog.setCancelable(false);
		mDialog.show();
		return mDialog;
	}



	public static void dismissDialog() {
		try {
			if (mDialog != null) {
				mDialog.dismiss();
				mDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
