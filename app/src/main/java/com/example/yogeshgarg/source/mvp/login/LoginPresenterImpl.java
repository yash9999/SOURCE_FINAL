package com.example.yogeshgarg.source.mvp.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;


import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.FcmSession;
import com.example.yogeshgarg.source.common.session.UserSession;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 06/06/17.
 */

public class LoginPresenterImpl implements LoginPresenter {

    Activity activity;
    LoginView loginView;
    JSONObject jsonObject;

    public LoginPresenterImpl(Activity activity, LoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
    }

    @Override
    public void fetchingLoginApiData(String username, String password) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(username, password)) {
                callingRegisterationApi(username, password);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            loginView.internetError();
        }
    }


    private boolean validation(String username, String password) {

        if (Utils.isEmptyOrNull(username)) {
            loginView.onLoginUnsuccess(activity.getString(R.string.username_error));
            return false;
        } else if (Utils.isEmptyOrNull(password)) {
            loginView.onLoginUnsuccess(activity.getString(R.string.password_error));
            return false;
        }
        return true;
    }

    private void callingRegisterationApi(final String username, final String password) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_USERNAME, username);
            jsonObject.put(Const.KEY_PASSWORD, password);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<LoginModel> getRegisterMessage = ApiAdapter.getApiService().userLogin("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                Progress.stop();
                try {
                    LoginModel loginModel = response.body();

                    if (loginModel.getSuccessful()) {
                        loginView.onLoginSuccess(loginModel.getResult());
                    } else {
                        loginView.onLoginUnsuccess(loginModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    loginView.onLoginUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                loginView.onLoginUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void callingPushNotificationApi() {
        try {
            ApiAdapter.getInstance(activity);
            sendingToken();
        } catch (ApiAdapter.NoInternetException ex) {
            loginView.internetError();
        }
    }

    private void sendingToken() {
        Progress.start(activity);

        String device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        FcmSession fcmSession = new FcmSession(activity);
        String token = fcmSession.getFcmToken();

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_DEVICE_ID, device_id);
            jsonObject.put(Const.KEY_FIREBASE_TOKEN, token);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ResponseBody> getRegisterMessage = ApiAdapter.getApiService().generateToken("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Progress.stop();
                loginView.onPushSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                loginView.onLoginUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }









}
