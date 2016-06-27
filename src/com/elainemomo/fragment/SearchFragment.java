package com.elainemomo.fragment;

import com.elainemomo.activity.base.BaseFragment;
import com.elainemomo.activity.ui.ConversationDetailActivity;
import com.elainemomo.activity.ui.R;
import com.elainemomo.adapter.ConversationListAdapter;
import com.elainemomo.bean.Conversation;
import com.elainemomo.dao.SimpleAnsyQueryHandler;
import com.elainemomo.globle.Contant;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchFragment extends BaseFragment {

	private EditText et_search;
	private ListView lv_search_list;
	private SimpleAnsyQueryHandler simpleAnsyQueryHandler;
	private String[] projection = {
			"sms.body AS snippet",
			"sms.thread_id AS _id",
			"groups.msg_count AS msg_count",
			"address AS address",
			"date AS date"
	};
	private ConversationListAdapter adapter;
	@Override
	public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, null);
		et_search = (EditText) view.findViewById(R.id.et_search);
		lv_search_list = (ListView) view.findViewById(R.id.lv_search_list);
		return view;
	}

	@Override
	public void initListener() {
		et_search.addTextChangedListener(new TextWatcher() {
			
			//s:EditText中输入的参数
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				simpleAnsyQueryHandler.startQuery(0, adapter, Contant.URI.URI_SMS_CONVERSATION, projection, "body like '%"+s+"%'", null, null);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		lv_search_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(adapter.isSelectMode()){
					//选中条目
					adapter.selectSingle(position);
				}
				else{
					//查看详细信息
					Intent intent = new Intent(getActivity(),ConversationDetailActivity.class);
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
		
		lv_search_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//输入法管理器
				//隐藏输入法软键盘
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
			}
		});
	}

	@Override
	public void initData() {
		adapter = new ConversationListAdapter(getActivity(), null);
		lv_search_list.setAdapter(adapter);
		simpleAnsyQueryHandler = new SimpleAnsyQueryHandler(getActivity().getContentResolver());
		
	}

	@Override
	public void processClick(View v) {
		
	}

}
