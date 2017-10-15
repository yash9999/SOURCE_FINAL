package com.example.yogeshgarg.source.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

public class BaseActivity extends AppCompatActivity {

    UserSession userSession;
    QBChatService chatService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSession = new UserSession(this);

        QBChatService.setDebugEnabled(true); // enable chat logging
        QBChatService.setDefaultAutoSendPresenceInterval(60); //enable sending online status every 60 sec to keep connection alive


        //configure chat socket
        QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
        chatServiceConfigurationBuilder.setSocketTimeout(60); //Sets chat socket's read timeout in seconds
        chatServiceConfigurationBuilder.setKeepAlive(true); //Sets connection socket's keepAlive option.
        chatServiceConfigurationBuilder.setUseTls(true); //Sets the TLS security mode used when making the connection. By default TLS is disabled.
        QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);

        chatService = QBChatService.getInstance();

        final QBUser user = new QBUser(userSession.getUserId(), Const.AC_PWD);
        QBAuth.createSession(user, new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // success, login to chat

                user.setId(session.getUserId());
                String userId = String.valueOf(session.getId());

                chatService.login(user, new QBEntityCallback() {

                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                       /* Log.d("createSession bundle", "" + bundle);
                        Log.d("createSession Object", "" + o);*/
                    }

                    @Override
                    public void onError(QBResponseException errors) {
                        Progress.stop();
                        errors.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(QBResponseException errors) {
                Progress.stop();
                errors.printStackTrace();
            }
        });


        ConnectionListener connectionListener = new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                Log.d("Inside", "connection");
            }

            @Override
            public void authenticated(XMPPConnection xmppConnection, boolean b) {
                // registerQbChatListeners();
            }


            @Override
            public void connectionClosed() {

            }

            @Override
            public void connectionClosedOnError(Exception e) {
                // connection closed on error. It will be established soon
            }

            @Override
            public void reconnectingIn(int seconds) {

            }

            @Override
            public void reconnectionSuccessful() {

            }

            @Override
            public void reconnectionFailed(Exception e) {

            }
        };


        QBChatService.getInstance().addConnectionListener(connectionListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        boolean isLoggedIn = chatService.isLoggedIn();
        if (!isLoggedIn) {
            return;
        }

        chatService.logout(new QBEntityCallback() {


            @Override
            public void onSuccess(Object o, Bundle bundle) {
                chatService.destroy();
            }

            @Override
            public void onError(QBResponseException errors) {
                Progress.stop();
            }
        });
    }
}
