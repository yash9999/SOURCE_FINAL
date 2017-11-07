package com.example.yogeshgarg.source.mvp.notification_act;

/**
 * Created by yogeshgarg on 15/09/17.
 */

public interface NotificationSettingPresenter {

    public void callingNotificationUpdateSettingApi(String sampling,String expiry,String newProduct,String notification);

    public void callingNotificationUpdateApi();
}
