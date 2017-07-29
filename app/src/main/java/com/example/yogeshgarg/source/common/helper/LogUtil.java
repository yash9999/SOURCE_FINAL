package com.example.yogeshgarg.source.common.helper;

import android.support.compat.BuildConfig;
import android.util.Log;


/**
 * Created by Braintech on 23-03-2017.
 */

public class LogUtil {
    public static final String TAG = "Gyadda";

    public static void v(String msg) {
        if (BuildConfig.DEBUG)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, msg);
    }


}
