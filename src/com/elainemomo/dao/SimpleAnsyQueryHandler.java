package com.elainemomo.dao;

import com.elainemomo.utils.CursorUtils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

public class SimpleAnsyQueryHandler extends AsyncQueryHandler {

	public SimpleAnsyQueryHandler(ContentResolver cr) {
		super(cr);
	}

	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		super.onQueryComplete(token, cookie, cursor);
//		CursorUtils.printCursor(cursor);
		
		if(cookie != null && cookie instanceof CursorAdapter){
			((CursorAdapter)cookie).changeCursor(cursor);
		}
	}

	
}
