package com.wuruoye.library.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wuruoye.library.WBaseApp;


/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class KeyBoardUtil {

    public static void hideSoftKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) WBaseApp.getApp()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert manager != null;
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftKeBoard(View view) {
        if (view.requestFocus()) {
            InputMethodManager manager = (InputMethodManager) WBaseApp.getApp()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            assert manager != null;
            manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void hideSoftKeyBoard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        hideSoftKeyboard(view);
    }

    public static void showSoftKeyBoard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        showSoftKeBoard(view);
    }

    public static void hideSoftKeyBoard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        hideSoftKeyboard(view);
    }

    public static void showSoftKeyBoard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        showSoftKeBoard(view);
    }

    public static void clip(Context context, String text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("content", text));
    }
}
