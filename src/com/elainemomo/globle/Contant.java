package com.elainemomo.globle;

import android.net.Uri;

public class Contant {
	public interface URI{
		Uri URI_SMS_CONVERSATION = Uri.parse("content://sms/conversations");
		Uri URI_SMS = Uri.parse("content://sms");
		Uri URI_GROUP_INSERT = Uri.parse("content://com.elainemomo/group/insert");
		Uri URI_GROUP_QUERY = Uri.parse("content://com.elainemomo/group/query");
		Uri URI_GROUP_UPDATE = Uri.parse("content://com.elainemomo/group/update");
		Uri URI_GROUP_DELETE = Uri.parse("content://com.elainemomo/group/delete");
		Uri URI_THREAD_GROUP_INSERT = Uri.parse("content://com.elainemomo/thread_group/insert");
		Uri URI_THREAD_GROUP_QUERY = Uri.parse("content://com.elainemomo/thread_group/query");
		Uri URI_THREAD_GROUP_UPDATE = Uri.parse("content://com.elainemomo/thread_group/update");
		Uri URI_THREAD_GROUP_DELETE = Uri.parse("content://com.elainemomo/thread_group/delete");
	}
	
	public interface SMS{
		int TYPE_RECEIVE = 1;
		int TYPE_SEND = 2;
	}
}
