package com.wuruoye.library.model;

import android.Manifest;
import android.content.Context;
import android.os.Environment;


/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the config class of the app
 */

public class WConfig {
    public static String PACKAGE_NAME;

    public static void init(Context context) {
        PACKAGE_NAME = context.getPackageName();
        APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + PACKAGE_NAME + "/";
        FILE_PATH = APP_PATH + "file/";
        IMAGE_PATH = APP_PATH + "image/";
        VIDEO_PATH = APP_PATH + "video/";
        RECORD_PATH = APP_PATH + "record/";
        PROVIDER_AUTHORITY = PACKAGE_NAME + ".fileprovider";
        WBaseCache.init(PACKAGE_NAME);
    }

    public static String APP_PATH;
    public static String FILE_PATH;
    public static String IMAGE_PATH;
    public static String VIDEO_PATH;
    public static String RECORD_PATH;
    public static String PROVIDER_AUTHORITY;

    // net related
    public static final int CONNECT_TIME_OUT = 30;

    // permission related
    public static final String[] FILE_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final String[] CAMERA_PERMISSION = {
            Manifest.permission.CAMERA
    };

    public static final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final String[] AUDIO_PERMISSION = {
            Manifest.permission.RECORD_AUDIO
    };
}
