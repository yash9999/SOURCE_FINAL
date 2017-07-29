package com.example.yogeshgarg.source.mvp.reset_password;

import android.app.Activity;

/**
 * Created by yogeshgarg on 16/07/17.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter {

    Activity activity;
    ResetPasswordView resetPasswordView;

    public ResetPasswordPresenterImpl(Activity activity, ResetPasswordView resetPasswordView) {
        this.activity = activity;
        this.resetPasswordView = resetPasswordView;

    }

    @Override
    public void callingResetPasswordApi(String password,String rePassword) {

    }
}
