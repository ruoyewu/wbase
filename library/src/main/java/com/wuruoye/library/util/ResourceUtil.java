package com.wuruoye.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;


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

}
