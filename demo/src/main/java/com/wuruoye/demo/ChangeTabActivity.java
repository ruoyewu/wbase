package com.wuruoye.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.widget.changetab.ChangeTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/5/17 13:57.
 * @Description :
 */

public class ChangeTabActivity extends WBaseActivity {
    private ViewPager vp;
    private ChangeTabLayout ctl;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_tab;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        vp = findViewById(R.id.vp_change_tab);
        ctl = findViewById(R.id.ctl_change_tab);

        final List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragmentList.add(new EmptyFragment());
        }

        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "PAGE " + position;
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });

        ctl.attachViewPager(vp);
    }
}
