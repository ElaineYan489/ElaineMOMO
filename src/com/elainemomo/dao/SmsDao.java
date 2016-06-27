package com.elainemomo.dao;

import java.util.List;

import com.elainemomo.globle.Contant;
import com.elainemomo.receiver.SendSmsReceiver;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SmsDao {
	public  static void sendSms(Context context,String address,String body){
		SmsManager manager = SmsManager.getDefault();
		List<String> smss = manager.divideMessage(body);
		
		Intent intent = new Intent(SendSmsReceiver.ACTION_SEAND_SMS);
		//短信发出后，系统会发送一条广播，会告诉我们短信发送是否成功或者失败
		PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		for(String text : smss){
			//这个API只负责发短信，不会把短信写入数据库
			manager.sendTextMessage(address, null, text, pintent, null);
			
			//把短信存入短信数据库
			insertSms(context,address, text);
		}
	}
	
	public static void insertSms(Context context,String address,String body){
		ContentValues values = new ContentValues();
		values.put("address", address);
		values.put("body", body);
		values.put("type", Contant.SMS.TYPE_SEND);
		context.getContentResolver().insert(Contant.URI.URI_SMS, values);
	}
}
