package com.example.yogeshgarg.source.mvp.about;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public class AboutPresenterImpl implements AboutPresenter {

    Activity activity;
    AboutView aboutView;
    JSONObject jsonObject;

    public AboutPresenterImpl(Activity activity, AboutView aboutView) {
        this.aboutView = aboutView;
        this.activity = activity;
    }

    @Override
    public void callingAboutApi() {
        try {
            ApiAdapter.getInstance(activity);
            getResult();
        } catch (ApiAdapter.NoInternetException ex) {
            aboutView.onInternetError();
        }
    }

    private void getResult() {
        Progress.start(activity);
        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<AboutModel> getResult = ApiAdapter.getApiService().resultAboutUS("application/json", "no-cache", body);

        getResult.enqueue(new Callback<AboutModel>() {
            @Override
            public void onResponse(Call<AboutModel> call, Response<AboutModel> response) {

                Progress.stop();
                try {
                    AboutModel aboutModel = response.body();

                    if (aboutModel.getSuccessful()) {
                        aboutView.onSuccess(aboutModel.getResult());
                    } else {
                        aboutView.onUnsuccess(aboutModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    aboutView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<AboutModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                aboutView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }
}
