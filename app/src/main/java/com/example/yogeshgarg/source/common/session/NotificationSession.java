package com.example.yogeshgarg.source.common.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yogeshgarg on 17/09/17.
 */

public class NotificationSession {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "user_pref";


    public static String str[] = new String[20];

    public NotificationSession(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setNotificationId(int position, String id) {
        editor.putString(str[position], id);
        editor.commit();
    }

    public String getNotificationId(int position) {
        return pref.getString(str[position], "");
    }

    public int getArraySize() {
        return  str.length;
    }

    public void clearUserSession() { // Clearing all data from Shared
        editor.clear();
        editor.commit();
    }
}
