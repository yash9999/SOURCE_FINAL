package com.example.yogeshgarg.source.common.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yogeshgarg on 14/09/17.
 */

public class FcmSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    public static final String FCM_TOKEN = "fcmToken";
    public static final String FCM_COUNT = "fcmCount";

    // Shared preferences file name
    private static final String PREF_NAME = "fcm_pref";

    public FcmSession(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveFcmToken(String token) {
        editor.putString(FCM_TOKEN, token);
        editor.commit();
    }

    public String getFcmToken() {
        return pref.getString(FCM_TOKEN, null);
    }

    public void saveFcmCount(int count) {
        editor.putInt(FCM_COUNT, count);
        editor.commit();
    }

    public int getFcmCount() {
        return pref.getInt(FCM_COUNT,0);
    }



}
