package com.elainemomo.activity.base;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdpter extends FragmentPagerAdapter {

	List<Fragment> fragments;
	public MainPagerAdpter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
		
	}

	//返回的fragment会作为viewPager的条目显示出来，就相当于ViewPager容器中每个可以动态滑动的页面
	@Override
	public Fragment getItem(int location) {
		return fragments.get(location);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}





}
