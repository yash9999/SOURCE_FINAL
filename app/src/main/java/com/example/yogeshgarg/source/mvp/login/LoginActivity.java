package com.example.yogeshgarg.source.mvp.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.forgot_password.ForgotPasswordActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;


    @BindView(R.id.edtTextUsername)
    EditText edtTextUsername;

    @BindView(R.id.edtTextpassword)
    EditText edtTextpassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.txtViewForgotPassword)
    TextView txtViewForgotPassword;
    LoginPresenterImpl loginPresenterImpl;

    QBPrivateChatManager privateChatManager;
    QBMessageListener<QBPrivateChat> privateChatMessageListener;
    QBChatService chatService;

    int opponentId = 34547169;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setFont();
        loginPresenterImpl = new LoginPresenterImpl(this, this);


        chatService = QBChatService.getInstance();
        registerQbChatListeners();

        privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
            @Override
            public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {
                //Log.e("Chat Message", "" + chatMessage.getBody());
                //chatAdapter.updateList(chatMessage);

                // chatMessage.getBody();


               /* requestBuilder.setSkip(skipRecords = 0);
                if (isActivityForeground) {
                    loadDialogsFromQbInUiThread(true);
                }*/
            }


            @Override
            public void processError(QBPrivateChat privateChat, QBChatException error, QBChatMessage originMessage) {
                error.printStackTrace();
                Progress.stop();
            }
        };

        //registerQbChatListeners();


    }

    @Override
    public void onLoginSuccess(LoginModel.Result result) {
        String id = result.getId();
        String token = result.getToken();
        String username=result.getName();
        String usertype=result.getUsertype();

        UserSession userSession = new UserSession(this);
        userSession.createUserSession(id, token,username,usertype);

        callingNotificationApi();


    }

    @Override
    public void onLoginUnsuccess(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void internetError() {
        SnackNotify.checkConnection(onRetryLoginApi, coordinateLayout);
    }

    @Override
    public void onPushSuccess() {
        Intent intent = new Intent(LoginActivity.this, StoresActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPushUnsuccess(String message) {

    }

    @Override
    public void internetErrorPush() {

    }

    OnClickInterface onRetryLoginApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingLoginApi();
        }
    };

    //-------------------------------------- other method---------------------------------------
    private void callingLoginApi() {
        getData();
    }

    private void getData() {

        String username = edtTextUsername.getText().toString();
        String password = edtTextpassword.getText().toString();


        loginPresenterImpl.fetchingLoginApiData(username, password);
    }

    @OnClick(R.id.btnLogin)
    public void btnSigninClick() {
        callingLoginApi();
    }

    @OnEditorAction(R.id.edtTextpassword)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callingLoginApi();
            return true;
        }
        return false;
    }

    @OnClick(R.id.txtViewForgotPassword)
    public void txtViewForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void setFont() {
        FontHelper.setFontFace(edtTextUsername, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(edtTextpassword, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(btnLogin, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewForgotPassword, FontHelper.FontType.FONT_Normal, this);

    }

    private void callingNotificationApi() {
        loginPresenterImpl.callingPushNotificationApi();
    }


    private void registerQbChatListeners() {
        privateChatManager = chatService.getPrivateChatManager();
        //QBGroupChatManager groupChatManager = QBChatService.getInstance().getGroupChatManager();
        if (privateChatManager != null) {
            //privateChatManager.addPrivateChatManagerListener(privateChatManagerListener);


            new CreateDialog().execute(privateChatManager);

        }
    }

    private class CreateDialog extends AsyncTask<QBPrivateChatManager, String, QBPrivateChatManager> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected QBPrivateChatManager doInBackground(QBPrivateChatManager... qbPrivateChatManagers) {


            return qbPrivateChatManagers[0];
        }

        @Override
        protected void onPostExecute(final QBPrivateChatManager s) {
            super.onPostExecute(s);

            s.createDialog(opponentId, new QBEntityCallback<QBDialog>() {
                @Override
                public void onSuccess(QBDialog dialog, Bundle args) {
                    Log.d("CreateDialog", "onSuccess");
                    // dialogId = dialog.getDialogId();
                    //chatInPrivateChat(s, false);
                }

                @Override
                public void onError(QBResponseException errors) {
                    Log.d("CreateDialog", "onError " + errors.getMessage());
                    //Progress.stop();
                    // AlertDialogManager.sAlertFinish(ChatActivity.this, getString(R.string.chat_not_availble));
                    errors.printStackTrace();
                }
            });
        }
    }

}
