package com.elainemomo.adapter;

import com.elainemomo.activity.ui.R;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AutoSearchAdapter extends CursorAdapter {

	public AutoSearchAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.item_auto_search_lv, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = getHolder(view);
		holder.tv_auto_search_address.setText(cursor.getString(cursor.getColumnIndex("data1")));
		holder.tv_auto_search_name.setText(cursor.getString(cursor.getColumnIndex("display_name")));
		
		
	}
	
	public ViewHolder getHolder(View view){
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		return holder;
	}
	
	class ViewHolder{
		private TextView tv_auto_search_address;
		private TextView tv_auto_search_name;

		public ViewHolder(View view){
			tv_auto_search_address = (TextView) view.findViewById(R.id.tv_auto_search_address);
			tv_auto_search_name = (TextView) view.findViewById(R.id.tv_auto_search_name);
		}
	}

	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex("data1"));
	}
	
	
}
