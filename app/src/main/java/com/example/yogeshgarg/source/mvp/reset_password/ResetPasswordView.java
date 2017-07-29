package com.example.yogeshgarg.source.mvp.reset_password;

/**
 * Created by yogeshgarg on 16/07/17.
 */

public interface ResetPasswordView {

    public void onSuccessResetPassword();
    public void onUnsuccessResetPassword(String message);
    public void onInternetError();
}
