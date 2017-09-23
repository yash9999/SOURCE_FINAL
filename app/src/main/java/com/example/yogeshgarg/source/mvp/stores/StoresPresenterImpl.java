package com.example.yogeshgarg.source.mvp.stores;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 19/07/17.
 */

public class StoresPresenterImpl implements StoresPresenter {


    Activity activity;
    StoresView storesView;
    JSONObject jsonObject;

    public StoresPresenterImpl(Activity activity, StoresView storesView) {
        this.activity = activity;
        this.storesView = storesView;
    }

    @Override
    public void callingStoreListApi() {
        try {
            ApiAdapter.getInstance(activity);
            callingStoresApi();
        } catch (ApiAdapter.NoInternetException ex) {
            storesView.onInternetError();
        }
    }

    private void callingStoresApi() {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<StoresModel> getRegisterMessage = ApiAdapter.getApiService().storesList("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<StoresModel>() {
            @Override
            public void onResponse(Call<StoresModel> call, Response<StoresModel> response) {

                Progress.stop();
                try {
                    StoresModel storesModel = response.body();
                    if (storesModel.getSuccessful()) {
                        storesView.onSuccessStoresList(storesModel.getResult());
                    } else {
                        storesView.onUnsuccessStoresList(storesModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    storesView.onUnsuccessStoresList(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<StoresModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                storesView.onUnsuccessStoresList(activity.getString(R.string.server_error));
            }
        });
    }

}

