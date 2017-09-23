package com.example.yogeshgarg.source.mvp.in_store_sampling.store_calendar;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_product.StoreProductModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 31/08/17.
 */

public class InStoreCalendarPresenterImpl implements InStoreCalendarPresenter {

    Activity activity;
    InStoreCalendarView inStoreCalendarView;
    JSONObject jsonObject;

    public InStoreCalendarPresenterImpl(Activity activity, InStoreCalendarView inStoreCalendarView) {
        this.activity = activity;
        this.inStoreCalendarView = inStoreCalendarView;
    }

    @Override
    public void callingInStoreSamplingCalendarApi(String productId, String start, String comment) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResult(productId, start, comment);
        } catch (ApiAdapter.NoInternetException ex) {
            inStoreCalendarView.onInternetError();
        }
    }

    public void gettingResult(String productId, String start, String comment) {

        Progress.start(activity);
        UserSession userSession = new UserSession(activity);
        String locationId = userSession.getLocationId();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_PRODUCT_ID, productId);
            jsonObject.put(Const.KEY_LOCATIONID_REAL, locationId);
            jsonObject.put(Const.KEY_COMMENT, comment);
            jsonObject.put(Const.KEY_SAMPLINGDATE, start);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<InStoreCalendarModel> getResultOfProductApi = ApiAdapter.getApiService().getResultOfAddInStoreSampling("application/json", "no-cache", body);

        getResultOfProductApi.enqueue(new Callback<InStoreCalendarModel>() {
            @Override
            public void onResponse(Call<InStoreCalendarModel> call, Response<InStoreCalendarModel> response) {

                Progress.stop();
                try {
                    InStoreCalendarModel  inStoreCalendarModel = response.body();


                    if (inStoreCalendarModel.getSuccessful()) {
                        inStoreCalendarView.onSuccess();
                    } else {
                        inStoreCalendarView.onUnsuccess(inStoreCalendarModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    inStoreCalendarView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<InStoreCalendarModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                inStoreCalendarView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });

    }
}
