package com.elainemomo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.elainemomo.activity.ui.R;
import com.elainemomo.bean.Conversation;
import com.elainemomo.dao.ContactDao;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Visibility;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConversationListAdapter extends CursorAdapter {

	public List<Integer> selectedConversationIds = new ArrayList<Integer>();
	private boolean isSelectMode = false;
	public boolean isSelectMode() {
		return isSelectMode;
	}

	public void setSelectMode(boolean isSelectMode) {
		this.isSelectMode = isSelectMode;
	}
	
	public List<Integer> getSelectedConversationIds() {
		return selectedConversationIds;
	}

	public ConversationListAdapter(Context context, Cursor c) {
		super(context, c);
		notifyDataSetChanged();//重新刷新界面，数据库改变，前台就会改变
		
	}

	//返回的view对象就是Listview的条目
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.item_conversation_list, null);
	}

	//设置listview每个条目显示的内容
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = getHolder(view);
		Conversation conversation = Conversation.createFromCursor(cursor);
		
		if(isSelectMode){
			holder.iv_check.setVisibility(View.VISIBLE);
			if(selectedConversationIds.contains(conversation.getThread_id())){
				holder.iv_check.setBackgroundResource(R.drawable.common_checkbox_checked);
			}else{
				holder.iv_check.setBackgroundResource(R.drawable.common_checkbox_normal);
			}
		}
		else{
			holder.iv_check.setVisibility(View.GONE);
		}
		
		//设置短信内容
		holder.tv_conversation_body.setText(conversation.getSnippet());
		
		String name = ContactDao.getNameByAddress(conversation.getAddress(), context.getContentResolver());
		if(TextUtils.isEmpty(name)){
			holder.tv_conversation_address.setText(conversation.getAddress()+"("+conversation.getMsg_count()+")");
		}
		else{
			holder.tv_conversation_address.setText(name+"("+conversation.getMsg_count()+")");
		}
		//显示时间
		if(DateUtils.isToday(conversation.getDate())){
			//如果是当前的时间，就显示时分
			holder.tv_conversation_date.setText(DateFormat.getTimeFormat(context).format(conversation.getDate()));
		}
		else{
			//如果不是当前的时间，就显示年月日
			holder.tv_conversation_date.setText(DateFormat.getDateFormat(context).format(conversation.getDate()));
		}
		
		//设置头像
		Bitmap avatar = ContactDao.getAvatarByAddress(conversation.getAddress(), context.getContentResolver());
		if(avatar != null){
			holder.iv_conversation_avatar.setBackgroundDrawable(new BitmapDrawable(avatar));
		}
		else{
			holder.iv_conversation_avatar.setBackgroundResource(R.drawable.img_default_avatar);
		}
		
		
	}
	
	private ViewHolder getHolder(View view){
		//先判断条目view对象中是否有holder
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			//如果没有，就创建一个，并存入view对象
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		return holder;
	}
	
	class ViewHolder{
		private ImageView iv_conversation_avatar;
		private TextView tv_conversation_address;
		private TextView tv_conversation_body;
		private TextView tv_conversation_date;
		private ImageView iv_check;

		//参数就是条目的view对象
		public ViewHolder(View view){
			iv_conversation_avatar = (ImageView) view.findViewById(R.id.iv_conversation_avatar);
			tv_conversation_address = (TextView) view.findViewById(R.id.tv_conversation_address);
			tv_conversation_body = (TextView) view.findViewById(R.id.tv_conversation_body);
			tv_conversation_date = (TextView) view.findViewById(R.id.tv_conversation_date);
			iv_check = (ImageView)view.findViewById(R.id.iv_check);
		}
	}
	
	/**
	 * 把选中的条目存入集合中
	 * @param position
	 */
	public void selectSingle(int position){
		Cursor cursor = (Cursor) getItem(position);
		Conversation conversation = Conversation.createFromCursor(cursor);
		if(selectedConversationIds.contains(conversation.getThread_id())){
			selectedConversationIds.remove((Integer)conversation.getThread_id());
		}
		else{
			selectedConversationIds.add(conversation.getThread_id());
		}
		notifyDataSetChanged();
	}
	
	public void selectAll(){
		Cursor cursor = getCursor();
		//因为这里拿到的cursor的索引是随机的，所以要自己先定好位
		cursor.moveToPosition(-1);
		selectedConversationIds.clear();
		while(cursor.moveToNext()){
			Conversation conversation = Conversation.createFromCursor(cursor);
			selectedConversationIds.add(conversation.getThread_id());
		}
	}
	
	public void cancelAll(){
		selectedConversationIds.clear();
		notifyDataSetChanged();//界面刷新
	}

}
