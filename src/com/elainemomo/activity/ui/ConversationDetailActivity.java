package com.elainemomo.activity.ui;

import com.elainemomo.activity.base.BaseActivity;
import com.elainemomo.adapter.ConversationDetailAdapter;
import com.elainemomo.dao.ContactDao;
import com.elainemomo.dao.SimpleAnsyQueryHandler;
import com.elainemomo.dao.SmsDao;
import com.elainemomo.globle.Contant;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ConversationDetailActivity extends BaseActivity {

	private Button bt_conversation_detail_send;
	private EditText et_conversation_detail;
	private ImageView iv_titlebar_back_btn;
	private TextView tv_titlebar_title;
	private String address;
	private int thread_id;
	private SimpleAnsyQueryHandler simpleAnsyQueryHandler;
	private ConversationDetailAdapter adapter;
	private ListView lv_conversation_detail;

	@Override
	public void initView() {
		setContentView(R.layout.activity_conversation_details);
		bt_conversation_detail_send = (Button) findViewById(R.id.bt_conversation_detail_send);
		et_conversation_detail = (EditText) findViewById(R.id.et_conversation_detail);
		iv_titlebar_back_btn = (ImageView) findViewById(R.id.iv_titlebar_back_btn);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		lv_conversation_detail = (ListView) findViewById(R.id.lv_conversation_detail);
		
		lv_conversation_detail.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	}

	@Override
	public void initData() {
		//拿到传递过来的数据
		Intent intent = getIntent();
		if(intent != null){
			address = intent.getStringExtra("address");
			thread_id = intent.getIntExtra("thread_id", -1);
			initTitleBar();
		}
		
		adapter = new ConversationDetailAdapter(this, null,lv_conversation_detail );
		lv_conversation_detail.setAdapter(adapter);
		
		String[] projection = {
			"_id",
			"date",
			"body",
			"type"
		};
		
		String selection = "thread_id="+thread_id;
		simpleAnsyQueryHandler = new SimpleAnsyQueryHandler(getContentResolver());
		simpleAnsyQueryHandler.startQuery(0, adapter, Contant.URI.URI_SMS, projection, selection, null, "date");
	}

	@Override
	public void initListener() {
		iv_titlebar_back_btn.setOnClickListener(this);
		bt_conversation_detail_send.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.iv_titlebar_back_btn:
			finish();
			break;
		case R.id.bt_conversation_detail_send:
			String body = et_conversation_detail.getText().toString();
			if(!TextUtils.isEmpty(body)){
				SmsDao.sendSms(this,address, body);
			}
			break;
		default:
			break;
		}
	}
	
	private void initTitleBar(){
		String name = ContactDao.getNameByAddress(address, getContentResolver());
		tv_titlebar_title.setText(TextUtils.isEmpty(name)?address:name);
	}
	
	

}
