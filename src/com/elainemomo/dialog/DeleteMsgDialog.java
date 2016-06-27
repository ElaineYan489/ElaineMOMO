package com.elainemomo.dialog;

import com.elainemomo.activity.ui.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DeleteMsgDialog extends BaseDialog {

	private TextView tv_deletemsg_title;
	private ProgressBar pb_deletemsg;
	private Button bt_deletemsg_cancel;
	private int maxProgress;
	private OnDeleteCancelListener onDeleteCancelListener;
	protected DeleteMsgDialog(Context context,int maxProgress,OnDeleteCancelListener onDeleteCancelListener) {
		super(context);
		this.maxProgress = maxProgress;
		this.onDeleteCancelListener = onDeleteCancelListener;
	}
	
	public static DeleteMsgDialog showDeleteDialog(Context context,int maxProgress,OnDeleteCancelListener onDeleteCancelListener){
		DeleteMsgDialog dialog = new DeleteMsgDialog(context, maxProgress, onDeleteCancelListener);
		dialog.show();
		return dialog;
	}

	@Override
	public void initView() {
		setContentView(R.layout.dialog_delete);
		tv_deletemsg_title = (TextView) findViewById(R.id.tv_deletemsg_title);
		pb_deletemsg = (ProgressBar) findViewById(R.id.pb_deletemsg);
		bt_deletemsg_cancel = (Button) findViewById(R.id.bt_deletemsg_cancel);
	}

	@Override
	public void initData() {
		tv_deletemsg_title.setText("����ɾ��(0/"+maxProgress+")");
		//���������������ֵ
		pb_deletemsg.setMax(maxProgress);
		
	}

	@Override
	public void initListener() {
		bt_deletemsg_cancel.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.bt_deletemsg_cancel:
			if(onDeleteCancelListener != null){
				onDeleteCancelListener.onCancel();
			}
			dismiss();//�öԻ�����ʧ
			break;

		default:
			break;
		}
	}

	public interface OnDeleteCancelListener{
		void onCancel();
	}
	
	/**
	 * ˢ�½������ͱ���
	 * @param progress
	 */
	public void updataProgressAndTitle(int progress){
		pb_deletemsg.setProgress(progress);
		tv_deletemsg_title.setText("����ɾ��("+progress+"/"+maxProgress);
	}
}
