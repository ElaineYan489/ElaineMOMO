package com.elainemomo.dao;

import java.io.InputStream;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.PhoneLookup;

public class ContactDao {
	
	/**
	 * 通过号码查询名字
	 * @param address
	 * @param resolver
	 * @return
	 */
	public static String getNameByAddress(String address,ContentResolver resolver){
		 Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		 Cursor cursor = resolver.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
//		 CursorUtils.printCursor(cursor);
		 //因为是一条条数据的查询，所以只需要简单的使用if就可以了
		 String name = null;
		 if(cursor.moveToFirst()){
			 name = cursor.getString(0);	
			 cursor.close();
		 }
		return name;
	}
	
	/**
	 * 根据号码查询图像
	 * @param address
	 * @param resolver
	 * @return
	 */
	public static Bitmap getAvatarByAddress(String address,ContentResolver resolver){
		String _id = null;
		Bitmap avatar = null;
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		Cursor cursor = resolver.query(uri, new String[]{PhoneLookup._ID}, null, null, null);
		 if(cursor.moveToFirst()){
			 _id = cursor.getString(0);
			 //获取联系人照片
			 InputStream is = Contacts.openContactPhotoInputStream(resolver,Uri.withAppendedPath(Contacts.CONTENT_URI, _id));
			 avatar = BitmapFactory.decodeStream(is);
		 }
		
		return avatar;
	}
}
