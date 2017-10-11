package com.example.yogeshgarg.source.mvp.in_store_sampling.store_category;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public class StoreCategoryPresenterImpl implements StoreCategoryPresenter {

    Activity activity;
    StoreCategoryView storeCategoryView;
    JSONObject jsonObject = null;

    public StoreCategoryPresenterImpl(Activity activity, StoreCategoryView storeCategoryView) {
        this.activity = activity;
        this.storeCategoryView = storeCategoryView;
    }

    @Override
    public void callingStoreCategoryApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfCategoryApi();
        } catch (ApiAdapter.NoInternetException ex) {
            storeCategoryView.onInternetError();
        }
    }

    private void gettingResultOfCategoryApi() {
        Progress.start(activity);

        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
            jsonObject.put(Const.KEY_PUBLISH,1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<StoreCategoryModel> getEP_CategoryList = ApiAdapter.getApiService().resultOfStoreCategory("application/json", "no-cache", body);

        getEP_CategoryList.enqueue(new Callback<StoreCategoryModel>() {
            @Override
            public void onResponse(Call<StoreCategoryModel> call, Response<StoreCategoryModel> response) {

                Progress.stop();
                try {
                    StoreCategoryModel storeCategoryModel = response.body();

                    String message = storeCategoryModel.getMessage();
                    if (storeCategoryModel.getSuccessful()) {
                        storeCategoryView.onSuccess(storeCategoryModel.getResult());
                    } else {
                        storeCategoryView.onUnsuccess(message);
                    }
                } catch (NullPointerException exp) {
                    storeCategoryView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<StoreCategoryModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                storeCategoryView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }

}
