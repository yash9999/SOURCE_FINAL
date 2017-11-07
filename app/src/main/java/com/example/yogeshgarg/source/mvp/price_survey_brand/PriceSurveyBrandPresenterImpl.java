package com.example.yogeshgarg.source.mvp.price_survey_brand;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 03/10/17.
 */

public class PriceSurveyBrandPresenterImpl implements PriceSurveyBrandPresenter {

    Activity activity;
    PriceSurveyBrandView priceSurveyBrandView;
    JSONObject jsonObject;

    public PriceSurveyBrandPresenterImpl(Activity activity,PriceSurveyBrandView priceSurveyBrandView){
        this.activity=activity;
        this.priceSurveyBrandView=priceSurveyBrandView;
    }

    @Override
    public void callingPSBrandApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfBrand(categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            priceSurveyBrandView.onInternetError();
        }
    }

    private void gettingResultOfBrand(String categoryId) {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
            jsonObject.put(Const.KAY_CATEGORY_ID, categoryId);
            jsonObject.put(Const.KEY_PUBLISH,1);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<PriceSurveyBrandModel> getResultOfProductApi = ApiAdapter.getApiService().priceSurveyBrandList("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<PriceSurveyBrandModel>() {
            @Override
            public void onResponse(Call<PriceSurveyBrandModel> call, Response<PriceSurveyBrandModel> response) {

                Progress.stop();
                try {
                    PriceSurveyBrandModel priceSurveyBrandModel = response.body();

                    if (priceSurveyBrandModel.getSuccessful()) {
                        priceSurveyBrandView.onSuccessPLBrand(priceSurveyBrandModel.getResult());
                    } else {
                        priceSurveyBrandView.onUnsuccessPLBrand(priceSurveyBrandModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    priceSurveyBrandView.onUnsuccessPLBrand(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<PriceSurveyBrandModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                priceSurveyBrandView.onUnsuccessPLBrand(activity.getString(R.string.server_error));
            }
        });
    }
}
