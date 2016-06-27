package com.elainemomo.fragment;

import java.util.List;

import com.elainemomo.activity.base.BaseFragment;
import com.elainemomo.activity.ui.ConversationDetailActivity;
import com.elainemomo.activity.ui.NewMsgActivity;
import com.elainemomo.activity.ui.R;
import com.elainemomo.adapter.ConversationListAdapter;
import com.elainemomo.bean.Conversation;
import com.elainemomo.bean.Group;
import com.elainemomo.dao.GroupDao;
import com.elainemomo.dao.SimpleAnsyQueryHandler;
import com.elainemomo.dao.ThreadGroupDao;
import com.elainemomo.dialog.ConfimDialog;
import com.elainemomo.dialog.ConfimDialog.OnConfimListener;
import com.elainemomo.dialog.DeleteMsgDialog;
import com.elainemomo.dialog.DeleteMsgDialog.OnDeleteCancelListener;
import com.elainemomo.dialog.ListDialog;
import com.elainemomo.dialog.ListDialog.OnClickListener;
import com.elainemomo.globle.Contant;
import com.elainemomo.utils.ToastUtils;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ConversationFragment extends BaseFragment {

	private View view;
	private Button btn_conversation_concel;
	private Button btn_conversation_edit;
	private Button btn_conversation_delete;
	private Button btn_conversation_selectAll;
	private Button btn_conversation_new_msg;
	private LinearLayout ll_conversation_menu_edit;
	private LinearLayout ll_conversation_menu_select;
	private ConversationListAdapter adapter;
	private ListView lv_conversation_list;
	
	static final int WHAT_DELETE_COMPLETE = 0;
	static final int WHAT_UPDATA_DELETE_PROGRESS = 1;
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case WHAT_DELETE_COMPLETE:
					//退出选择模式，进入编辑模式
					adapter.setSelectMode(false);
					adapter.notifyDataSetChanged();
					showEditMenu();
					dialog.dismiss();
				break;
			case WHAT_UPDATA_DELETE_PROGRESS:
				dialog.updataProgressAndTitle(msg.arg1+1);
				break;
			default:
				break;
			}
		}
		
	};


	private DeleteMsgDialog dialog;	
	@Override
	public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_conversation, null);
		btn_conversation_concel = (Button) view.findViewById(R.id.btn_conversation_concel);
		btn_conversation_edit = (Button) view.findViewById(R.id.btn_conversation_edit);
		btn_conversation_delete = (Button) view.findViewById(R.id.btn_conversation_delete);
		btn_conversation_selectAll = (Button) view.findViewById(R.id.btn_conversation_selectAll);
		btn_conversation_new_msg = (Button) view.findViewById(R.id.btn_conversation_new_msg);
		
		ll_conversation_menu_edit = (LinearLayout) view.findViewById(R.id.ll_conversation_menu_edit);
		ll_conversation_menu_select = (LinearLayout) view.findViewById(R.id.ll_conversation_menu_select);

		lv_conversation_list = (ListView) view.findViewById(R.id.lv_coversation_list);
		return view;
		
	}

	@Override
	public void initListener() {
		btn_conversation_concel.setOnClickListener(this);
		btn_conversation_edit.setOnClickListener(this);
		btn_conversation_delete.setOnClickListener(this);
		btn_conversation_selectAll.setOnClickListener(this);
		btn_conversation_new_msg.setOnClickListener(this);
		lv_conversation_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				Conversation conversation = Conversation.createFromCursor(cursor);
				//判断选中的会话是否有所属的群组
				if(ThreadGroupDao.queryThreadId(getActivity().getContentResolver(), conversation.getThread_id())){
					showGroupDeleteDialog(conversation.getThread_id());
				}
				else{
					showGroupSelectDialog(conversation.getThread_id());
				}
				return false;
			}
		});
		lv_conversation_list.setOnItemClickListener(new OnItemClickListener() {

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
	}

	@Override
	public void initData() {
		adapter = new ConversationListAdapter(getActivity(), null);
		lv_conversation_list.setAdapter(adapter);
		
		SimpleAnsyQueryHandler queryHandler = new SimpleAnsyQueryHandler(getActivity().getContentResolver());
		
		//自定义字段：自定义的projection会覆盖原先的字段,源码里面中必须要存在AS 所以。。
		String[] projection = {
				"sms.body AS snippet",
				"sms.thread_id AS _id",
				"groups.msg_count AS msg_count",
				"address AS address",
				"date AS date"
		};
		
		/**
		 * 开始异步查询
		 * arg0、arg1：可以用来携带一个int型和一个对象
		 * arg1：用来携带adapter对象，查询完毕后给adapter设置cursor
		 * arg6:order by 
		 */
		queryHandler.startQuery(0, adapter, Contant.URI.URI_SMS_CONVERSATION, projection, null, null, "date desc");
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_conversation_edit:
			showSelectMenu();
			adapter.setSelectMode(true);
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_conversation_new_msg:
			Intent intent = new Intent(getActivity(), NewMsgActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_conversation_selectAll:
			adapter.selectAll();
			adapter.notifyDataSetChanged();
			break;
        case R.id.btn_conversation_concel:
			showEditMenu();
			adapter.setSelectMode(false);
			adapter.cancelAll();
			break;
        case R.id.btn_conversation_delete:
        	if(adapter.getSelectedConversationIds().size() == 0){
        		return;
        	}
        	showDeleteDialog();
        	break;
		default:
			break;
		}
	}
	
	private void showSelectMenu(){
		ViewPropertyAnimator.animate(ll_conversation_menu_edit).translationY(ll_conversation_menu_edit.getWidth()).setDuration(200);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				ViewPropertyAnimator.animate(ll_conversation_menu_select).translationY(0).setDuration(200);
			}
		}, 200);
	}
	
	private void showEditMenu(){
		ViewPropertyAnimator.animate(ll_conversation_menu_select).translationY(ll_conversation_menu_select.getWidth()).setDuration(200);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				ViewPropertyAnimator.animate(ll_conversation_menu_edit).translationY(0).setDuration(200);
			}
		}, 200);
	}
	
	boolean isStopDelete = false;
	private List<Integer> selectedConversationIds;
	private void deleteSms(){
		selectedConversationIds = adapter.getSelectedConversationIds();
		dialog = DeleteMsgDialog.showDeleteDialog(getActivity(), selectedConversationIds.size(), new OnDeleteCancelListener() {
			
			@Override
			public void onCancel() {
				isStopDelete = true;
			}
		});
		
		Thread thread = new Thread(){

			@Override
			public void run() {
				super.run();
				
				for(int i=0;i<selectedConversationIds.size();i++){
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(isStopDelete){
						isStopDelete = false;
						break;
					}
					String where = "thread_id="+selectedConversationIds.get(i);
					getActivity().getContentResolver().delete(Contant.URI.URI_SMS, where, null);
					
					//发送消息，让删除进度条刷新，同时把当前的删除进度传给进度条
					Message msg = handler.obtainMessage();
					msg.what = WHAT_UPDATA_DELETE_PROGRESS;
					//把删除进度存入到Message消息中
					msg.arg1 = i;
					handler.sendMessage(msg);
				}
				selectedConversationIds.clear();
				handler.sendEmptyMessage(WHAT_DELETE_COMPLETE);
			}
			
		};
		
		thread.start();
	}
	
	private void showDeleteDialog(){
		ConfimDialog.showDialog(getActivity(), "提示", "确定需要删除吗？", new OnConfimListener() {
			
			@Override
			public void onConfim() {
				deleteSms();
			}
			
			@Override
			public void onCancel() {
				
			}
		});
	}
	
	private void showGroupDeleteDialog(final int thread_id){
		//根据thread_id查询group_id
		final int group_id = ThreadGroupDao.queryGroupId(getActivity().getContentResolver(), thread_id);
		//根据group_id查询name
		String name = GroupDao.queryNameById(getActivity().getContentResolver(), group_id);
		String message ="该会话已经存在于["+name+"]群组中，需要退出吗？";
		ConfimDialog.showDialog(getActivity(), "提示", message, new OnConfimListener() {
			
			@Override
			public void onConfim() {
				boolean deleteThreadGroupByThreadId = ThreadGroupDao.deleteThreadGroupByThreadId(getActivity().getContentResolver(), thread_id, group_id);
				ToastUtils.showToast(getActivity(), deleteThreadGroupByThreadId?"退出成功":"退出失败");
			}
			
			@Override
			public void onCancel() {
				
			}
		});
	}
	
	private void showGroupSelectDialog(final int thread_id){
		
		final Cursor cursor = getActivity().getContentResolver().query(Contant.URI.URI_GROUP_QUERY, null, null, null, null);
		if(cursor.getCount() == 0){
			ToastUtils.showToast(getActivity(), "当前没有已创建好的群组");
			return;
		}
		String[] items = new String[cursor.getCount()];
		while(cursor.moveToNext()){
			Group group = Group.showGroup(cursor);
			items[cursor.getPosition()] = group.getName();
		}

		ListDialog.showListDialog(getActivity(), "选择群组", items, new OnClickListener(){
			
			@Override
			public void onClickListener(AdapterView<?> parent, View view, int position, long id) {
				cursor.moveToPosition(position);
				Group group = Group.showGroup(cursor);
				boolean insertThreadGroup = ThreadGroupDao.insertThreadGroup(getActivity().getContentResolver(), thread_id, group.get_id());
				ToastUtils.showToast(getActivity(), insertThreadGroup?"插入成功":"插入失败");
			}
			
		});
	}
}
