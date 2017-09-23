package com.example.yogeshgarg.source.mvp.vacation.vacation_home;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 20/08/17.
 */

public class VacationHomePresenterImpl implements VacationHomePresenter {

    Activity activity;
    VacationHomeView vacationHomeView;
    JSONObject jsonObject;

    public VacationHomePresenterImpl(Activity activity, VacationHomeView vacationHomeView) {
        this.activity = activity;
        this.vacationHomeView = vacationHomeView;
    }

    @Override
    public void callingVacationHomeApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfVacationHome();
        } catch (ApiAdapter.NoInternetException ex) {
            vacationHomeView.onInternetError();
        }
    }

    private void gettingResultOfVacationHome() {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<VacationHomeModel> getVacationResult = ApiAdapter.getApiService().getVacationResult("application/json", "no-cache", body);

        getVacationResult.enqueue(new Callback<VacationHomeModel>() {
            @Override
            public void onResponse(Call<VacationHomeModel> call, Response<VacationHomeModel> response) {

                Progress.stop();
                try {
                    VacationHomeModel vacationHomeModel = response.body();

                    if (vacationHomeModel.getSuccessful()) {
                        vacationHomeView.onSuccessVH(vacationHomeModel.getResult());
                    } else {
                        vacationHomeView.onUnsuccessVH(vacationHomeModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    vacationHomeView.onUnsuccessVH(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<VacationHomeModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                vacationHomeView.onUnsuccessVH(activity.getString(R.string.server_error));
            }
        });

    }


    @Override
    public void callingVacationDeleteApi(String vacationId) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfDeleteApi(vacationId);
        } catch (ApiAdapter.NoInternetException ex) {
            vacationHomeView.onInternetErrorDelete();
        }
    }


    public void gettingResultOfDeleteApi(String vacationId) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_VACATION_ID, vacationId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ResponseBody> getResultOfDeleteVacationApi = ApiAdapter.getApiService().getResultOfDeleteVacation("application/json", "no-cache", body);

        getResultOfDeleteVacationApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Progress.stop();
                /*try {
                    VacationCalendarModel vacationCalendarModel = response.body();


                    if (vacationCalendarModel.getSuccessful()) {
                        vacationCalendarView.onSuccessVC();
                    } else {
                        vacationCalendarView.onUnsuccessVC(vacationCalendarModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    vacationCalendarView.onUnsuccessDelete(activity.getString(R.string.server_error));
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                vacationHomeView.onUnsuccessDelete(activity.getString(R.string.server_error));
            }
        });

    }
}
