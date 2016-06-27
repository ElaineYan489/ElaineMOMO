package com.elainemomo.dialog;

import com.elainemomo.activity.ui.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfimDialog extends BaseDialog {

	private String title;
	private String message;
	private TextView tv_dialog_message;
	private TextView tv_dialog_title;
	private Button bt_dialog_cancel;
	private Button bt_dialog_confirm;
	private OnConfimListener onConfimListener;
	
	public OnConfimListener getOnConfimListener() {
		return onConfimListener;
	}

	public void setOnConfimListener(OnConfimListener onConfimListener) {
		this.onConfimListener = onConfimListener;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected ConfimDialog(Context context) {
		super(context);
		
	}
	
	public static void showDialog(Context context,String title,String message,OnConfimListener onConfimListener){
		ConfimDialog confimDialog = new ConfimDialog(context);
		confimDialog.setTitle(title);
		confimDialog.setMessage(message);
		confimDialog.setOnConfimListener(onConfimListener);
		confimDialog.show();
	}

	@Override
	public void initView() {
		setContentView(R.layout.dialog_confim);
		tv_dialog_message = (TextView) findViewById(R.id.tv_dialog_message);
		tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
		bt_dialog_cancel = (Button) findViewById(R.id.bt_dialog_cancel);
		bt_dialog_confirm = (Button) findViewById(R.id.bt_dialog_confirm);
	}

	@Override
	public void initData() {
		tv_dialog_message.setText(message);
		tv_dialog_title.setText(title);
	}

	@Override
	public void initListener() {
		bt_dialog_cancel.setOnClickListener(this);
		bt_dialog_confirm.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.bt_dialog_cancel:
			if(onConfimListener != null){
				onConfimListener.onCancel();
			}
			break;
		case R.id.bt_dialog_confirm:
			if(onConfimListener != null){
				onConfimListener.onConfim();
			}
			break;
		default:
			break;
		}
		dismiss();
	}
	
	//ÉèÖÃ¼àÌý½Ó¿Ú
	public interface OnConfimListener{
		void onCancel();
		void onConfim();
	}

}
