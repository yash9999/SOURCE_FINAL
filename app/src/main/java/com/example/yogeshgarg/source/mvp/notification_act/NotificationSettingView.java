package com.example.yogeshgarg.source.mvp.notification_act;

/**
 * Created by yogeshgarg on 15/09/17.
 */

public interface NotificationSettingView {

    public void onSuccessNotificationUpdate();// for updating the setting

    public void onUnscuuessNotificationUpdate(String message);

    public void onInternetErrorUpdate();

    public void onSuccessNotification(NotificationSettingModel.Result result);

    public void onUnsuccessNotification(String message);

    public void onInternetError();


}
