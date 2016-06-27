package com.elainemomo.activity.ui;

import javax.crypto.spec.IvParameterSpec;

import com.elainemomo.activity.base.BaseActivity;
import com.elainemomo.adapter.ConversationListAdapter;
import com.elainemomo.bean.Conversation;
import com.elainemomo.dao.SimpleAnsyQueryHandler;
import com.elainemomo.globle.Contant;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GroupDetailActivity extends BaseActivity {

	private ListView lv_group_details_list;
	private TextView tv_titlebar_title;
	private ImageView iv_titlebar_back_btn;
	private ConversationListAdapter adapter;

	@Override
	public void initView() {
		setContentView(R.layout.activity_group_details);
		lv_group_details_list = (ListView) findViewById(R.id.lv_group_details_list);
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		String groupName = intent.getStringExtra("groupName");
		int groupId = intent.getIntExtra("groupId", -1);
		initTitleBar(groupName);
		
		adapter = new ConversationListAdapter(this, null);
		lv_group_details_list.setAdapter(adapter);
		
		SimpleAnsyQueryHandler simpleAnsyQueryHandler = new SimpleAnsyQueryHandler(getContentResolver());
		String[] projection = {
				"sms.body AS snippet",
				"sms.thread_id AS _id",
				"groups.msg_count AS msg_count",
				"address AS address",
				"date AS date"
		};
		String selection = BuildSelectionQuery(groupId);
		simpleAnsyQueryHandler.startQuery(0, adapter, Contant.URI.URI_SMS_CONVERSATION, projection,selection, null, null);
		
	}

	@Override
	public void initListener() {
		iv_titlebar_back_btn.setOnClickListener(this);
		lv_group_details_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(adapter.isSelectMode()){
					//选中条目
					adapter.selectSingle(position);
				}
				else{
					//查看详细信息
					Intent intent = new Intent(GroupDetailActivity.this,ConversationDetailActivity.class);
					Cursor cursor = (Cursor) adapter.getItem(position);
					Conversation conversation = Conversation.createFromCursor(cursor);
					String address = conversation.getAddress();
					int thread_id = conversation.getThread_id();
					intent.putExtra("address", address);
					intent.putExtra("thread_id", thread_id);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.iv_titlebar_back_btn:
			finish();
			break;
			
		default:
			break;
		}
	}
	
	private void initTitleBar(String groupName){
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		iv_titlebar_back_btn = (ImageView) findViewById(R.id.iv_titlebar_back_btn);
		tv_titlebar_title.setText(groupName);
	}
	
	private String BuildSelectionQuery(int group_id){
		Cursor cursor = getContentResolver().query(Contant.URI.URI_THREAD_GROUP_QUERY, new String[]{"thread_id"}, "group_id="+group_id, null, null);
		String selection = "thread_id in (";
		while(cursor.moveToNext()){
			if(cursor.isLast()){
				selection += cursor.getInt(0)+")";
			}
			else{
				selection += cursor.getInt(0)+", ";
			}
		}
		return selection;
	}

}
