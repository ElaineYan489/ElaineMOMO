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

	//���ص�fragment����ΪviewPager����Ŀ��ʾ���������൱��ViewPager������ÿ�����Զ�̬������ҳ��
	@Override
	public Fragment getItem(int location) {
		return fragments.get(location);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}





}
