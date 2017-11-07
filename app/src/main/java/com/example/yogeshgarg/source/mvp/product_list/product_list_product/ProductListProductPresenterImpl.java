package com.example.yogeshgarg.source.mvp.product_list.product_list_product;

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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public class ProductListProductPresenterImpl implements ProductListProductPresenter {

    Activity activity;
    ProductListProductView productListProductView;
    JSONObject jsonObject;

    public ProductListProductPresenterImpl(Activity activity, ProductListProductView productListProductView) {
        this.activity = activity;
        this.productListProductView = productListProductView;
    }

    @Override
    public void callingProductApi(String brandId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfProductApi(brandId);
        } catch (ApiAdapter.NoInternetException ex) {
            productListProductView.onInternetError();
        }
    }


    private void gettingResultOfProductApi(String brandId) {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
            jsonObject.put(Const.KEY_BRANDS, brandId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProductListProductModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfPLProduct("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ProductListProductModel>() {
            @Override
            public void onResponse(Call<ProductListProductModel> call, Response<ProductListProductModel> response) {

                Progress.stop();
                try {
                    ProductListProductModel productListProductModel = response.body();

                    if (productListProductModel.getSuccessful()) {
                        productListProductView.onSuccessPLProduct(productListProductModel.getResult());
                    } else {
                        productListProductView.onUnsuccessPlProduct(productListProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productListProductView.onUnsuccessPlProduct(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProductListProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productListProductView.onUnsuccessPlProduct(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void callingProductPublish(int publish, int position, String productId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfPublish(publish,position,productId);
        } catch (ApiAdapter.NoInternetException ex) {
            productListProductView.onInternetErrorPublish();
        }
    }

    public void gettingResultOfPublish(final int publish, final int position, String productId){
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATIONID_REAL, locationId);
            jsonObject.put(Const.KEY_PUBLISH,publish);
            jsonObject.put(Const.KEY_PRODUCT_ID,productId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProductPublishModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfPLProductPublish("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ProductPublishModel>() {
            @Override
            public void onResponse(Call<ProductPublishModel> call, Response<ProductPublishModel> response) {

                Progress.stop();
                try {
                    ProductPublishModel productPublishModel = response.body();

                    if (productPublishModel.getSuccessful()) {
                        productListProductView.onSuccessPlProductPublish(publish,position);
                    } else {
                        productListProductView.onUnsccuessPlProductPublish(productPublishModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productListProductView.onUnsccuessPlProductPublish(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProductPublishModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productListProductView.onUnsccuessPlProductPublish(activity.getString(R.string.server_error));
            }
        });
    }
}
