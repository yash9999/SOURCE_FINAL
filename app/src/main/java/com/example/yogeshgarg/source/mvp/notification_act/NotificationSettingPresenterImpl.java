package com.example.yogeshgarg.source.mvp.notification_act;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.login.LoginModel;
import com.example.yogeshgarg.source.mvp.notification.NotificationPushPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 15/09/17.
 */

public class NotificationSettingPresenterImpl implements NotificationSettingPresenter {

    Activity activity;
    NotificationSettingView notificationSettingView;
    JSONObject jsonObject;

    public NotificationSettingPresenterImpl(Activity activity, NotificationSettingView notificationSettingView) {
        this.activity = activity;
        this.notificationSettingView = notificationSettingView;
    }

    @Override
    public void callingNotificationUpdateSettingApi(String sampling, String expiry, String newProduct, String notification) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfUpdateNotificationSetting(sampling, expiry, newProduct, notification);
        } catch (ApiAdapter.NoInternetException ex) {
            notificationSettingView.onInternetErrorUpdate();
        }
    }

    public void gettingResultOfUpdateNotificationSetting(String sampling, String expiry, String newProduct, String notification) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_NOTIFICATION_SAMPLING, sampling);
            jsonObject.put(Const.KEY_NOTIFICATION_EXPIRY, expiry);
            jsonObject.put(Const.KEY_NOTIFICATION_NEWPRODUCT, newProduct);
            jsonObject.put(Const.KEY_NOTIFICATION_Notification, notification);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<NotificationSettingUpdateModel> getResultForUpdatingSetting = ApiAdapter.getApiService().pushNotificSetting("application/json", "no-cache", body);

        getResultForUpdatingSetting.enqueue(new Callback<NotificationSettingUpdateModel>() {
            @Override
            public void onResponse(Call<NotificationSettingUpdateModel> call, Response<NotificationSettingUpdateModel> response) {

                Progress.stop();
                try {
                    NotificationSettingUpdateModel notificationSettingUpdateModel = response.body();

                    if (notificationSettingUpdateModel.getSuccessful()) {
                        notificationSettingView.onSuccessNotificationUpdate();
                    } else {
                        notificationSettingView.onUnscuuessNotificationUpdate(notificationSettingUpdateModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    notificationSettingView.onUnscuuessNotificationUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<NotificationSettingUpdateModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                notificationSettingView.onUnscuuessNotificationUpdate(activity.getString(R.string.server_error));
            }
        });
    }

    @Override
    public void callingNotificationUpdateApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfNotification();
        } catch (ApiAdapter.NoInternetException ex) {
            notificationSettingView.onInternetError();
        }
    }

    public void gettingResultOfNotification() {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<NotificationSettingModel> getRegisterMessage = ApiAdapter.getApiService().pushNotificationUpdate("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<NotificationSettingModel>() {
            @Override
            public void onResponse(Call<NotificationSettingModel> call, Response<NotificationSettingModel> response) {

                Progress.stop();
                try {
                    NotificationSettingModel notificationSettingModel = response.body();

                    if (notificationSettingModel.getSuccessful()) {
                        notificationSettingView.onSuccessNotification(notificationSettingModel.getResult());
                    } else {
                        notificationSettingView.onUnsuccessNotification(notificationSettingModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    notificationSettingView.onUnsuccessNotification(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<NotificationSettingModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                notificationSettingView.onUnsuccessNotification(activity.getString(R.string.server_error));
            }
        });

    }
}
