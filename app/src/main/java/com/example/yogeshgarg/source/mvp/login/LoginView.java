package com.example.yogeshgarg.source.mvp.login;

/**
 * Created by yogeshgarg on 06/06/17.
 */

public interface LoginView {
    public void onLoginSuccess(LoginModel.Result result);
    public void onLoginUnsuccess(String message);
    public void internetError();

    public void onPushSuccess();
    public void onPushUnsuccess(String message);
    public void internetErrorPush();

}
