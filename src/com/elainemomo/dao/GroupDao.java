package com.elainemomo.dao;

import org.w3c.dom.Text;

import com.elainemomo.globle.Contant;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

public class GroupDao {

	public static void insertGroup(ContentResolver resolver,String groupName){
		ContentValues values = new ContentValues();
		values.put("name", groupName);
		values.put("thread_count", 0);
		values.put("create_date", System.currentTimeMillis());
		resolver.insert(Contant.URI.URI_GROUP_INSERT, values);
	}
	
	public static void updateGroupByName(ContentResolver resolver,String groupName,int _id){
		ContentValues values = new ContentValues();
		values.put("name", groupName);
		String where = "_id="+_id;
		resolver.update(Contant.URI.URI_GROUP_UPDATE, values, where, null);
	}
	
	public static void deleteGroup(ContentResolver resolver,int _id){
		ContentValues values = new ContentValues();
		String where = "_id="+_id;
		resolver.delete(Contant.URI.URI_GROUP_DELETE, where, null);
	}
	
	public static String queryNameById(ContentResolver resolver,int _id){
		Cursor cursor = resolver.query(Contant.URI.URI_GROUP_QUERY, new String[]{"name"}, "_id="+_id, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(0);
		}
		return null;
	}
	
	public static int getThreadCount(ContentResolver resolver,int _id){
		int thread_count = -1;
		Cursor cursor = resolver.query(Contant.URI.URI_GROUP_QUERY, new String[]{"thread_count"}, "_id="+_id, null, null);
		cursor.moveToFirst();
		thread_count = cursor.getInt(0);
		return thread_count;
	}
	
	
}
