package com.example.yogeshgarg.source.mvp.ProductUpdate;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public class ProductUpdatePresenterImpl implements ProductUpdatePresenter {

    Activity activity;
    ProductUpdateView productUpdateView;
    JSONObject jsonObject;

    ProductUpdatePresenterImpl(Activity activity, ProductUpdateView productUpdateView) {
        this.activity = activity;
        this.productUpdateView = productUpdateView;
    }

    @Override
    public void callingProductUpdateApi(String productId, String stock, String stockUnit, String price, String discount,
                                        String discountType, String taxType, String comment, String inStore) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(stock, stockUnit, price, discount, discountType, taxType, comment)) {
                getResultOfProductUpdate(productId, stock, stockUnit, price, discount, discountType, taxType, comment, inStore);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            productUpdateView.onInternetErro();
        }
    }

    private void getResultOfProductUpdate(String productId, String stock, String stockUnit, String price, String discount,
                                          String discountType, String taxType, String comment, String instore) {
        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();
        String Stock = stock + stockUnit;
        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_PRODUCT_ID, productId);
            jsonObject.put(Const.KEY_LOCATIONID_REAL, locationId);
            jsonObject.put(Const.KEY_STOCK, Stock);
            jsonObject.put(Const.KEY_COST, price);
            jsonObject.put(Const.KEY_DISCOUNT, discount);
            jsonObject.put(Const.KEY_DISCOUNT_TYPE, discountType);
            jsonObject.put(Const.KEY_TAX_TYPE, taxType);
            jsonObject.put(Const.KEY_COMMENT, comment);
            jsonObject.put(Const.KEY_INSTORE, instore);


        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProductUpdateModel> getResultOfResetPassword = ApiAdapter.getApiService().getResultOfUpdateProductInfo("application/json", "no-cache", body);

        getResultOfResetPassword.enqueue(new Callback<ProductUpdateModel>() {
            @Override
            public void onResponse(Call<ProductUpdateModel> call, Response<ProductUpdateModel> response) {

                Progress.stop();
                try {
                    ProductUpdateModel productUpdateModel = response.body();

                    if (productUpdateModel.getSuccessful()) {
                        productUpdateView.onSuccessProductUpdate();
                    } else {
                        productUpdateView.onUnsuccessProductUpdate(productUpdateModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    productUpdateView.onUnsuccessProductUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProductUpdateModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                productUpdateView.onUnsuccessProductUpdate(activity.getString(R.string.server_error));
            }
        });

    }

    private boolean validation(String stock, String stockUnit, String price, String discount, String discountType, String taxType, String comment) {
        if (Utils.isEmptyOrNull(price)) {
            productUpdateView.onUnsuccessProductUpdate("Please enter product price.");
            return false;
        }
        return true;
    }
}
