package com.wuruoye.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;

import com.wuruoye.library.R;


/**
 * Created by wuruoye on 2018/2/25.
 * this file is to
 */

public class ResourceUtil {
    public static String getString(Context context, int resourceId) {
        return context.getResources().getString(resourceId);
    }

    public static int getColor(Context context, int resourceId) {
        return ActivityCompat.getColor(context, resourceId);
    }

    public static Drawable getDrawable(Context context, int resourceId) {
        return ActivityCompat.getDrawable(context, resourceId);
    }

    public static Bitmap getBitmap(Context context, int resourceId) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public static int getColorPrimary(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static int getColorPrimaryDark(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        return value.data;
    }

    public static int getColorAccent(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }
}
