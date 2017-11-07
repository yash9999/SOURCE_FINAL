package com.example.yogeshgarg.source.mvp.chatting;

import android.app.Activity;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.mvp.forgot_password.ForgotPasswordOtpModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 05/11/17.
 */

public class ChattingPresenterImpl implements ChattingPresenter {

    Activity activity;
    ChattingView chattingView;
    JSONObject jsonObject;

    public ChattingPresenterImpl(Activity activity, ChattingView chattingView) {
        this.activity = activity;
        this.chattingView = chattingView;
    }

    @Override
    public void callingConversationApi(String userId) {
        try {
            ApiAdapter.getInstance(activity);
            getResultOfConversation(userId);
        } catch (ApiAdapter.NoInternetException ex) {
            chattingView.onInternetError();
        }
    }


    private void getResultOfConversation(String userId) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_USERID, userId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ChattingModel> conversation = ApiAdapter.getApiService().conversation("application/json", "no-cache", body);

        conversation.enqueue(new Callback<ChattingModel>() {
            @Override
            public void onResponse(Call<ChattingModel> call, Response<ChattingModel> response) {

                Progress.stop();
                try {
                    ChattingModel chattingModel = response.body();
                    String message = chattingModel.getMessage();
                    if (chattingModel.getSuccessful()) {
                        chattingView.onSuccessConversation(chattingModel.getResult());
                    } else {
                        chattingView.onUnsuccessConversation(message);
                    }
                } catch (NullPointerException exp) {
                    chattingView.onUnsuccessConversation(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ChattingModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                chattingView.onUnsuccessConversation(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void sendMessage(String message, String userId) {
        try {
            ApiAdapter.getInstance(activity);
            getResultOfSendMessage(message, userId);
        } catch (ApiAdapter.NoInternetException ex) {
            chattingView.onInternetError();
        }
    }

    private void getResultOfSendMessage(String message, String userId) {
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_MESSAGE, message);
            jsonObject.put(Const.KEY_TO, userId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<SendingModel> conversation = ApiAdapter.getApiService().sendMessage("application/json", "no-cache", body);

        conversation.enqueue(new Callback<SendingModel>() {
            @Override
            public void onResponse(Call<SendingModel> call, Response<SendingModel> response) {


                try {
                    SendingModel sendingModel = response.body();
                    String message = sendingModel.getMessage();
                    if (sendingModel.getSuccessful()) {
                        chattingView.onSuccessSendMessage(sendingModel.getResult());
                    } else {
                        chattingView.onUnsuccessSendMessage(message);
                    }
                } catch (NullPointerException exp) {
                    chattingView.onUnsuccessSendMessage(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<SendingModel> call, Throwable t) {
                t.printStackTrace();
                chattingView.onUnsuccessSendMessage(activity.getString(R.string.server_error));
            }
        });
    }

    @Override
    public void receivedMessage(String userId) {
        try {
            ApiAdapter.getInstance(activity);
            getResultOfReceivedMessage(userId);
        } catch (ApiAdapter.NoInternetException ex) {
            chattingView.onInternetError();
        }
    }

    private void getResultOfReceivedMessage(String userId) {


        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_USERID, userId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ResponseBody> conversation = ApiAdapter.getApiService().receivedMessage("application/json", "no-cache", body);

        conversation.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


               /* try {
                    ForgotPasswordOtpModel forgotPasswordOtpModel = response.body();
                    String message = forgotPasswordOtpModel.getMessage();
                    if (forgotPasswordOtpModel.getSuccessful()) {
                        chattingView.onSuccessForgotPasswordOtp(message);
                    } else {
                        chattingView.onUnsuccessConversation(message);
                    }
                } catch (NullPointerException exp) {
                    chattingView.onUnsuccessConversation(activity.getString(R.string.server_error));
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
                chattingView.onUnsuccessConversation(activity.getString(R.string.server_error));
            }
        });
    }
}
