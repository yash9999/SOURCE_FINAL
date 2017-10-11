package com.example.yogeshgarg.source.mvp.faq;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.mvp.about.AboutModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public class FAQPresenterImpl implements FAQPresenter {
    Activity activity;
    FAQView faqView;
    JSONObject jsonObject;

    public FAQPresenterImpl(Activity activity, FAQView faqView) {
        this.activity = activity;
        this.faqView = faqView;
    }

    @Override
    public void callingFaq() {
        try {
            ApiAdapter.getInstance(activity);
            getResult();
        } catch (ApiAdapter.NoInternetException ex) {
            faqView.onInternetError();
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

        Call<FAQModel> getResult = ApiAdapter.getApiService().resultFAQ("application/json", "no-cache", body);

        getResult.enqueue(new Callback<FAQModel>() {
            @Override
            public void onResponse(Call<FAQModel> call, Response<FAQModel> response) {

                Progress.stop();
                try {
                    FAQModel faqModel = response.body();

                    if (faqModel.getSuccessful()) {
                        faqView.onSuccessFaqApi(faqModel.getResult());
                    } else {
                        faqView.onUnsuccess(faqModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    faqView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<FAQModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                faqView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }
}
