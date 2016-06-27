package com.elainemomo.activity.ui;
import java.util.ArrayList;
import java.util.List;

import com.elainemomo.activity.base.BaseActivity;
import com.elainemomo.activity.base.MainPagerAdpter;
import com.elainemomo.fragment.ConversationFragment;
import com.elainemomo.fragment.GroupFragment;
import com.elainemomo.fragment.SearchFragment;
import com.elainemomo.utils.LogUtils;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MainActivity extends BaseActivity {

	private ViewPager viewPager;
	private LinearLayout ll_tab_conversation;
	private LinearLayout ll_tab_group;
	private LinearLayout ll_tab_search;
	private TextView tv_tab_conversation;
	private TextView tv_tab_group;
	private TextView tv_tab_search;
	private List<Fragment> fragments;
	private MainPagerAdpter mainPagerAdpter;
	private View v_indicate_line;

	@Override
	public void initView() {
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		ll_tab_conversation = (LinearLayout) findViewById(R.id.ll_tab_conversation);
		ll_tab_group = (LinearLayout) findViewById(R.id.ll_tab_group);
		ll_tab_search = (LinearLayout) findViewById(R.id.ll_tab_search);
		 
		tv_tab_conversation = (TextView) findViewById(R.id.tv_tab_conversation);
		tv_tab_group = (TextView) findViewById(R.id.tv_tab_group);
		tv_tab_search = (TextView) findViewById(R.id.tv_tab_search);
		v_indicate_line = findViewById(R.id.v_indicate_line);
	}

	@Override
	public void initData() {
		fragments = new ArrayList<Fragment>();
		ConversationFragment conversationFragment = new ConversationFragment();
		GroupFragment groupFragment = new GroupFragment();
		SearchFragment searchFragment = new SearchFragment();
		fragments.add(conversationFragment);
		fragments.add(groupFragment);
		fragments.add(searchFragment);
		
		mainPagerAdpter = new MainPagerAdpter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(mainPagerAdpter);
		textLightAndScale();
		computeIndicateLineWidth();
	}

	@Override
	public void initListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//会话0，群组1，搜索2
				LogUtils.i(this, position+"");
				textLightAndScale();
			}
			
			//滑动过程中不断调用
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//参数positionOffsetPixels是实时反应单个page滑动的距离
				int distance = positionOffsetPixels/3;
				ViewPropertyAnimator.animate(v_indicate_line).translationX(distance+position*v_indicate_line.getWidth()).setDuration(0);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				//输入法管理器
				//隐藏输入法软键盘
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				
			}
		});
		
		ll_tab_conversation.setOnClickListener(this);
		ll_tab_group.setOnClickListener(this);
		ll_tab_search.setOnClickListener(this);
	}

	@Override
	public void processClick(View v) {
		switch(v.getId()){
		case R.id.ll_tab_conversation:
			viewPager.setCurrentItem(0);
			break;
		case R.id.ll_tab_group:
			viewPager.setCurrentItem(1);
			break;
		case R.id.ll_tab_search:
			viewPager.setCurrentItem(2);
			break;
		}
		
	}

	public void textLightAndScale(){
		int currentItem = viewPager.getCurrentItem();
		tv_tab_conversation.setTextColor(currentItem==0?Color.WHITE:0xaa666666);
		tv_tab_group.setTextColor(currentItem==1?Color.WHITE:0xaa666666);
		tv_tab_search.setTextColor(currentItem==2?Color.WHITE:0xaa666666);
		
		ViewPropertyAnimator.animate(tv_tab_conversation).scaleX(currentItem==0?1.2f:1);
		ViewPropertyAnimator.animate(tv_tab_group).scaleX(currentItem==1?1.2f:1);
		ViewPropertyAnimator.animate(tv_tab_search).scaleX(currentItem==2?1.2f:1);
		ViewPropertyAnimator.animate(tv_tab_conversation).scaleY(currentItem==0?1.2f:1);
		ViewPropertyAnimator.animate(tv_tab_group).scaleY(currentItem==1?1.2f:1);
		ViewPropertyAnimator.animate(tv_tab_search).scaleY(currentItem==2?1.2f:1);
	
	}

	private void computeIndicateLineWidth(){
		int width = getWindowManager().getDefaultDisplay().getWidth();
		v_indicate_line.getLayoutParams().width = width/3;
	}

}
