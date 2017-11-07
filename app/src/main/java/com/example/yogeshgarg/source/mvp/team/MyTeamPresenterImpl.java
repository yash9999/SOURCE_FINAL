package com.example.yogeshgarg.source.mvp.team;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.SourceApp;
import com.example.yogeshgarg.source.common.helper.NetworkUtil;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by himanshu on 29/07/17.
 */

public class MyTeamPresenterImpl implements MyTeamContractor.Presenter {

    MyTeamContractor.View mView;

    public MyTeamPresenterImpl(MyTeamContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getTeam() {
        if(NetworkUtil.isNetworkConnected(SourceApp.getInstance())){

            mView.showProgress();
            final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

            Call<MyTeamModel> getRegisterMessage = ApiAdapter.getApiService().gettingUsers("application/json", "no-cache", body);

            getRegisterMessage.enqueue(new Callback<MyTeamModel>() {
                @Override
                public void onResponse(Call<MyTeamModel> call, Response<MyTeamModel> response) {

                    mView.hideProgress();
                    try {
                        MyTeamModel model = response.body();

                        if (model.getSuccessful()) {
                            mView.getTeam(model.getResult());
                        } else {

                             mView.getTeamFailure(model.getMessage());
                        }
                    } catch (NullPointerException exp) {
                        mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                    }
                }

                @Override
                public void onFailure(Call<MyTeamModel> call, Throwable t) {
                    mView.hideProgress();
                    t.printStackTrace();
                    mView.showAlert(SourceApp.getInstance().getString(R.string.server_error));
                }
            });
        }else{
            mView.getTeamInternetError();
        }
    }
}
