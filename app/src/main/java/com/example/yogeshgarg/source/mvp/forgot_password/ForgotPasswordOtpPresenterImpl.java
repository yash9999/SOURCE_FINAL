package com.example.yogeshgarg.source.mvp.forgot_password;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 12/08/17.
 */

public class ForgotPasswordOtpPresenterImpl implements ForgotPasswordOtpPresenter {


    Activity activity;
    ForgotPasswordOtpView forgotPasswordOtpView;
    JSONObject jsonObject = null;

    public ForgotPasswordOtpPresenterImpl(Activity activity, ForgotPasswordOtpView forgotPasswordOtpView) {
        this.activity = activity;
        this.forgotPasswordOtpView = forgotPasswordOtpView;
    }

    @Override
    public void callingForgotPasswordOtpApi(String otp, String password, String confirmPassword) {

        try {
            ApiAdapter.getInstance(activity);
            if (validation(otp, password, confirmPassword)) {
                gettingResultOfForgotPasswordOtp(otp, password);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            forgotPasswordOtpView.onInternetErrorForgotPasswordApi();
        }
    }


    private void gettingResultOfForgotPasswordOtp(String otp, String password) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_OTP, otp);
            jsonObject.put(Const.KEY_PASSWORD, password);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ForgotPasswordOtpModel> forgotPasswordOtpModelCall = ApiAdapter.getApiService().forgotPasswordOtp("application/json", "no-cache", body);

        forgotPasswordOtpModelCall.enqueue(new Callback<ForgotPasswordOtpModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordOtpModel> call, Response<ForgotPasswordOtpModel> response) {

                Progress.stop();
                try {
                    ForgotPasswordOtpModel forgotPasswordOtpModel = response.body();
                    String message = forgotPasswordOtpModel.getMessage();
                    if (forgotPasswordOtpModel.getSuccessful()) {
                        forgotPasswordOtpView.onSuccessForgotPasswordOtp(message);
                    } else {
                        forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(message);
                    }
                } catch (NullPointerException exp) {
                    forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordOtpModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(activity.getString(R.string.server_error));
            }
        });
    }

    private boolean validation(String otp, String password, String confirmpassword) {

        if (Utils.isEmptyOrNull(otp)) {
            forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(activity.getString(R.string.otp_empty));
            return false;
        } else if (Utils.isEmptyOrNull(password)) {
            forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(activity.getString(R.string.password_empty));
            return false;
        } else if (!Utils.matchString(password)) {
            forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(activity.getString(R.string.password_valid));
            return false;
        } else if (!confirmpassword.equals(password)) {
            forgotPasswordOtpView.onUnsuccessForgotPasswordOtp(activity.getString(R.string.confirm_password_error_msg));
            return false;
        }
        return true;
    }
}
