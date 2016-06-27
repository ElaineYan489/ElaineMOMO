package com.elainemomo.bean;

import android.database.Cursor;

public class Conversation {
	
	private String snippet;
	private int thread_id;
	private String msg_count;
	private String address;
	private Long date;
	
	/**
	 * 创建会话bean对象
	 * @param cursor
	 * @return
	 */
	public static Conversation createFromCursor(Cursor cursor){
		Conversation conversation = new Conversation();
		conversation.setSnippet(cursor.getString(cursor.getColumnIndex("snippet")));
		conversation.setThread_id(cursor.getInt(cursor.getColumnIndex("_id")));//因为CursorAdapter中必须需要一个_id的标志，才能进行查询
		conversation.setMsg_count(cursor.getString(cursor.getColumnIndex("msg_count")));
		conversation.setAddress(cursor.getString(cursor.getColumnIndex("address")));
		conversation.setDate(cursor.getLong(cursor.getColumnIndex("date")));
		return conversation;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(long l) {
		this.date = l;
	}

	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public String getMsg_count() {
		return msg_count;
	}
	public void setMsg_count(String msg_count) {
		this.msg_count = msg_count;
	}
	
	
}
