package com.wuruoye.library.util.permission;

import android.Manifest;

/**
 * @Created : wuruoye
 * @Date : 2018/3/22.
 * @Decription : 常用的需要动态申请的权限
 */

public class PermissionConfig {
    public static final String[] PERMISSION_FILE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final String[] PERMISSION_CAMARE = {
            Manifest.permission.CAMERA
    };

    public static final String[] PERMISSION_LOCATION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };
}
