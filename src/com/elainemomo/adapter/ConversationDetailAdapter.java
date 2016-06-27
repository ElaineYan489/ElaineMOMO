package com.elainemomo.adapter;

import com.elainemomo.activity.ui.R;
import com.elainemomo.bean.Sms;
import com.elainemomo.globle.Contant;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ConversationDetailAdapter extends CursorAdapter{

	static final int DURATION = 3*60*1000;
	ListView lv;
	public ConversationDetailAdapter(Context context, Cursor c,ListView lv) {
		super(context, c);
		this.lv = lv;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.item_conversation_details_list, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = getHolder(view);
		Sms sms = Sms.showSms(cursor);
		
		if(cursor.getPosition() == 0){
			showDate(context, holder, sms);
			holder.tv_conversation_detail_date.setVisibility(View.VISIBLE);
		}else{
			
			if(sms.getDate()-showPreDate(cursor.getPosition()-1)>DURATION){
				holder.tv_conversation_detail_date.setVisibility(View.VISIBLE);
				showDate(context, holder, sms);
			}else{
				holder.tv_conversation_detail_date.setVisibility(View.GONE);
			}
		}
		
		
		if(Contant.SMS.TYPE_RECEIVE == sms.getType()){
			holder.tv_conversation_detail_receive.setText(sms.getBody());
			holder.tv_conversation_detail_send.setVisibility(View.GONE);
		}
		else if(Contant.SMS.TYPE_SEND == sms.getType()){
			holder.tv_conversation_detail_send.setText(sms.getBody());
			holder.tv_conversation_detail_receive.setVisibility(View.GONE);
		}
		
		
	}
	
	private Long showPreDate(int position){
		Cursor cursor = (Cursor) getItem(position);
		Sms sms = Sms.showSms(cursor);
		return sms.getDate();
	}
	
	private void showDate(Context context , ViewHolder holder, Sms sms){
		if(DateUtils.isToday(sms.getDate())){
			holder.tv_conversation_detail_date.setText(DateFormat.getTimeFormat(context).format(sms.getDate()));
		}
		else{
			holder.tv_conversation_detail_date.setText(DateFormat.getDateFormat(context).format(sms.getDate()));
		}
			
	}
	
	public ViewHolder getHolder(View v){
		ViewHolder holder = (ViewHolder) v.getTag();
		if(holder == null){
			holder = new ViewHolder(v);
			v.setTag(holder);
		}
		return holder;
	}

	class ViewHolder{
		private TextView tv_conversation_detail_date;
		private TextView tv_conversation_detail_receive;
		private TextView tv_conversation_detail_send;

		public ViewHolder(View v){
			tv_conversation_detail_date = (TextView) v.findViewById(R.id.tv_conversation_detail_date);
			tv_conversation_detail_receive = (TextView) v.findViewById(R.id.tv_conversation_detail_receive);
			tv_conversation_detail_send = (TextView) v.findViewById(R.id.tv_conversation_detail_send);
		}
	}

	@Override
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
		//让listview滑动到指定的条目上
		lv.setSelection(getCount());
	}
	
	

}
