package com.example.yogeshgarg.source.mvp.dashboard;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.SourceApp;
import com.example.yogeshgarg.source.common.helper.NetworkUtil;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.login.LoginModel;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by himanshu on 29/07/17.
 */

public class DashboardPresenterImpl implements DashboardContractor.Presenter {


    DashboardContractor.View mView;

    public DashboardPresenterImpl(DashboardContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getNewProduct() {
        if(NetworkUtil.isNetworkConnected(SourceApp.getInstance())){

            mView.showProgress();
            final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

            Call<PriceSurveyProductModel> getRegisterMessage = ApiAdapter.getApiService().gettingResultOfNewProductUpdate("application/json", "no-cache", body);

            getRegisterMessage.enqueue(new Callback<PriceSurveyProductModel>() {
                @Override
                public void onResponse(Call<PriceSurveyProductModel> call, Response<PriceSurveyProductModel> response) {

                    mView.hideProgress();
                    try {
                        PriceSurveyProductModel loginModel = response.body();

                        if (loginModel.getSuccessful()) {
                            mView.getNewProductResponse(loginModel.getResult());
                        } else {

                            // mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                        }
                    } catch (NullPointerException exp) {
                        mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                    }
                }

                @Override
                public void onFailure(Call<PriceSurveyProductModel> call, Throwable t) {
                    mView.hideProgress();
                    t.printStackTrace();
                    mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                }
            });
        }else{

        }
    }

    @Override
    public void getRecentPriceChangeProduct() {
        if(NetworkUtil.isNetworkConnected(SourceApp.getInstance())){

            mView.showProgress();
            final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

            Call<PriceSurveyProductModel> getRegisterMessage = ApiAdapter.getApiService().gettingResultOfResentPriceUpdateProduct("application/json", "no-cache", body);

            getRegisterMessage.enqueue(new Callback<PriceSurveyProductModel>() {
                @Override
                public void onResponse(Call<PriceSurveyProductModel> call, Response<PriceSurveyProductModel> response) {

                    mView.hideProgress();
                    try {
                        PriceSurveyProductModel loginModel = response.body();

                        if (loginModel.getSuccessful()) {
                            mView.getRecentPriceChangedProductResponse(loginModel.getResult());
                        } else {

                            // mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                        }
                    } catch (NullPointerException exp) {
                        mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                    }
                }

                @Override
                public void onFailure(Call<PriceSurveyProductModel> call, Throwable t) {
                    mView.hideProgress();
                    t.printStackTrace();
                    mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                }
            });
        }else{

        }
    }

    @Override
    public void getSamplingProduct() {
    if(NetworkUtil.isNetworkConnected(SourceApp.getInstance())){

    mView.showProgress();
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

    Call<PriceSurveyProductModel> getRegisterMessage = ApiAdapter.getApiService().gettingResultOfSamplingProduct("application/json", "no-cache", body);

    getRegisterMessage.enqueue(new Callback<PriceSurveyProductModel>() {
        @Override
        public void onResponse(Call<PriceSurveyProductModel> call, Response<PriceSurveyProductModel> response) {

            mView.hideProgress();
            try {
                PriceSurveyProductModel loginModel = response.body();

                if (loginModel.getSuccessful()) {
                    mView.getSamplingProductResponse(loginModel.getResult());
                } else {

                   // mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                }
            } catch (NullPointerException exp) {
                mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
            }
        }

        @Override
        public void onFailure(Call<PriceSurveyProductModel> call, Throwable t) {
            mView.hideProgress();
            t.printStackTrace();
            mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
        }
    });
}else{

}
    }

    @Override
    public void getExpiryProduct() {
        if(NetworkUtil.isNetworkConnected(SourceApp.getInstance())){

            mView.showProgress();
            final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

            Call<PriceSurveyProductModel> getRegisterMessage = ApiAdapter.getApiService().gettingResultOfExpiryProduct("application/json", "no-cache", body);

            getRegisterMessage.enqueue(new Callback<PriceSurveyProductModel>() {
                @Override
                public void onResponse(Call<PriceSurveyProductModel> call, Response<PriceSurveyProductModel> response) {

                    mView.hideProgress();
                    try {
                        PriceSurveyProductModel loginModel = response.body();

                        if (loginModel.getSuccessful()) {
                            mView.getExpiryProductResponse(loginModel.getResult());
                        } else {

                            // mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                        }
                    } catch (NullPointerException exp) {
                        mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                    }
                }

                @Override
                public void onFailure(Call<PriceSurveyProductModel> call, Throwable t) {
                    mView.hideProgress();
                    t.printStackTrace();
                    mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                }
            });
        }else{

        }
    }
}
