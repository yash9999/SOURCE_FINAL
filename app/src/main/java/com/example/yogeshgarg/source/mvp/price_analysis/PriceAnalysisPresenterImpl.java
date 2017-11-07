package com.example.yogeshgarg.source.mvp.price_analysis;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.vacation.vacation_calendar.VacationCalendarModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringJoiner;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 10/09/17.
 */

public class PriceAnalysisPresenterImpl implements PriceAnalysisPresenter {

    Activity activity;
    PriceAnalysisView priceAnalysisView;
    JSONObject jsonObject;

    public PriceAnalysisPresenterImpl(Activity activity, PriceAnalysisView priceAnalysisView) {
        this.activity = activity;
        this.priceAnalysisView = priceAnalysisView;

    }

    @Override
    public void callingPriceAnalysisApi(ArrayList<String> arrayListCategory, ArrayList<String> arrayListProduct,
                                        ArrayList<String> arrayListBrand, ArrayList<String> arrayListStore) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfPriceAnalysis(arrayListCategory, arrayListProduct,
                    arrayListBrand, arrayListStore);
        } catch (ApiAdapter.NoInternetException ex) {
            priceAnalysisView.onInternetError();
        }
    }

    public void gettingResultOfPriceAnalysis(ArrayList<String> arrayListCategory, ArrayList<String> arrayListProduct,
                                             ArrayList<String> arrayListBrand, ArrayList<String> arrayListStore) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();


            StringBuilder category = new StringBuilder();

            for (int i = 0; i < arrayListCategory.size(); i++) {
                if(i<arrayListCategory.size()-1){
                    category.append(arrayListCategory.get(i));
                    category.append(",");
                }else{
                    category.append(arrayListCategory.get(i));
                }
            }


            StringBuilder brand = new StringBuilder();
            for (int i = 0; i < arrayListBrand.size(); i++) {
                if(i<arrayListBrand.size()-1){
                    brand.append(arrayListBrand.get(i));
                    brand.append(",");
                }else{
                    brand.append(arrayListBrand.get(i));
                }
            }


            StringBuilder product = new StringBuilder();
            for (int i = 0; i < arrayListProduct.size(); i++) {
                if(i<arrayListProduct.size()-1){
                    product.append(arrayListProduct.get(i));
                    product.append(",");
                }else{
                    product.append(arrayListProduct.get(i));
                }
            }

            StringBuilder store = new StringBuilder();
            for (int i = 0; i < arrayListStore.size(); i++) {
                if(i<arrayListStore.size()-1){
                    store.append(arrayListStore.get(i));
                    store.append(",");
                }else{
                    store.append(arrayListStore.get(i));
                }
            }

            jsonObject.put(Const.KAY_CATEGORY_ID, category);
            jsonObject.put(Const.KEY_BRANDS, brand);
            jsonObject.put(Const.KEY_PRODUCTS, product);
            jsonObject.put(Const.KEY_STORES, store);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<PriceAnalysisModel> getResultOfPriceAnalysis = ApiAdapter.getApiService().getResultOfPriceAnalysis("application/json", "no-cache", body);

        getResultOfPriceAnalysis.enqueue(new Callback<PriceAnalysisModel>() {
            @Override
            public void onResponse(Call<PriceAnalysisModel> call, Response<PriceAnalysisModel> response) {
                Progress.stop();

                try {
                    PriceAnalysisModel priceAnalysisModel = response.body();

                    if (priceAnalysisModel.getSuccessful()) {
                        priceAnalysisView.onSuccess(priceAnalysisModel.getResult());
                    } else {
                        priceAnalysisView.onUnsuccess(priceAnalysisModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    exp.printStackTrace();
                    priceAnalysisView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<PriceAnalysisModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                Log.e("response body", "second");
                priceAnalysisView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });

    }


}
