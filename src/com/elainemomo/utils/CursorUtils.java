package com.elainemomo.utils;

import android.database.Cursor;

public class CursorUtils {

	public static void printCursor(Cursor cursor){
		LogUtils.i(cursor,"一共有"+ cursor.getCount()+"行");
		while(cursor.moveToNext()){
			for(int i=0; i<cursor.getColumnCount();i++){
				String columnName = cursor.getColumnName(i);
				String content = cursor.getString(i);
				LogUtils.i(cursor, columnName+" : "+content);
			}
			LogUtils.i(cursor, "===============================");
		}
	}
}
