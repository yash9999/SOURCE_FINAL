package com.example.yogeshgarg.source.mvp.dashboard;

import android.app.Activity;

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

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by himanshu on 29/07/17.
 */

public class DashboardPresenterImpl implements DashboardPresenter {


    Activity activity;
    DashboardView dashboardView;

    JSONObject jsonObject;

    public DashboardPresenterImpl(Activity activity, DashboardView dashboardView) {
        this.activity = activity;
        this.dashboardView = dashboardView;
    }


    //New product update
    @Override
    public void newProductApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingNewProductApi(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfNewProductUpdate();
        }
    }

    private void callingNewProductApi(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<NewProductModel> getRegisterMessage = ApiAdapter.getApiService().gettingResultOfNewProductUpdate("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<NewProductModel>() {
            @Override
            public void onResponse(Call<NewProductModel> call, Response<NewProductModel> response) {

                Progress.stop();
                try {
                    NewProductModel newProductModel = response.body();

                    if (newProductModel.getSuccessful()) {
                        dashboardView.onSuccessOfNewProductUpdate(newProductModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfNewProductUpdate(newProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfNewProductUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<NewProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfNewProductUpdate(activity.getString(R.string.server_error));
            }
        });
    }

    //------------------Recent Price update
    @Override
    public void recentPriceUpdateApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingRecentPriceUpdate(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfRecentPriceUpdate();
        }
    }

    private void callingRecentPriceUpdate(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardCommonModel> getRecentPriceUpdate = ApiAdapter.getApiService().gettingResultOfResentPriceUpdateProduct("application/json", "no-cache", body);

        getRecentPriceUpdate.enqueue(new Callback<DashboardCommonModel>() {
            @Override
            public void onResponse(Call<DashboardCommonModel> call, Response<DashboardCommonModel> response) {

                Progress.stop();
                try {
                    DashboardCommonModel dashboardCommonModel = response.body();

                    if (dashboardCommonModel.getSuccessful()) {
                        dashboardView.onSuccessOfRecentPriceUpdate(dashboardCommonModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfRecentPriceUpdate(dashboardCommonModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfRecentPriceUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardCommonModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfRecentPriceUpdate(activity.getString(R.string.server_error));
            }
        });

    }


    @Override
    public void expiryProductApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingExpiryProductApi(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfExpiryProduct();
        }
    }

    private void callingExpiryProductApi(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardCommonModel> gotResultOfExpiryProduct = ApiAdapter.getApiService().gettingResultOfExpiryProduct("application/json", "no-cache", body);

        gotResultOfExpiryProduct.enqueue(new Callback<DashboardCommonModel>() {
            @Override
            public void onResponse(Call<DashboardCommonModel> call, Response<DashboardCommonModel> response) {

                Progress.stop();
                try {
                    DashboardCommonModel dashboardCommonModel = response.body();

                    if (dashboardCommonModel.getSuccessful()) {
                        dashboardView.onSuccessOfExpiryProduct(dashboardCommonModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfExpiryProduct(dashboardCommonModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfExpiryProduct(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardCommonModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfExpiryProduct(activity.getString(R.string.server_error));
            }
        });

    }


    //Store sampling

    @Override
    public void storeSamplingProductApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingStoreSamplingApi(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfInstoreSampling();
        }
    }

    private void callingStoreSamplingApi(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
           // jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardCommonModel> gotResultOfSamplingProduct = ApiAdapter.getApiService().gettingResultOfSamplingProduct("application/json", "no-cache", body);

        gotResultOfSamplingProduct.enqueue(new Callback<DashboardCommonModel>() {
            @Override
            public void onResponse(Call<DashboardCommonModel> call, Response<DashboardCommonModel> response) {

                Progress.stop();
                try {
                    DashboardCommonModel dashboardCommonModel = response.body();

                    if (dashboardCommonModel.getSuccessful()) {
                        dashboardView.onSuccessOfInstoreSampling(dashboardCommonModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfInstoreSampling(dashboardCommonModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfInstoreSampling(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardCommonModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfInstoreSampling(activity.getString(R.string.server_error));
            }
        });

    }


}
