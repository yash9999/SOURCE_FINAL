package com.example.yogeshgarg.source.common.session;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Braintech on 9/13/2016.
 */
public class UserSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "user_pref";


    // All Shared Preferences Keys
    private static final String IS_LOGIN = "is_user_logged_in";
    private static final String LOGIN_DATA = "login_data";
    private static final String KEY_USER_ID = "ID";
    private static final String KEY_TOKEN = "token";

    private static final String IS_LOCATION_SET = "is_location_set";


    private static String LOCATION_ID = "LocationId";


    public UserSession(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    /**
     * Create login session
     */
    public void createUserSession(String id, String token) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_TOKEN, token);

        editor.commit();
    }


    public void clearUserSession() { // Clearing all data from Shared
        editor.clear();
        editor.commit();
    }


    // Get Login State
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);

    }

    public void setLocationStatus() {
        editor.putBoolean(IS_LOCATION_SET, true);
        editor.commit();
    }

    public boolean isLocationStatusSet() {
        return pref.getBoolean(IS_LOCATION_SET, false);
    }

    public String getUserToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void setLocationId(String locationId) {
        editor.putString(LOCATION_ID, locationId);
        editor.commit();
    }

    public String getLocationId() {
        return pref.getString(LOCATION_ID, "");
    }

}
