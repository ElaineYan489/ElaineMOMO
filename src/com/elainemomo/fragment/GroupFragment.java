package com.elainemomo.fragment;

import com.elainemomo.activity.base.BaseFragment;
import com.elainemomo.activity.ui.GroupDetailActivity;
import com.elainemomo.activity.ui.R;
import com.elainemomo.adapter.GroupListAdapter;
import com.elainemomo.bean.Group;
import com.elainemomo.dao.GroupDao;
import com.elainemomo.dao.SimpleAnsyQueryHandler;
import com.elainemomo.dialog.DeleteMsgDialog;
import com.elainemomo.dialog.DeleteMsgDialog.OnDeleteCancelListener;
import com.elainemomo.dialog.InputDialog;
import com.elainemomo.dialog.InputDialog.OnInputDialogListener;
import com.elainemomo.dialog.ListDialog;
import com.elainemomo.dialog.ListDialog.OnClickListener;
import com.elainemomo.globle.Contant;
import com.elainemomo.utils.ToastUtils;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

public class GroupFragment extends BaseFragment {

	private Button bt_group_newgroup_btn;
	private ListView lv_group_list;
	private GroupListAdapter adapter;

	@Override
	public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group, null);
		bt_group_newgroup_btn = (Button) view.findViewById(R.id.bt_group_newgroup_btn);
		lv_group_list = (ListView) view.findViewById(R.id.lv_group_list);
		return view;
	}

	@Override
	public void initListener() {
		bt_group_newgroup_btn.setOnClickListener(this);
		lv_group_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				final Group group = Group.showGroup(cursor);
				ListDialog.showListDialog(getActivity(), "选择操作", new String[]{"修改","删除"}, new OnClickListener() {
					
					@Override
					public void onClickListener(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
						case 0:
							InputDialog.showInputDialog(getActivity(), new OnInputDialogListener() {
								
								@Override
								public void confim(String inputMessage) {
									GroupDao.updateGroupByName(getActivity().getContentResolver(), inputMessage, group.get_id());
								}
								
								@Override
								public void cancel() {
									
								}
							}, "修改组名");
							break;
						case 1:
							GroupDao.deleteGroup(getActivity().getContentResolver(), group.get_id());
							break;
						default:
							break;
						}
					}
				});
				return false;
			}
		});
		
		lv_group_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				Group group = Group.showGroup(cursor);
				if(group.getThread_count()>0){
					Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
					intent.putExtra("groupName", group.getName());
					intent.putExtra("groupId", group.get_id());
					startActivity(intent);
				}
				else{
					ToastUtils.showToast(getActivity(), "当前没有会话");
				}
				
			}
		});
	}

	@Override
	public void initData() {
		adapter = new GroupListAdapter(getActivity(), null);
		lv_group_list.setAdapter(adapter);
		
		SimpleAnsyQueryHandler simpleAnsyQueryHandler = new SimpleAnsyQueryHandler(getActivity().getContentResolver());
		simpleAnsyQueryHandler.startQuery(0, adapter, Contant.URI.URI_GROUP_QUERY, null, null, null, "create_date desc");
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.bt_group_newgroup_btn:
			InputDialog.showInputDialog(getActivity(), new OnInputDialogListener() {
				
				@Override
				public void confim(String inputMessage) {
					if(TextUtils.isEmpty(inputMessage)){
						ToastUtils.showToast(getActivity(), "请输入群组名称");
					}
					else{
						GroupDao.insertGroup(getActivity().getContentResolver(), inputMessage);
					}
				}
				
				@Override
				public void cancel() {
					
				}
			}, "创建群组");
			break;

		default:
			break;
		}
	}
 
}
