package com.example.yogeshgarg.source.mvp.product_list.product_list_brand;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.product_list.product_list_category.ProductListCategoryModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_category.ProductListCategoryPresenter;

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

public class ProductListBrandPresenterImpl implements ProductListBrandPresenter {

    Activity activity;
    ProductListBrandView productListBrandView;
    JSONObject jsonObject;

    public ProductListBrandPresenterImpl(Activity activity, ProductListBrandView productListBrandView) {
        this.activity = activity;
        this.productListBrandView = productListBrandView;
    }


    @Override
    public void callingPLBrandApi(String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfBrand(categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            productListBrandView.onInternetError();
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
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProductListBrandModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfPLBrand("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ProductListBrandModel>() {
            @Override
            public void onResponse(Call<ProductListBrandModel> call, Response<ProductListBrandModel> response) {

                Progress.stop();
                try {
                    ProductListBrandModel productListBrandModel = response.body();

                    if (productListBrandModel.getSuccessful()) {
                        productListBrandView.onSuccessPLBrand(productListBrandModel.getResult());
                    } else {
                        productListBrandView.onUnsuccessPLBrand(productListBrandModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productListBrandView.onUnsuccessPLBrand(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProductListBrandModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productListBrandView.onUnsuccessPLBrand(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void callingPlBrandPublish(int publish, int position, String brandId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultPlBrandPublish(publish, position, brandId);
        } catch (ApiAdapter.NoInternetException ex) {
            productListBrandView.onInternetError();
        }
    }

    public void gettingResultPlBrandPublish(final int publish, final int position, String brandId) {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATIONID_REAL, locationId);
            jsonObject.put(Const.KEY_PUBLISH, publish);
            jsonObject.put(Const.KEY_BRAND_ID, brandId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<BrandPublishModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfPLBrandPublish("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<BrandPublishModel>() {
            @Override
            public void onResponse(Call<BrandPublishModel> call, Response<BrandPublishModel> response) {

                Progress.stop();
                try {
                    BrandPublishModel brandPublishModel = response.body();

                    if (brandPublishModel.getSuccessful()) {
                        productListBrandView.onSuccessPlBrandPublish(publish,position);
                    } else {
                        productListBrandView.onUnsuccessBrandPublish(brandPublishModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productListBrandView.onUnsuccessBrandPublish(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<BrandPublishModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productListBrandView.onUnsuccessBrandPublish(activity.getString(R.string.server_error));
            }
        });
    }
}
