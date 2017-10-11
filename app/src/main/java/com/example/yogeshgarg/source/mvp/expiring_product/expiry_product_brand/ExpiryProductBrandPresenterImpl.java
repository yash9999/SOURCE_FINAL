package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand;

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
 * Created by yogeshgarg on 30/09/17.
 */

public class ExpiryProductBrandPresenterImpl implements ExpiryProductBrandPresenter {

    Activity activity;
    ExpiryProductBrandView expiryProductBrandView;
    JSONObject jsonObject;

    public ExpiryProductBrandPresenterImpl(Activity activity, ExpiryProductBrandView expiryProductBrandView) {
        this.activity = activity;
        this.expiryProductBrandView = expiryProductBrandView;
    }

    @Override
    public void callingEPBrandApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfBrand(categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            expiryProductBrandView.onInternetError();
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

        Call<ExpiryProductBrandModel> getResultOfProductApi = ApiAdapter.getApiService().resultOf_EP_Brand("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ExpiryProductBrandModel>() {
            @Override
            public void onResponse(Call<ExpiryProductBrandModel> call, Response<ExpiryProductBrandModel> response) {

                Progress.stop();
               try {
                   ExpiryProductBrandModel expiryProductBrandModel = response.body();

                    if (expiryProductBrandModel.getSuccessful()) {
                        expiryProductBrandView.onSuccessEPBrand(expiryProductBrandModel.getResult());
                    } else {
                        expiryProductBrandView.onUnsuccessEPBrand(expiryProductBrandModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    expiryProductBrandView.onUnsuccessEPBrand(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ExpiryProductBrandModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                expiryProductBrandView.onUnsuccessEPBrand(activity.getString(R.string.server_error));
            }
        });
    }
}
