package com.wuruoye.library.util;

import android.content.Context;

/**
 * Created by wuruoye on 2018/2/25.
 * this file is to
 */

public class StatusBarUtil {
    public static int getStatusHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
