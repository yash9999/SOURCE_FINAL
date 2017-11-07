package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class EPHomePresenterImpl implements EPHomePresenter {

    Activity activity;
    EPHomeView epHomeView;
    JSONObject jsonObject;

    public EPHomePresenterImpl(Activity activity, EPHomeView epHomeView) {
        this.activity = activity;
        this.epHomeView = epHomeView;
    }

    @Override
    public void callingExpiryProductHomeApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfExpiryProductApi();
        } catch (ApiAdapter.NoInternetException ex) {
            epHomeView.onInternetError();
        }
    }

    private void gettingResultOfExpiryProductApi() {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ExpiryProductHomeModel> getResultOfProductApi = ApiAdapter.getApiService().expiryProductHome("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ExpiryProductHomeModel>() {
            @Override
            public void onResponse(Call<ExpiryProductHomeModel> call, Response<ExpiryProductHomeModel> response) {

                Progress.stop();
                try {
                    ExpiryProductHomeModel priceSurveyProductModel = response.body();

                    if (priceSurveyProductModel.getSuccessful()) {
                        epHomeView.onSuccess(priceSurveyProductModel.getResult());
                    } else {
                        epHomeView.onUnsuccess(priceSurveyProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    epHomeView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ExpiryProductHomeModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                epHomeView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });

    }
}
