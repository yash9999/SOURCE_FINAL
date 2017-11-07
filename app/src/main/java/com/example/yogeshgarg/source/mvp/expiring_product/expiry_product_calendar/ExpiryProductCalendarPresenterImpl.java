package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product.ExpiryProduct_ProductModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 27/08/17.
 */

public class ExpiryProductCalendarPresenterImpl implements ExpiryProductCalendarPresenter {

    Activity activity;
    ExpiryProductCalendarView expiryProductCalendarView;
    JSONObject jsonObject;

    public ExpiryProductCalendarPresenterImpl(Activity activity, ExpiryProductCalendarView expiryProductCalendarView) {
        this.activity = activity;
        this.expiryProductCalendarView = expiryProductCalendarView;
    }

    @Override
    public void callingExpirtProductCalendarApi(String productId, String start,  String stock, String stockUnit, String comment) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(productId, start, stock, stockUnit, comment)) {
                getResultOfExpiryProductAdd(productId, start,  stock, stockUnit, comment);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            expiryProductCalendarView.onInternetError();
        }
    }

    private void getResultOfExpiryProductAdd(String productId, String start,  String stock, String stockUnit, String comment) {
        Progress.start(activity);

        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_PRODUCT_ID, productId);
            jsonObject.put(Const.KEY_LOCATIONID_REAL, locationId);
            jsonObject.put(Const.KEY_START, start);
            jsonObject.put(Const.KEY_COMMENT, comment);
            jsonObject.put(Const.KEY_STOCK, stock + stockUnit);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ExpiryProductCalendarModel> getResultOfAddExpiryApi = ApiAdapter.getApiService().getResultOfAddExpiryProduct("application/json", "no-cache", body);

        getResultOfAddExpiryApi.enqueue(new Callback<ExpiryProductCalendarModel>() {
            @Override
            public void onResponse(Call<ExpiryProductCalendarModel> call, Response<ExpiryProductCalendarModel> response) {

                Progress.stop();
                try {
                    ExpiryProductCalendarModel expiryProductCalendarModel = response.body();


                    if (expiryProductCalendarModel.getSuccessful()) {
                        expiryProductCalendarView.onSuccess();
                    } else {
                        expiryProductCalendarView.onUnsuccess(expiryProductCalendarModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    expiryProductCalendarView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ExpiryProductCalendarModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                expiryProductCalendarView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }

    private boolean validation(String productId, String start,  String stock, String stockUnit, String comment) {

        if (Utils.isEmptyOrNull(start)) {
            expiryProductCalendarView.onUnsuccess(activity.getString(R.string.start__date_empty));
            return false;
        }

        return true;
    }
}
