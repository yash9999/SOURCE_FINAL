package com.example.yogeshgarg.source.mvp.in_store_sampling.store_home;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home.ExpiryProductHomeModel;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class InstoreHomePresenterImpl implements InstoreHomePresenter {

    Activity activity;
    InstoreHomeView instoreHomeView;
    JSONObject jsonObject;

    public InstoreHomePresenterImpl(Activity activity, InstoreHomeView instoreHomeView) {
        this.activity = activity;
        this.instoreHomeView = instoreHomeView;
    }

    @Override
    public void callingInStoreHomeApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfInStoreHome();
        } catch (ApiAdapter.NoInternetException ex) {
            instoreHomeView.onInternetError();
        }
    }

    private void gettingResultOfInStoreHome(){
        Progress.start(activity);
        UserSession userSession=new UserSession(activity);
        String locatioId=userSession.getLocationId();
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID,locatioId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<InStoreHomeModel> getResultOfProductApi = ApiAdapter.getApiService().instoreProductHome("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<InStoreHomeModel>() {
            @Override
            public void onResponse(Call<InStoreHomeModel> call, Response<InStoreHomeModel> response) {

                Progress.stop();
                try {
                    InStoreHomeModel inStoreHomeModel = response.body();

                    if (inStoreHomeModel.getSuccessful()) {
                        instoreHomeView.onSuccess(inStoreHomeModel.getResult());
                    } else {
                        instoreHomeView.onUnsuccess(inStoreHomeModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    instoreHomeView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<InStoreHomeModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                instoreHomeView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });

    }
}
