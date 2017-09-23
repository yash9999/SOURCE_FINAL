package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryModel;
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

public class ExpiryProduct_ProductPresenterImpl implements ExpiryProduct_ProductPresenter {

    Activity activity;
    ExpiryProduct_ProductView expiryProductProductView;
    JSONObject jsonObject;

    public ExpiryProduct_ProductPresenterImpl(Activity activity, ExpiryProduct_ProductView expiryProductProductView) {
        this.activity = activity;
        this.expiryProductProductView = expiryProductProductView;
    }

    @Override
    public void callingExpiryProduct_ProductApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfProductApi(categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            expiryProductProductView.onInternetError();
        }
    }

    private void gettingResultOfProductApi(String categoryId) {
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

        Call<ExpiryProduct_ProductModel> getResultOfProductApi = ApiAdapter.getApiService().expiryProduct_Product("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ExpiryProduct_ProductModel>() {
            @Override
            public void onResponse(Call<ExpiryProduct_ProductModel> call, Response<ExpiryProduct_ProductModel> response) {

                Progress.stop();
                try {
                    ExpiryProduct_ProductModel expiryProductProductModel = response.body();


                    if (expiryProductProductModel.getSuccessful()) {
                        expiryProductProductView.onSuccess(expiryProductProductModel.getResult());
                    } else {
                        expiryProductProductView.onUnsuccess(expiryProductProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    expiryProductProductView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ExpiryProduct_ProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                expiryProductProductView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }
}
