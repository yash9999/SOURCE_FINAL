package com.example.yogeshgarg.source.mvp.profile;

/**
 * Created by yogeshgarg on 12/08/17.
 */

public interface ProfileView {
    public void onSuccessProfile(ProfileModel.Result result);

    public void onUnsuccessProfile(String message);

    public void onInternetError();

    public void onSuccessProfilePic(String message);

    public void onUnsuccessProfilePic(String message);

    public void onInternetErrorProfilePic();


    public void onSuccessProfileInfo();

    public void onUnsuccessProfileInfo(String message);

    public void onInternertErrorProfileInfo();

}
