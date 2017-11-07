package com.example.yogeshgarg.source.mvp.notification;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 14/09/17.
 */

public interface NotificationPushView {
    public void onSuccess(ArrayList<NotificationPushModel.Result> resultArrayList);
    public void onUnsuccess(String message);
    public void onInternetError();


    public void onSuccessMarkRead(int position);
    public void onUnsuccessMarkRead(String message);
    public void onInternetErrorMark();
}
