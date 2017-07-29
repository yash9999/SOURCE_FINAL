package com.example.yogeshgarg.source.mvp.price_survey;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.stores.StoresModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 20/07/17.
 */

public class PriceSurveyPresenterImpl implements PriceSurveyPresenter {

    Activity activity;
    PriceSurveyView priceSurveyView;
    JSONObject jsonObject;

    public PriceSurveyPresenterImpl(Activity activity, PriceSurveyView priceSurveyView) {
        this.priceSurveyView = priceSurveyView;
        this.activity = activity;
    }

    @Override
    public void callingCategoryApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfCategoryApi();
        } catch (ApiAdapter.NoInternetException ex) {
            priceSurveyView.onInternetError();
        }
    }

    private void gettingResultOfCategoryApi() {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<PriceSurveyModel> getPriceSurveyCategoryList = ApiAdapter.getApiService().priceSurveyCategoryList("application/json", "no-cache", body);

        getPriceSurveyCategoryList.enqueue(new Callback<PriceSurveyModel>() {
            @Override
            public void onResponse(Call<PriceSurveyModel> call, Response<PriceSurveyModel> response) {

                Progress.stop();
                try {
                    PriceSurveyModel priceSurveyModel = response.body();


                    if (priceSurveyModel.getSuccessful()) {
                        priceSurveyView.onSuccessCategory(priceSurveyModel.getResult());
                    } else {
                        priceSurveyView.onUnsccuessCategory(activity.getString(R.string.user_is_not_authentic));
                    }
                } catch (NullPointerException exp) {
                    priceSurveyView.onUnsccuessCategory(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<PriceSurveyModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                priceSurveyView.onUnsccuessCategory(activity.getString(R.string.server_error));
            }
        });
    }


}
