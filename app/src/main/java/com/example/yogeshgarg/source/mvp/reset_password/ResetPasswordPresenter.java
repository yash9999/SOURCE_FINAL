package com.example.yogeshgarg.source.mvp.reset_password;

/**
 * Created by yogeshgarg on 16/07/17.
 */

public interface ResetPasswordPresenter {

    public void callingResetPasswordApi(String currentPassword,String password,String confirmPassword);
}
