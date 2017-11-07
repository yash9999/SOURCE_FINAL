package com.example.yogeshgarg.source.mvp.about;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public interface AboutView {
    public void onSuccess(AboutModel.Result result);
    public void onUnsuccess(String message);
    public void onInternetError();

}
