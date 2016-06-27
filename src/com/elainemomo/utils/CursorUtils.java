package com.elainemomo.utils;

import android.database.Cursor;

public class CursorUtils {

	public static void printCursor(Cursor cursor){
		LogUtils.i(cursor,"һ����"+ cursor.getCount()+"��");
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
