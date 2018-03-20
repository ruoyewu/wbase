package com.wuruoye.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the base of all fragment
 */

public abstract class AbsBaseFragment extends Fragment {
    protected abstract int getContentView();
    protected abstract void initData(Bundle bundle);
    protected abstract void initView(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        initData(getArguments());
        initView(view);
        return view;
    }
}
