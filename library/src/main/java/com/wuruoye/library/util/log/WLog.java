package com.wuruoye.library.util.log;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by wuruoye on 2018/2/14.
 * this file is to
 */

public class WLog {
    public static boolean IS_LOG = true;
    public static final String SPACE = "\t";
    private static Gson mGson = new Gson();

    public static void loge(Object object, Object value) {
        if (IS_LOG) {
            Log.e(object.getClass().getName(), mGson.toJsonTree(value).getAsString());
        }
    }
}
