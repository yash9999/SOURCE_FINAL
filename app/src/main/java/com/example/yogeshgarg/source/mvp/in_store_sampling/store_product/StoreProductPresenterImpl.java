package com.example.yogeshgarg.source.mvp.in_store_sampling.store_product;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product.ExpiryProduct_ProductModel;
import com.example.yogeshgarg.source.mvp.stores.StoresPresenter;
import com.example.yogeshgarg.source.mvp.stores.StoresView;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public class StoreProductPresenterImpl implements StoreProductPresenter {

    Activity activity;
    StoreProductView storeProductView;
    JSONObject jsonObject;

    public StoreProductPresenterImpl(Activity activity, StoreProductView storeProductView) {
        this.activity = activity;
        this.storeProductView = storeProductView;
    }

    private void gettingResultOfProductApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfProductApi(categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            storeProductView.onInternetError();
        }
    }

    @Override
    public void callingStoreProductApi(String categoryId) {

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

        Call<StoreProductModel> getResultOfProductApi = ApiAdapter.getApiService().inStoreProduct("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<StoreProductModel>() {
            @Override
            public void onResponse(Call<StoreProductModel> call, Response<StoreProductModel> response) {

                Progress.stop();
               try {
                   StoreProductModel storeProductModel = response.body();


                    if (storeProductModel.getSuccessful()) {
                        storeProductView.onSuccess(storeProductModel.getResult());
                    } else {
                        storeProductView.onUnsuccess(storeProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    storeProductView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<StoreProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                storeProductView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }
}

