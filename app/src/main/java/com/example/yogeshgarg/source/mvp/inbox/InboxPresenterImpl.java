package com.example.yogeshgarg.source.mvp.inbox;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.forgot_password.ForgotPasswordModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 15/10/17.
 */

public class InboxPresenterImpl implements InboxPresenter {

    Activity activity;
    InboxView inboxView;
    JSONObject jsonObject;

    InboxPresenterImpl(Activity activity, InboxView inboxView) {
        this.activity = activity;
        this.inboxView = inboxView;
    }

    @Override
    public void getOpponentList() {
        try {
            ApiAdapter.getInstance(activity);
            getResult();
        } catch (ApiAdapter.NoInternetException ex) {
            inboxView.onInternetError();
        }
    }

    public void getResult(){
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<InboxModel> inboxList = ApiAdapter.getApiService().gettingInboxList("application/json", "no-cache", body);

        inboxList.enqueue(new Callback<InboxModel>() {
            @Override
            public void onResponse(Call<InboxModel> call, Response<InboxModel> response) {

                Progress.stop();
                try {
                    InboxModel inboxModel = response.body();
                    String message = inboxModel.getMessage();
                    if (inboxModel.getSuccessful()) {
                        inboxView.onSuccess(inboxModel.getResult());
                    } else {
                        inboxView.onUnsuccess(message);
                    }
                } catch (NullPointerException exp) {
                    inboxView.onUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<InboxModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                inboxView.onUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }
}
