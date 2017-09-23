package com.example.yogeshgarg.source.mvp.forgot_password;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordModel;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 11/07/17.
 */

public class ForgotPasswordPresenterImpl implements ForgorPasswordPresenter {

    Activity activity;
    ForgotPasswordView forgotPasswordView;
    JSONObject jsonObject;

    public ForgotPasswordPresenterImpl(Activity activity, ForgotPasswordView forgotPasswordView) {
        this.activity = activity;
        this.forgotPasswordView = forgotPasswordView;
    }

    @Override
    public void callingForgotPasswordApi(String emailId) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(emailId)) {
                gettingResultOfForgotPassword(emailId);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            forgotPasswordView.onInternetErrorForgotPassword();
        }
    }


    private void gettingResultOfForgotPassword(String emailId) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_EMAIL_ID, emailId);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ForgotPasswordModel> forgotPasswordModelCall = ApiAdapter.getApiService().forgotPassword("application/json", "no-cache", body);

        forgotPasswordModelCall.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {

                Progress.stop();
                try {
                    ForgotPasswordModel forgotPasswordModel = response.body();
                    String message = forgotPasswordModel.getMessage();
                    if (forgotPasswordModel.getSuccessful()) {
                        forgotPasswordView.onSuccessForgotPassword(message);
                    } else {
                        forgotPasswordView.onUnsuccessForgotPassword(message);
                    }
                } catch (NullPointerException exp) {
                    forgotPasswordView.onUnsuccessForgotPassword(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                forgotPasswordView.onUnsuccessForgotPassword(activity.getString(R.string.server_error));
            }
        });
    }

    private boolean validation(String emailId) {
        if (Utils.isEmptyOrNull(emailId)) {
            forgotPasswordView.onUnsuccessForgotPassword(activity.getString(R.string.empty_emailId));
            return false;
        } else if (!Utils.isValidEmail(emailId)) {
            forgotPasswordView.onUnsuccessForgotPassword(activity.getString(R.string.valid_emailId_error_msg));
            return false;
        }
        return true;
    }




}
