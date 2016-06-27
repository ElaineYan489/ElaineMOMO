package com.elainemomo.dialog;


import com.elainemomo.activity.ui.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class BaseDialog extends AlertDialog implements android.view.View.OnClickListener {

	protected BaseDialog(Context context) {
		super(context,R.style.BaseDialog);
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initData();
		initListener();
	}



	public abstract void initView();
	public abstract void initData();
	public abstract void initListener();
	public abstract void processClick(View v);

	@Override
	public void onClick(View v) {
		processClick(v);
	}

}
