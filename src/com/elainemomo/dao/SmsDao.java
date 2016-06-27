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
		//���ŷ�����ϵͳ�ᷢ��һ���㲥����������Ƕ��ŷ����Ƿ�ɹ�����ʧ��
		PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		for(String text : smss){
			//���APIֻ���𷢶��ţ�����Ѷ���д�����ݿ�
			manager.sendTextMessage(address, null, text, pintent, null);
			
			//�Ѷ��Ŵ���������ݿ�
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
