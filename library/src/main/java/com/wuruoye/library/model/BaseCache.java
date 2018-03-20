package com.wuruoye.library.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to the base cache manager class
 */

public abstract class BaseCache {
    private static String SP_NAME = "sp_name";

    public static void init(String name) {
        SP_NAME = name;
    }

    private SharedPreferences mSP;

    protected abstract void clearCache();

    public BaseCache(Context context){
        if (mSP == null){
            synchronized (this){
                mSP = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            }
        }
    }

    protected boolean getBoolean(String key, Boolean value){
        return mSP.getBoolean(key, value);
    }
    protected void putBoolean(String key, Boolean value){
        mSP.edit().putBoolean(key, value).apply();
    }

    protected int getInt(String key, int value){
        return mSP.getInt(key, value);
    }
    protected void putInt(String key, int value){
        mSP.edit().putInt(key, value).apply();
    }

    protected long getLong(String key, long value){
        return mSP.getLong(key, value);
    }
    protected void putLong(String key, long value){
        mSP.edit().putLong(key, value).apply();
    }

    protected String getString(String key, String value){
        return mSP.getString(key, value);
    }
    protected void putString(String key, String value){
        mSP.edit().putString(key, value).apply();
    }
}
