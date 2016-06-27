package com.elainemomo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupOpenHelper extends SQLiteOpenHelper {

	/**
	 * 保证内存中只有一个这样的数据库实例，所以使用单例模式
	 * 单例模式的特点是：构造函数是私有的；私有静态实例参数；公有静态的getInstance的方法
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
		//sqlite里面创建表的字段类型中数值型的只有integer，没有long等类型，而且要注意最后一个字段后面不能添加逗号，否则会报错
		
		//创建group表
		db.execSQL("create table groups("
				+ "_id integer primary key autoincrement,"
				+ "name varchar,"
				+ "create_date integer,"
				+ "thread_count integer"
				+ ")");
		
		//创建会话和群组的映射表
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
