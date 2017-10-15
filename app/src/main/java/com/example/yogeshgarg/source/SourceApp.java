package com.example.yogeshgarg.source;

import android.app.Application;
import android.content.Context;

import com.example.yogeshgarg.source.common.quickblox.QuickbloxSession;

/**
 * Created by himanshu on 29/07/17.
 */

public class SourceApp extends Application {


    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        QuickbloxSession quickbloxSession=new QuickbloxSession();
        quickbloxSession.startSession(getApplicationContext());
    }


    public static Context getInstance() {
        return instance;
    }
}

