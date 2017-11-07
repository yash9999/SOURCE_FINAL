package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class ExpiryProductPresenterImpl implements ExpiryProductPresenter {

    Activity activity;
    ExpiryProductView expiryProductView;
    JSONObject jsonObject = null;

    public ExpiryProductPresenterImpl(Activity activity, ExpiryProductView expiryProductView) {
        this.activity = activity;
        this.expiryProductView = expiryProductView;
    }

    @Override
    public void callingExpiryCategoryApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfCategoryApi();
        } catch (ApiAdapter.NoInternetException ex) {
            expiryProductView.onInternetErrorExpiryCategory();
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

        Call<ExpiryProductCategoryModel> getEP_CategoryList = ApiAdapter.getApiService().resultOf_EP_Category("application/json", "no-cache", body);

        getEP_CategoryList.enqueue(new Callback<ExpiryProductCategoryModel>() {
            @Override
            public void onResponse(Call<ExpiryProductCategoryModel> call, Response<ExpiryProductCategoryModel> response) {

                Progress.stop();
                try {
                    ExpiryProductCategoryModel expiryProductCategoryModel = response.body();

                    String message = expiryProductCategoryModel.getMessage();
                    if (expiryProductCategoryModel.getSuccessful()) {
                        expiryProductView.onSuccessExpiryCategory(expiryProductCategoryModel.getResult());
                    } else {
                        expiryProductView.onUnsuccessExpiryCategory(message);
                    }
                } catch (NullPointerException exp) {
                    expiryProductView.onUnsuccessExpiryCategory(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ExpiryProductCategoryModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                expiryProductView.onUnsuccessExpiryCategory(activity.getString(R.string.server_error));
            }
        });
    }

}
