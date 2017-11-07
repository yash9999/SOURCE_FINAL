package com.example.yogeshgarg.source.mvp.notification;

/**
 * Created by yogeshgarg on 14/09/17.
 */

public interface NotificationPushPresenter {
    public void callingNotificationApi();
    public void callingMarkReadNotification(String messageId,int position);
}
