package com.wuruoye.library.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.ui.WBaseApp;

/**
 * Created by wuruoye on 2018/2/26.
 * this file is to
 */

public class AppUtil {
    public static int getVersionCode() throws PackageManager.NameNotFoundException {
        PackageInfo info = WBaseApp.getApp().getPackageManager()
                .getPackageInfo(WConfig.PACKAGE_NAME, 0);
        return info.versionCode;
    }

    public static String getVersionName() throws PackageManager.NameNotFoundException {
        PackageInfo info = WBaseApp.getApp().getPackageManager()
                .getPackageInfo(WConfig.PACKAGE_NAME, 0);
        return info.versionName;
    }

    public static int getSystemCode() {
        return Build.VERSION.SDK_INT;
    }
}
