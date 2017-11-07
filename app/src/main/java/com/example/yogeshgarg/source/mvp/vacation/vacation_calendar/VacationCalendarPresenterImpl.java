package com.example.yogeshgarg.source.mvp.vacation.vacation_calendar;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;

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

public class VacationCalendarPresenterImpl implements VacationCalenderPresenter {

    Activity activity;
    VacationCalendarView vacationCalendarView;
    JSONObject jsonObject;

    public VacationCalendarPresenterImpl(Activity activity, VacationCalendarView vacationCalendarView) {
        this.activity = activity;
        this.vacationCalendarView = vacationCalendarView;
    }

    @Override
    public void callingVacationApi(String start, String end, String comment, String type, int days) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(start, end, days)) {
                gettingResult(start, end, comment, type);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            vacationCalendarView.onInternetErrorVC();
        }
    }


    public void gettingResult(String start, String end, String comment, String type) {

        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_START, start);
            jsonObject.put(Const.KEY_END, end);
            jsonObject.put(Const.KEY_TYPE, type);
            jsonObject.put(Const.KEY_COMMENT, comment);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<VacationCalendarModel> getResultOfVacationApi = ApiAdapter.getApiService().getResultOfAddVacation("application/json", "no-cache", body);

        getResultOfVacationApi.enqueue(new Callback<VacationCalendarModel>() {
            @Override
            public void onResponse(Call<VacationCalendarModel> call, Response<VacationCalendarModel> response) {

                Progress.stop();
                try {
                    VacationCalendarModel vacationCalendarModel = response.body();


                    if (vacationCalendarModel.getSuccessful()) {
                        vacationCalendarView.onSuccessVC();
                    } else {
                        vacationCalendarView.onUnsuccessVC(vacationCalendarModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    vacationCalendarView.onUnsuccessVC(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<VacationCalendarModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                vacationCalendarView.onUnsuccessVC(activity.getString(R.string.server_error));
            }
        });

    }

    public boolean validation(String start, String end, int days) {
        if (Utils.isEmptyOrNull(start)) {
            vacationCalendarView.onUnsuccessVC("Please enter start date.");
            return false;
        } else if (Utils.isEmptyOrNull(end)) {
            vacationCalendarView.onUnsuccessVC("Please enter end date.");
            return false;
        } else if (days < 0) {
            vacationCalendarView.onUnsuccessVC("From date can not be greater than To date.");
            return false;
        }

        return true;
    }





}
