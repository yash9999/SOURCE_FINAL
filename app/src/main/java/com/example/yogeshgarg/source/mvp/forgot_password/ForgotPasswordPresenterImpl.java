package com.example.yogeshgarg.source.mvp.forgot_password;

import android.app.Activity;

/**
 * Created by yogeshgarg on 11/07/17.
 */

public class ForgotPasswordPresenterImpl implements ForgorPasswordPresenter {

    Activity activity;
    ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenterImpl(Activity activity,ForgotPasswordView forgotPasswordView){
        this.activity=activity;
        this.forgotPasswordView=forgotPasswordView;
    }

    @Override
    public void callingForgotPasswordApi(String emailId) {

    }
}
