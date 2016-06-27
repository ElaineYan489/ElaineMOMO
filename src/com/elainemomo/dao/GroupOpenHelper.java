package com.elainemomo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupOpenHelper extends SQLiteOpenHelper {

	/**
	 * ��֤�ڴ���ֻ��һ�����������ݿ�ʵ��������ʹ�õ���ģʽ
	 * ����ģʽ���ص��ǣ����캯����˽�еģ�˽�о�̬ʵ�����������о�̬��getInstance�ķ���
	 */
	private static GroupOpenHelper instance;
	
	public static GroupOpenHelper getInstance(Context context){
		if(instance == null){
			instance = new GroupOpenHelper(context, "group.db", null, 1);
		}
		return instance;
	}
	
	private GroupOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//sqlite���洴������ֶ���������ֵ�͵�ֻ��integer��û��long�����ͣ�����Ҫע�����һ���ֶκ��治����Ӷ��ţ�����ᱨ��
		
		//����group��
		db.execSQL("create table groups("
				+ "_id integer primary key autoincrement,"
				+ "name varchar,"
				+ "create_date integer,"
				+ "thread_count integer"
				+ ")");
		
		//�����Ự��Ⱥ���ӳ���
		db.execSQL("create table thread_group("
				+ "_id integer primary key autoincrement,"
				+ "group_id integer,"
				+ "thread_id integer"
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
