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
	 * ͨ�������ѯ����
	 * @param address
	 * @param resolver
	 * @return
	 */
	public static String getNameByAddress(String address,ContentResolver resolver){
		 Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		 Cursor cursor = resolver.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
//		 CursorUtils.printCursor(cursor);
		 //��Ϊ��һ�������ݵĲ�ѯ������ֻ��Ҫ�򵥵�ʹ��if�Ϳ�����
		 String name = null;
		 if(cursor.moveToFirst()){
			 name = cursor.getString(0);	
			 cursor.close();
		 }
		return name;
	}
	
	/**
	 * ���ݺ����ѯͼ��
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
			 //��ȡ��ϵ����Ƭ
			 InputStream is = Contacts.openContactPhotoInputStream(resolver,Uri.withAppendedPath(Contacts.CONTENT_URI, _id));
			 avatar = BitmapFactory.decodeStream(is);
		 }
		
		return avatar;
	}
}
