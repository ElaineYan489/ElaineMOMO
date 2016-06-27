package com.elainemomo.provider;

import com.elainemomo.dao.GroupOpenHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class GroupProvider extends ContentProvider {
	GroupOpenHelper helper;
	private SQLiteDatabase db;
	
	static final int CODE_GROUPS_INSERT = 0;
	static final int CODE_GROUPS_QUERY = 1;
	static final int CODE_GROUPS_UPDATE = 2;
	static final int CODE_GROUPS_DELETE = 3;
	static final int CODE_THREAD_GROUP_INSERT = 4;
	static final int CODE_THREAD_GROUP_QUERY = 5;
	static final int CODE_THREAD_GROUP_UPDATE = 6;
	static final int CODE_THREAD_GROUP_DELETE = 7;
	private final static String authority = "com.elainemomo";
	public static final Uri BASE_URI = Uri.parse("content://"+authority);
	
	UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	{
		//uri的匹配规则
		//authority为主机名，path为后接的路径，code
		matcher.addURI(authority, "group/insert", CODE_GROUPS_INSERT);
		matcher.addURI(authority, "group/query", CODE_GROUPS_QUERY);
		matcher.addURI(authority, "group/update", CODE_GROUPS_UPDATE);
		matcher.addURI(authority, "group/delete", CODE_GROUPS_DELETE);
		matcher.addURI(authority, "thread_group/insert", CODE_THREAD_GROUP_INSERT);
		matcher.addURI(authority, "thread_group/query", CODE_THREAD_GROUP_QUERY);
		matcher.addURI(authority, "thread_group/update", CODE_THREAD_GROUP_UPDATE);
		matcher.addURI(authority, "thread_group/delete", CODE_THREAD_GROUP_DELETE);
	}
	
	@Override
	public boolean onCreate() {
		//创建数据库
		helper = GroupOpenHelper.getInstance(getContext());
		db = helper.getWritableDatabase();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		switch (matcher.match(uri)) {
		case CODE_GROUPS_QUERY:
			Cursor cursor = db.query("groups", projection, selection, selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
			return cursor;
		case CODE_THREAD_GROUP_QUERY:
			cursor = db.query("thread_group", projection, selection, selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
			return cursor;
		default:
			throw new IllegalArgumentException("未识别的uri:"+uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		switch (matcher.match(uri)) {
		case CODE_GROUPS_INSERT:
			long rawId = db.insert("groups", null, values);
			//插入失败
			if(rawId == -1){
				return null;
			}
			else{
				getContext().getContentResolver().notifyChange(BASE_URI, null);
				//拼接uri:把返回的行id，拼接在uri的后面，然后返回
				return ContentUris.withAppendedId(uri, rawId);
			}
			
		case CODE_THREAD_GROUP_INSERT:
			rawId = db.insert("thread_group", null, values);
			//插入失败
			if(rawId == -1){
				return null;
			}
			else{
				getContext().getContentResolver().notifyChange(BASE_URI, null);
				//拼接uri:把返回的行id，拼接在uri的后面，然后返回
				return ContentUris.withAppendedId(uri, rawId);
			}
		default:
			throw new IllegalArgumentException("未识别uri:"+uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch (matcher.match(uri)) {
		case CODE_GROUPS_DELETE:
			int number = db.delete("groups", selection, selectionArgs);
			getContext().getContentResolver().notifyChange(BASE_URI, null);
			return number;
			
		case CODE_THREAD_GROUP_DELETE:
			number = db.delete("thread_group", selection, selectionArgs);
			getContext().getContentResolver().notifyChange(BASE_URI, null);
			return number;
		default:
			throw new IllegalArgumentException("未识别uri:"+uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		switch (matcher.match(uri)) {
		case CODE_GROUPS_UPDATE:
			int number = db.update("groups", values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(BASE_URI, null);
			return number;
		case CODE_THREAD_GROUP_UPDATE:
			number = db.update("thread_group", values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(BASE_URI, null);
			return number;
		default:
			throw new IllegalArgumentException("未识别uri:"+uri);
		}
	}

}
