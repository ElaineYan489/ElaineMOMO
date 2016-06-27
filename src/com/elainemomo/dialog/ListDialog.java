package com.elainemomo.dialog;

import com.elainemomo.activity.ui.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListDialog extends BaseDialog {

	private String title;
	private String[] items;
	private TextView tv_dialoglist_title;
	private ListView lv_dialog_list;
	
	private Context context;
	private OnClickListener onClickListener;
	private DialogListAdapter adapter;

	protected ListDialog(Context context,String title,String[] items,OnClickListener onClickListener) {
		super(context);
		this.context = context;
		this.title = title;
		this.items = items;
		this.onClickListener = onClickListener;
	}
	
	public static void showListDialog(Context context,String title,String[] items,OnClickListener onItemClickListener){
		ListDialog listDialog = new ListDialog(context, title, items, onItemClickListener);
		listDialog.show();
	}
	

	@Override
	public void initView() {
		setContentView(R.layout.dialog_list);
		tv_dialoglist_title = (TextView) findViewById(R.id.tv_dialoglist_title);
		lv_dialog_list = (ListView) findViewById(R.id.lv_dialog_list);
	}

	@Override
	public void initData() {
		tv_dialoglist_title.setText(title);
		adapter = new DialogListAdapter();
		lv_dialog_list.setAdapter(adapter);
		
	}

	@Override
	public void initListener() {
		lv_dialog_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(onClickListener != null){
					onClickListener.onClickListener(parent, view, position, id);;
				}
				dismiss();
			}
		});
	}

	@Override
	public void processClick(View v) {
		
	}
	
	class DialogListAdapter extends BaseAdapter{

		private TextView tv_dialoglist_item;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(context, R.layout.item_listdialog, null);
			tv_dialoglist_item = (TextView) view.findViewById(R.id.tv_dialoglist_item);
			tv_dialoglist_item.setText(items[position]);
			return view;
		}

	}
	
	public interface OnClickListener{
		void onClickListener(AdapterView<?> parent, View view, int position, long id);
	}
	

}
