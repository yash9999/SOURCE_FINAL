package com.example.yogeshgarg.source.mvp.price_survey_product;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 21/07/17.
 */

public class PriceSurveyProductPresenterImpl implements PriceSurveyProductPresenter {

    Activity activity;
    PriceSurveyProductView priceSurveyProductView;
    JSONObject jsonObject;

    public PriceSurveyProductPresenterImpl(Activity activity, PriceSurveyProductView priceSurveyProductView) {
        this.activity = activity;
        this.priceSurveyProductView = priceSurveyProductView;
    }

    @Override
    public void callingPriceSurveyProductApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(categoryId)) {
                gettingResultOfProductApi(categoryId);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            priceSurveyProductView.onInternetErrorPriceSurveyProduct();
        }
    }

    private void gettingResultOfProductApi(String categoryId) {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KAY_CATEGORY_ID, categoryId);
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<PriceSurveyProductModel> getResultOfProductApi = ApiAdapter.getApiService().gettingResultOfProduct("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<PriceSurveyProductModel>() {
            @Override
            public void onResponse(Call<PriceSurveyProductModel> call, Response<PriceSurveyProductModel> response) {

                Progress.stop();
                try {
                    PriceSurveyProductModel priceSurveyProductModel = response.body();


                    if (priceSurveyProductModel.getSuccessful()) {
                        priceSurveyProductView.onSuccessPriceSurveyProduct(priceSurveyProductModel.getResult());
                    } else {
                        priceSurveyProductView.onUnsuccessPriceSurveyProduct(activity.getString(R.string.user_is_not_authentic));
                    }
                } catch (NullPointerException exp) {
                    priceSurveyProductView.onUnsuccessPriceSurveyProduct(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<PriceSurveyProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                priceSurveyProductView.onUnsuccessPriceSurveyProduct(activity.getString(R.string.server_error));
            }
        });
    }

    private boolean validation(String categoryId) {
        if (Utils.isEmptyOrNull(categoryId)) {
            return false;
        }
        return true;
    }
}
