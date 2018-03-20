package com.wuruoye.library.util;

import android.util.Log;

/**
 * Created by wuruoye on 2018/2/14.
 * this file is to
 */

public class LogUtil {
    public static final boolean IS_DEBUG = true;

    public static void loge(Object object, String log) {
        if (IS_DEBUG) {
            Log.e(object.getClass().getName(), log);
        }
    }
}
