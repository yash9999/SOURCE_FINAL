package com.example.yogeshgarg.source;

import android.app.Application;
import android.content.Context;

/**
 * Created by himanshu on 29/07/17.
 */

public class SourceApp extends Application {


    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }


    public static Context getInstance() {
        return instance;
    }
}

