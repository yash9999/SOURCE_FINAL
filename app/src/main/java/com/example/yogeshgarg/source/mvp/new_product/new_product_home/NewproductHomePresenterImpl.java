package com.example.yogeshgarg.source.mvp.new_product.new_product_home;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home.ExpiryProductHomeModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 20/08/17.
 */

public class NewproductHomePresenterImpl implements NewProductHomePresenter {

    Activity activity;
    NewProductHomeView newProductHomeView;
    JSONObject jsonObject;

    public NewproductHomePresenterImpl(Activity activity, NewProductHomeView newProductHomeView) {
        this.activity = activity;
        this.newProductHomeView = newProductHomeView;
    }

    @Override
    public void callingNewProductHomeApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfNewProductApi();
        } catch (ApiAdapter.NoInternetException ex) {
            newProductHomeView.onInternetError();
        }
    }

    private void gettingResultOfNewProductApi() {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<NewProductHomeModel> getResultOfNewProductApi = ApiAdapter.getApiService().getNewProductResult("application/json", "no-cache", body);

        getResultOfNewProductApi.enqueue(new Callback<NewProductHomeModel>() {
            @Override
            public void onResponse(Call<NewProductHomeModel> call, Response<NewProductHomeModel> response) {

                Progress.stop();
                try {
                    NewProductHomeModel newProductModel = response.body();

                    if (newProductModel.getSuccessful()) {
                        newProductHomeView.onSuccess(newProductModel.getResult());
                    } else {
                        newProductHomeView.onUnsuccess(newProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    newProductHomeView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<NewProductHomeModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                newProductHomeView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });

    }
}
