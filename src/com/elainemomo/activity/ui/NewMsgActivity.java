package com.elainemomo.activity.ui;

import com.elainemomo.activity.base.BaseActivity;
import com.elainemomo.adapter.AutoSearchAdapter;
import com.elainemomo.dao.SmsDao;
import com.elainemomo.utils.CursorUtils;
import com.elainemomo.utils.ToastUtils;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;

public class NewMsgActivity extends BaseActivity {

	private AutoCompleteTextView et_newmsg_address;
	private EditText et_newmsg_body;
	private AutoSearchAdapter adapter;
	private TextView tv_titlebar_title;
	private ImageView iv_titlebar_back_btn;
	private ImageView iv_newmsg_select_contact;
	private Button bt_newmsg_send;

	@Override
	public void initView() {
		setContentView(R.layout.activity_newmsg);
		et_newmsg_address = (AutoCompleteTextView) findViewById(R.id.et_newmsg_address);
		et_newmsg_body = (EditText) findViewById(R.id.et_newmsg_body);
		iv_newmsg_select_contact = (ImageView) findViewById(R.id.iv_newmsg_select_contact);
		bt_newmsg_send = (Button) findViewById(R.id.bt_newmsg_send);
	}

	@Override
	public void initData() {
		adapter = new AutoSearchAdapter(this, null);
		et_newmsg_address.setAdapter(adapter);
		
		adapter.setFilterQueryProvider(new FilterQueryProvider() {
			
			@Override
			public Cursor runQuery(CharSequence constraint) {
				String[] projection = {
						"data1",
						"display_name",
						"_id"
				};
				//模糊查询
				String selection = "data1 like '%" + constraint + "%'";
				Cursor cursor = getContentResolver().query(Phone.CONTENT_URI, projection, selection, null, null);
				CursorUtils.printCursor(cursor);
				//返回cursor，就是把cursor交给adapter
				return cursor;
			}
		});
		initTitleBar();
	}

	@Override
	public void initListener() {
		iv_titlebar_back_btn.setOnClickListener(this);
		iv_newmsg_select_contact.setOnClickListener(this);
		bt_newmsg_send.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.iv_titlebar_back_btn:
			finish();
			break;
		case R.id.iv_newmsg_select_contact:
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("vnd.android.cursor.dir/contact");
			startActivityForResult(intent, 0);
			break;
		case R.id.bt_newmsg_send:
			String address = et_newmsg_address.getText().toString();
			String body = et_newmsg_body.getText().toString();
			if(!TextUtils.isEmpty(address)&& !TextUtils.isEmpty(body)){
				SmsDao.sendSms(this, address, body);
			}
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Uri uri = data.getData();
		if(uri !=null){
			String[] projection = {
					"_id",
					"has_phone_number"
				};
				Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
				cursor.moveToFirst();
				String _id = cursor.getString(0);
				int has_phone_number = cursor.getInt(1);
				if(has_phone_number == 0){
					ToastUtils.showToast(this, "没有该联系人的号码");
				}
				else{
					//拿着联系人id去查询号码
					String selection = "contact_id="+_id;
					Cursor cursor2 = getContentResolver().query(Phone.CONTENT_URI, new String[]{"data1"}, selection, null, null);
					cursor2.moveToFirst();
					String data1 = cursor2.getString(0);
					et_newmsg_address.setText(data1);
					et_newmsg_body.requestFocus();//内容获得焦点
					
				}
		}
	}
	
	private void initTitleBar(){
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		tv_titlebar_title.setText("发送短信");
		iv_titlebar_back_btn = (ImageView) findViewById(R.id.iv_titlebar_back_btn);
	}

}
