package com.elainemomo.adapter;

import com.elainemomo.activity.ui.R;
import com.elainemomo.bean.Group;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class GroupListAdapter extends CursorAdapter {


	private ViewHolder holder;

	public GroupListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		return View.inflate(context, R.layout.item_group_list, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		holder = getHolder(view);
		Group group = Group.showGroup(cursor);
		holder.tv_grouplist_name.setText(group.getName()+" ("+group.getThread_count()+")");
		if(DateUtils.isToday(group.getCreate_date())){
			holder.tv_grouplist_time.setText(DateFormat.getTimeFormat(context).format(group.getCreate_date()));
		}
		else{
			holder.tv_grouplist_time.setText(DateFormat.getDateFormat(context).format(group.getCreate_date()));
		}
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
		private TextView tv_grouplist_name;
		private TextView tv_grouplist_time;

		public ViewHolder(View view){
			tv_grouplist_name = (TextView) view.findViewById(R.id.tv_grouplist_name);
			tv_grouplist_time = (TextView) view.findViewById(R.id.tv_grouplist_time);
		}
	}

}
