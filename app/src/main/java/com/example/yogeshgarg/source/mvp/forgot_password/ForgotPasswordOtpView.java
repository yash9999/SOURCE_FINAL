package com.example.yogeshgarg.source.mvp.forgot_password;

/**
 * Created by yogeshgarg on 12/08/17.
 */

public interface ForgotPasswordOtpView {
    public void onSuccessForgotPasswordOtp(String message);
    public void onUnsuccessForgotPasswordOtp(String message);
    public void onInternetErrorForgotPasswordApi();
}
