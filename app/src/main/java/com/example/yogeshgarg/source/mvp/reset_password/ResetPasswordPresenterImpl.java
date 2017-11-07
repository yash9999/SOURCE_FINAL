package com.example.yogeshgarg.source.mvp.reset_password;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.login.LoginModel;



import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by yogeshgarg on 16/07/17.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter {

    Activity activity;
    ResetPasswordView resetPasswordView;
    JSONObject jsonObject;

    public ResetPasswordPresenterImpl(Activity activity, ResetPasswordView resetPasswordView) {
        this.activity = activity;
        this.resetPasswordView = resetPasswordView;

    }

    @Override
    public void callingResetPasswordApi(String currentPassword, String password, String confirmPassword) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(currentPassword, password, confirmPassword)) {
                gettingResultOfResetPasswordApi(currentPassword, password);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            resetPasswordView.onInternetError();
        }
    }

    private void gettingResultOfResetPasswordApi(String currentPassword, String password) {

        Progress.start(activity);

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_CURRENT_PASSWORD, currentPassword);
            jsonObject.put(Const.KEY_PASSWORD, password);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ResetPasswordModel> getResultOfResetPassword = ApiAdapter.getApiService().getResetResult("application/json", "no-cache", body);

        getResultOfResetPassword.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {

                Progress.stop();
                try {
                    ResetPasswordModel resetPasswordModel = response.body();

                    if (resetPasswordModel.getSuccessful()) {
                        resetPasswordView.onSuccessResetPassword(resetPasswordModel.getMessage());
                    } else {
                        resetPasswordView.onUnsuccessResetPassword(resetPasswordModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    resetPasswordView.onUnsuccessResetPassword(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                resetPasswordView.onUnsuccessResetPassword(activity.getString(R.string.server_error));
            }
        });

    }

    private boolean validation(String currentPassword, String password, String confirmPassword) {
        if (Utils.isEmptyOrNull(currentPassword)) {
            resetPasswordView.onUnsuccessResetPassword(activity.getString(R.string.old_password_empty));
            return false;
        } else if (Utils.isEmptyOrNull(password)) {
            resetPasswordView.onUnsuccessResetPassword(activity.getString(R.string.password_empty));
            return false;
        } else if (!Utils.matchString(password)) {
            resetPasswordView.onUnsuccessResetPassword(activity.getString(R.string.password_valid));
            return false;
        } else if (!confirmPassword.equals(password)) {
            resetPasswordView.onUnsuccessResetPassword(activity.getString(R.string.confirm_password_error_msg));
            return false;
        }
        return true;
    }
}
