package com.example.yogeshgarg.source.mvp.product_list.product_list_category;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;

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

public class ProductListCategoryPresenterImpl implements ProductListCategoryPresenter {

    Activity activity;
    ProductListCategoryView productListCategoryView;
    JSONObject jsonObject;

    public ProductListCategoryPresenterImpl(Activity activity, ProductListCategoryView productListCategoryView) {
        this.activity = activity;
        this.productListCategoryView = productListCategoryView;
    }

    @Override
    public void callingProductListCategoryApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfProductListCategory();
        } catch (ApiAdapter.NoInternetException ex) {
            productListCategoryView.onInternetError();
        }
    }


    private void gettingResultOfProductListCategory() {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATION_ID, locationId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProductListCategoryModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfPLCategory("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<ProductListCategoryModel>() {
            @Override
            public void onResponse(Call<ProductListCategoryModel> call, Response<ProductListCategoryModel> response) {

                Progress.stop();
                try {
                    ProductListCategoryModel productListCategoryModel = response.body();

                    if (productListCategoryModel.getSuccessful()) {
                        productListCategoryView.onSuccessProductListCategory(productListCategoryModel.getResult());
                    } else {
                        productListCategoryView.onUnsuccessProductListCategory(productListCategoryModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productListCategoryView.onUnsuccessProductListCategory(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProductListCategoryModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productListCategoryView.onUnsuccessProductListCategory(activity.getString(R.string.server_error));
            }
        });
    }

    @Override
    public void callingPublishApi(int publish, int position,String categoryId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultPublish(publish, position,categoryId);
        } catch (ApiAdapter.NoInternetException ex) {
            productListCategoryView.onInternetErrorPublish();
        }
    }

    public void gettingResultPublish(final int publish, final int position, String categoryId) {

        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_LOCATIONID_REAL, locationId);
            jsonObject.put(Const.KEY_CATEGORY_ID_PUBLISH,categoryId);
            jsonObject.put(Const.KEY_PUBLISH,publish);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<CategoryPublishModel> getResultOfProductApi = ApiAdapter.getApiService().resultOfPLCategoryPublish("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<CategoryPublishModel>() {
            @Override
            public void onResponse(Call<CategoryPublishModel> call, Response<CategoryPublishModel> response) {

                Progress.stop();
                try {
                    CategoryPublishModel categoryPublishModel = response.body();

                    if (categoryPublishModel.getSuccessful()) {
                        productListCategoryView.onSuccessPublish(publish,position);
                    } else {
                        productListCategoryView.onUnsuccessPublish(categoryPublishModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productListCategoryView.onUnsuccessPublish(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<CategoryPublishModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productListCategoryView.onUnsuccessPublish(activity.getString(R.string.server_error));
            }
        });
    }
}
