package com.iswsc.smackdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySP {
	
	public static final String FILE_APPLICATION = "user";
	public static final String KEY_SERVER ="server";
	public static final String KEY_USERINFO ="userinfo";

	public static String readString(Context context,String fileName,String key){
		if(context == null) return "";
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE); 
		String value = sharedPreferences.getString(key, "");
		return value;
	}
	
	public static void write(Context context,String fileName,String key,String value){
		if(context == null)
			return;
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE); 
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**写入boolean开关值
	 * @param context
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public static void writeBoolean(Context context,String fileName,String key,boolean value){
		if(context == null)
			return;
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE); 
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static boolean readBoolean(Context context,String fileName,String key){
		if(context == null) return false;
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE); 
		return sharedPreferences.getBoolean(key, false);
	}
	
	/**
	 * 删除key值
	 */
	public static void remove(Context c,String key){
		if (c == null) return;
		SharedPreferences sharedPreferences = c.getSharedPreferences(MySP.FILE_APPLICATION, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}
}
