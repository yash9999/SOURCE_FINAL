package com.example.yogeshgarg.source.common.push;

import android.util.Log;


import com.example.yogeshgarg.source.common.session.FcmSession;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Braintech on 8/8/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration tokerefreshedTokenn
        String refreshedToken  = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        FcmSession fcmSession = new FcmSession(this);
        fcmSession.saveFcmToken(token);
    }
}
