package com.wuruoye.library.adapter;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by wuruoye on 2018/2/21.
 * this file is to
 */

public abstract class TextWatchAdapter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
