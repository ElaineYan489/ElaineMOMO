package com.elainemomo.dao;

import com.elainemomo.globle.Contant;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ThreadGroupDao {
	//查询thread_group表中是否存在thread_id
	public static boolean queryThreadId(ContentResolver resolver,int thread_id){
		Cursor cursor = resolver.query(Contant.URI.URI_THREAD_GROUP_QUERY, null, "thread_id="+thread_id, null, null);
		return cursor.moveToNext();
	}
	
	//根据thread_id 查询group_id
	public static int queryGroupId(ContentResolver resolver,int thread_id){
		Cursor cursor = resolver.query(Contant.URI.URI_THREAD_GROUP_QUERY, new String[]{"group_id"}, "thread_id="+thread_id, null, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	public static boolean insertThreadGroup(ContentResolver resolver,int thread_id,int group_id){
		ContentValues values = new ContentValues();
		values.put("group_id", group_id);
		values.put("thread_id", thread_id);
		Uri uri = resolver.insert(Contant.URI.URI_THREAD_GROUP_INSERT, values);
		
		if(uri!=null){
			int currentThreadCount = GroupDao.getThreadCount(resolver, group_id);
			updateThreadCount(resolver, group_id, currentThreadCount+1);
		}
		
		return uri!=null;
		
	}
	
	public static boolean deleteThreadGroupByThreadId(ContentResolver resolver,int thread_id,int group_id){
		int number = resolver.delete(Contant.URI.URI_THREAD_GROUP_DELETE, "thread_id="+thread_id, null);
		int currentThreadCount = GroupDao.getThreadCount(resolver, group_id);
		updateThreadCount(resolver, group_id, currentThreadCount-1);
		return number>0;
	}
	
	public static void updateThreadCount(ContentResolver resolver,int _id,int thread_count){
		ContentValues values = new ContentValues();
		values.put("thread_count", thread_count);
		resolver.update(Contant.URI.URI_GROUP_UPDATE, values, "_id="+_id, null);
	}
}
