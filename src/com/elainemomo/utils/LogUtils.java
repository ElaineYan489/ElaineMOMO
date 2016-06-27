package com.elainemomo.utils;

import android.util.Log;

public class LogUtils {
	public static boolean isDug = true;
	public static void i(String tag,String msg){
		if(isDug){
			Log.i(tag, msg);
		}
	}
	
	public static void i(Object tag,String msg){
		if(isDug){
			Log.i(tag.getClass().getSimpleName(), msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(isDug){
			Log.i(tag, msg);
		}
	}
	
	public static void e(Object tag,String msg){
		if(isDug){
			Log.i(tag.getClass().getSimpleName(), msg);
		}
	}
}
