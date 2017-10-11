package com.example.yogeshgarg.source.mvp.in_store_sampling.store_brand;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand.ExpiryProductBrandModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 03/10/17.
 */

public class InStoreBrandPresenterImpl implements InStoreBrandPresenter {

    Activity activity;
    InStoreBrandView inStoreBrandView;
    JSONObject jsonObject;

    public InStoreBrandPresenterImpl(Activity activity,InStoreBrandView inStoreBrandView){
        this.activity=activity;
        this.inStoreBrandView=inStoreBrandView;
    }

    @Override
    public void callingSSBrandApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfBrand(categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            inStoreBrandView.onInternetError();
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

        Call<InStoreBrandModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfSSBrand("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<InStoreBrandModel>() {
            @Override
            public void onResponse(Call<InStoreBrandModel> call, Response<InStoreBrandModel> response) {

                Progress.stop();
                try {
                    InStoreBrandModel inStoreBrandModel = response.body();

                    if (inStoreBrandModel.getSuccessful()) {
                        inStoreBrandView.onSuccessSSBrand(inStoreBrandModel.getResult());
                    } else {
                        inStoreBrandView.onUnsuccessSSBrand(inStoreBrandModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    inStoreBrandView.onUnsuccessSSBrand(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<InStoreBrandModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                inStoreBrandView.onUnsuccessSSBrand(activity.getString(R.string.server_error));
            }
        });
    }
}
