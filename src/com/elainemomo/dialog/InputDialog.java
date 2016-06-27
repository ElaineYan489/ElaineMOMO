package com.elainemomo.dialog;

import com.elainemomo.activity.ui.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputDialog extends BaseDialog {

	private String text;
	private Button bt_inputdialog_confirm;
	private Button bt_inputdialog_cancel;
	private EditText et_inputdialog_message;
	private TextView tv_inputdialog_title;
	private OnInputDialogListener onInputDialogListener;

	protected InputDialog(Context context,OnInputDialogListener onInputDialogListener,String text){
		super(context);
		this.onInputDialogListener = onInputDialogListener;
		this.text = text;
	}
	
	public static void showInputDialog(Context context,OnInputDialogListener onInputDialogListener,String text){
		InputDialog inputDialog = new InputDialog(context, onInputDialogListener, text);
		//为了显示软键盘：对话框默认不支持文本输入，手动把一个输入框设置为对话框的内容，Android自动对其进行设置
		inputDialog.setView(new EditText(context));
		inputDialog.show();
	}

	@Override
	public void initView() {
		setContentView(R.layout.dialog_input);
		tv_inputdialog_title = (TextView) findViewById(R.id.tv_inputdialog_title);
		et_inputdialog_message = (EditText) findViewById(R.id.et_inputdialog_message);
		bt_inputdialog_cancel = (Button) findViewById(R.id.bt_inputdialog_cancel);
		bt_inputdialog_confirm = (Button) findViewById(R.id.bt_inputdialog_confirm);
	}

	@Override
	public void initData() {
		tv_inputdialog_title.setText(text);
		
	}

	@Override
	public void initListener() {
		bt_inputdialog_cancel.setOnClickListener(this);
		bt_inputdialog_confirm.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.bt_inputdialog_confirm:
			if(onInputDialogListener != null){
				onInputDialogListener.confim(et_inputdialog_message.getText().toString());
			}
			dismiss();
			break;
		case R.id.bt_inputdialog_cancel:
			if(onInputDialogListener != null){
				onInputDialogListener.cancel();
			}
			dismiss();
		default:
			break;
		}
	}
	
	public interface OnInputDialogListener{
		void cancel();
		void confim(String inputMessage );
	}

}
