package com.example.yogeshgarg.source.common.quickblox;

import android.content.Context;
import android.os.Bundle;


import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;

/**
 * Created by Braintech on 14-Sep-16.
 */
public class QuickbloxSession {

    public void startSession(Context context){
        QBSettings.getInstance().init(context, Const.APP_ID, Const.AUTH_KEY, Const.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(Const.ACCOUNT_KEY);


        QBAuth.createSession(new QBEntityCallback<QBSession>() {

            @Override
            public void onSuccess(QBSession session, Bundle params) {


            }

            @Override
            public void onError(QBResponseException errors) {
                errors.printStackTrace();

            }
        });
    }
}
