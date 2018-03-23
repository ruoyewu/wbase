package com.wuruoye.library.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.wuruoye.library.ui.WBaseApp;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to the base cache manager class
 */

public abstract class WBaseCache {
    private static String SP_NAME = "sp_name";

    static void init(String name) {
        SP_NAME = name;
    }

    private SharedPreferences mSP;

    public WBaseCache() {
        if (mSP == null){
            synchronized (this){
                mSP = WBaseApp.getApp().getSharedPreferences(SP_NAME,
                        Context.MODE_PRIVATE);
            }
        }
    }

    public WBaseCache(String name){
        if (mSP == null){
            synchronized (this){
                mSP = WBaseApp.getApp().getSharedPreferences(SP_NAME + "_" + name,
                        Context.MODE_PRIVATE);
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

    protected float getFloat(String key, float value) {
        return mSP.getFloat(key, value);
    }
    protected void putFloat(String key, float value) {
        mSP.edit().putFloat(key, value).apply();
    }

    public void clear() {
        mSP.edit().clear().apply();
    }

    public void remove(String key) {
        mSP.edit().remove(key).apply();
    }
}
