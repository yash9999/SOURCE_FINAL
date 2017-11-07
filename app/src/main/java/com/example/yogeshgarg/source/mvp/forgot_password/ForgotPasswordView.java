package com.example.yogeshgarg.source.mvp.forgot_password;

/**
 * Created by yogeshgarg on 11/07/17.
 */

public interface ForgotPasswordView {
    public void onSuccessForgotPassword(String message);
    public void onUnsuccessForgotPassword(String message);
    public void onInternetErrorForgotPassword();
}
