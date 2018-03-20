package com.wuruoye.library.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wuruoye on 2017/11/21.
 * this file is to
 */

public class FragmentVPAdapter extends FragmentStatePagerAdapter {
    private FragmentManager mFM;
    private List<String> mTitleList;
    private List<Fragment> mFragmentList;

    public FragmentVPAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        mFM = fm;
        mTitleList = titles;
        mFragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList.size() == mFragmentList.size()) {
            return mTitleList.get(position);
        }else {
            return super.getPageTitle(position);
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFM.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragmentList.get(position);
        mFM.beginTransaction().hide(fragment).commit();
    }
}
