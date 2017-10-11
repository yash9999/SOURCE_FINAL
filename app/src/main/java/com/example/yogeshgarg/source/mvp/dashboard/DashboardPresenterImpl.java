package com.example.yogeshgarg.source.mvp.dashboard;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardPlanogramModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

        Call<DashboardRecentUpdateModel> getRecentPriceUpdate = ApiAdapter.getApiService().gettingResultOfResentPriceUpdateProduct("application/json", "no-cache", body);

        getRecentPriceUpdate.enqueue(new Callback<DashboardRecentUpdateModel>() {
            @Override
            public void onResponse(Call<DashboardRecentUpdateModel> call, Response<DashboardRecentUpdateModel> response) {

                Progress.stop();
                try {
                    DashboardRecentUpdateModel dashboardRecentUpdateModel = response.body();

                    if (dashboardRecentUpdateModel.getSuccessful()) {
                        dashboardView.onSuccessOfRecentPriceUpdate(dashboardRecentUpdateModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfRecentPriceUpdate(dashboardRecentUpdateModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfRecentPriceUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardRecentUpdateModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfRecentPriceUpdate(activity.getString(R.string.server_error));
            }
        });

    }

    //expiry product
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

        Call<DashboardExpiryProductModel> gotResultOfExpiryProduct = ApiAdapter.getApiService().gettingResultOfExpiryProduct("application/json", "no-cache", body);

        gotResultOfExpiryProduct.enqueue(new Callback<DashboardExpiryProductModel>() {
            @Override
            public void onResponse(Call<DashboardExpiryProductModel> call, Response<DashboardExpiryProductModel> response) {

                Progress.stop();
                try {
                    DashboardExpiryProductModel dashboardExpiryProductModel = response.body();

                    if (dashboardExpiryProductModel.getSuccessful()) {
                        dashboardView.onSuccessOfExpiryProduct(dashboardExpiryProductModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfExpiryProduct(dashboardExpiryProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfExpiryProduct(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardExpiryProductModel> call, Throwable t) {
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

        Call<DashboardInStoreModel> gotResultOfSamplingProduct = ApiAdapter.getApiService().gettingResultOfSamplingProduct("application/json", "no-cache", body);

        gotResultOfSamplingProduct.enqueue(new Callback<DashboardInStoreModel>() {
            @Override
            public void onResponse(Call<DashboardInStoreModel> call, Response<DashboardInStoreModel> response) {

                Progress.stop();
                try {
                    DashboardInStoreModel dashboardInStoreModel = response.body();

                    if (dashboardInStoreModel.getSuccessful()) {
                        dashboardView.onSuccessOfInstoreSampling(dashboardInStoreModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfInstoreSampling(dashboardInStoreModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfInstoreSampling(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardInStoreModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfInstoreSampling(activity.getString(R.string.server_error));
            }
        });

    }

    @Override
    public void planogramApi() {
        try {
            ApiAdapter.getInstance(activity);
            callingPlanogramApi();
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorPlanogram();
        }
    }

    private void callingPlanogramApi() {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardPlanogramModel> getPlanogramResult = ApiAdapter.getApiService().gettingResultOfPlanogram("application/json", "no-cache", body);

        getPlanogramResult.enqueue(new Callback<DashboardPlanogramModel>() {
            @Override
            public void onResponse(Call<DashboardPlanogramModel> call, Response<DashboardPlanogramModel> response) {

               Progress.stop();
                try {
                    DashboardPlanogramModel dashboardPlanogramModel = response.body();

                    if (dashboardPlanogramModel.getSuccessful()) {
                        dashboardView.onSuccessPlanogram(dashboardPlanogramModel.getResult());
                    } else {
                        dashboardView.onUnsuccessPlanogram(dashboardPlanogramModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsuccessPlanogram(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardPlanogramModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsuccessPlanogram(activity.getString(R.string.server_error));
            }
        });
    }
}
