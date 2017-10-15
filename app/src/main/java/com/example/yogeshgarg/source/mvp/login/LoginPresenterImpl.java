package com.example.yogeshgarg.source.mvp.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;


import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.FcmSession;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 06/06/17.
 */

public class LoginPresenterImpl implements LoginPresenter {

    Activity activity;
    LoginView loginView;
    JSONObject jsonObject;

    public LoginPresenterImpl(Activity activity, LoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
    }

    @Override
    public void fetchingLoginApiData(String username, String password) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(username, password)) {
                callingRegisterationApi(username, password);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            loginView.internetError();
        }
    }


    private boolean validation(String username, String password) {

        if (Utils.isEmptyOrNull(username)) {
            loginView.onLoginUnsuccess(activity.getString(R.string.username_error));
            return false;
        } else if (Utils.isEmptyOrNull(password)) {
            loginView.onLoginUnsuccess(activity.getString(R.string.password_error));
            return false;
        }
        return true;
    }

    private void callingRegisterationApi(final String username, final String password) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_USERNAME, username);
            jsonObject.put(Const.KEY_PASSWORD, password);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<LoginModel> getRegisterMessage = ApiAdapter.getApiService().userLogin("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                Progress.stop();
                try {
                    LoginModel loginModel = response.body();

                    if (loginModel.getSuccessful()) {
                        loginView.onLoginSuccess(loginModel.getResult());
                        loginUserOnQuikBlox(username,password);
                    } else {
                        loginView.onLoginUnsuccess(loginModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    loginView.onLoginUnsuccess(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                loginView.onLoginUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void callingPushNotificationApi() {
        try {
            ApiAdapter.getInstance(activity);
            sendingToken();
        } catch (ApiAdapter.NoInternetException ex) {
            loginView.internetError();
        }
    }

    private void sendingToken() {
        Progress.start(activity);

        String device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        FcmSession fcmSession = new FcmSession(activity);
        String token = fcmSession.getFcmToken();

        try {
            jsonObject = new JSONObject();

            jsonObject.put(Const.KEY_DEVICE_ID, device_id);
            jsonObject.put(Const.KEY_FIREBASE_TOKEN, token);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ResponseBody> getRegisterMessage = ApiAdapter.getApiService().generateToken("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Progress.stop();
                loginView.onPushSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                loginView.onLoginUnsuccess(activity.getString(R.string.server_error));
            }
        });
    }




    // login to quickblox
    private void  loginUserOnQuikBlox(final String username, final String password) {
        Progress.start(activity);

        final QBUser user = new QBUser(username, Const.AC_PWD);

        QBUsers.signIn(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle params) {
                Log.e("signIn", "onSuccess");
                Progress.stop();

                UserSession userSession = new UserSession(activity);
                userSession.setUserID(user.getLogin());
                userSession.setQuikBloxID(user.getId().toString());

                //QBPrivateChat privateChat = privateChatManager.getChat(opponentId);
               /* if (privateChat == null) {
                    privateChat = privateChatManager.createChat(opponentId, privateChatMessageListener);
                }

                Log.e("Dialog id is -----", "" + privateChat.getDialogId());
*/
               // redirect the page
               /* Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(Const.KEY_OPPONENT_ID, Integer.parseInt(edtTextOpponentId.getText().toString()));
                intent.putExtra(Const.KEY_NAME, "Bb");
                intent.putExtra(Const.KEY_DIALOG_ID, "59d921dca28f9a0ab0ce1407");
                startActivity(intent);*/
            }

            @Override
            public void onError(QBResponseException errors) {
                errors.printStackTrace();
                registerUserOnQuikBlox(username+"@gmail.com",username,username);
                Progress.stop();
            }
        });


    }


    //Sign Up Quik Blox
    private void registerUserOnQuikBlox(String email, String name, String userName) {

        Progress.start(activity);

        final QBUser user = new QBUser(userName, Const.AC_PWD);
        user.setFullName(name);
        user.setEmail(email);


        QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                Log.e("Inside", "signUp onSuccess " + user.getId());

                Progress.stop();
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.e("Inside", "signUp onError");
                errors.printStackTrace();
                Progress.stop();
            }
        });
    }

}
