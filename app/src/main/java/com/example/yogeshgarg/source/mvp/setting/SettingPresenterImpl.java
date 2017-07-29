package com.example.yogeshgarg.source.mvp.setting;

import android.app.Activity;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public class SettingPresenterImpl implements SettingPresenter {
    Activity activity;
    SettingView settingView;

    public SettingPresenterImpl(Activity activity, SettingView settingView) {
        this.activity = activity;
        this.settingView = settingView;
    }
}
