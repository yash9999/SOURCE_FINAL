package com.example.yogeshgarg.source.mvp.notification;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_category.StoreCategoryModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 14/09/17.
 */

public class NotificationPushPresenterImpl implements NotificationPushPresenter {

    Activity activity;
    NotificationPushView notificationPushView;
    JSONObject jsonObject;

    public NotificationPushPresenterImpl(Activity activity, NotificationPushView notificationPushView) {
        this.activity = activity;
        this.notificationPushView = notificationPushView;
    }

    @Override
    public void callingNotificationApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfPushNotification();
        } catch (ApiAdapter.NoInternetException ex) {
            notificationPushView.onInternetError();
        }
    }


    private void gettingResultOfPushNotification() {
        Progress.start(activity);

        String section = "5";
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_SECTION, section);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<NotificationPushModel> getNotification = ApiAdapter.getApiService().pushNotification("application/json", "no-cache", body);

        getNotification.enqueue(new Callback<NotificationPushModel>() {
            @Override
            public void onResponse(Call<NotificationPushModel> call, Response<NotificationPushModel> response) {

                Progress.stop();
                try {
                    NotificationPushModel notificationPushModel = response.body();

                    String message = notificationPushModel.getMessage();
                    if (notificationPushModel.getSuccessful()) {
                        notificationPushView.onSuccess(notificationPushModel.getResult());
                    } else {
                        notificationPushView.onUnsuccess(message);
                    }
                } catch (NullPointerException exp) {
                    notificationPushView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<NotificationPushModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                notificationPushView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void callingMarkReadNotification(String messageId, int position) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfMarkReadNotification(messageId, position);
        } catch (ApiAdapter.NoInternetException ex) {
            notificationPushView.onInternetError();
        }
    }


    private void gettingResultOfMarkReadNotification(String messageId, final int position) {
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_MESSAGE_ID, messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<MarkReadModel> getNotification = ApiAdapter.getApiService().markRead("application/json", "no-cache", body);

        getNotification.enqueue(new Callback<MarkReadModel>() {
            @Override
            public void onResponse(Call<MarkReadModel> call, Response<MarkReadModel> response) {
                try {
                    MarkReadModel markReadModel = response.body();

                    String message = markReadModel.getMessage();
                    if (markReadModel.getSuccessful()) {
                        notificationPushView.onSuccessMarkRead(position);
                    } else {
                        notificationPushView.onUnsuccess(message);
                    }
                } catch (NullPointerException exp) {
                    notificationPushView.onUnsuccessMarkRead(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<MarkReadModel> call, Throwable t) {
                t.printStackTrace();
                notificationPushView.onUnsuccessMarkRead(activity.getString(R.string.server_error));
            }
        });
    }

}
