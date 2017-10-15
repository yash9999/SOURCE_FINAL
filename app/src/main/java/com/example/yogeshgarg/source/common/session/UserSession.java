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






    public static final String KEY_USER_TOKEN = "user_token";
    public static final String KEY_OPPONENT_ID_CURRENT_USER = "opponent_id";
    public static final String KEY_FCM_TOKEN = "fcm_token";
    public static final String KEY_PERMISSION_DIALOG = "perm_dialog";
    public static final String KEY_QUIKBLOX_ID = "quikblox_id";

    public  static  final String KEY_USERIMAGE="";




    // Shared preferences file name
    private static final String PREF_NAME = "user_pref";


    // All Shared Preferences Keys
    private static final String IS_LOGIN = "is_user_logged_in";
    private static final String LOGIN_DATA = "login_data";
    private static final String KEY_USER_ID = "ID";
    private static final String KEY_TOKEN = "token";

    private static final String IS_LOCATION_SET = "is_location_set";


    private static String LOCATION_ID = "LocationId";
    private static String STORE_ADDRESS = "store_address";


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

    public void setStoreAddress(String storeAddress) {
        editor.putString(STORE_ADDRESS, storeAddress);
        editor.commit();
    }

    public String getStoreAddress() {
        return pref.getString(STORE_ADDRESS, "");
    }

    public String getLocationId() {
        return pref.getString(LOCATION_ID, "");
    }











    /**
     * Create login session
     */
    public void createUserSession(String user_token) {

        // Log.d("user_token",user_token);
        editor.putString(KEY_USER_TOKEN, user_token);
        editor.putBoolean(IS_LOGIN, true);

        // commit changes
        editor.commit();
    }

    public void createFCMToken(String fcmToken) {
        editor.putString(KEY_FCM_TOKEN, fcmToken);
        editor.commit();
    }

    public void setQuikBloxID(String userID) {
        editor.putString(KEY_QUIKBLOX_ID, userID);
        editor.commit();
    }

    public void setUserImage(String userimage){
        editor.putString(KEY_USERIMAGE, userimage);
        editor.commit();
    }

    public String getUserimage(){
        return pref.getString(KEY_USERIMAGE,"");
    }

    public String getQuikbloxId() {
        return pref.getString(KEY_QUIKBLOX_ID, "0");
    }

    public void setUserID(String userID) {
        editor.putString(KEY_USER_ID, userID);
        editor.commit();
    }

    public void setPermissionDialog(Boolean permStatus) {
        editor.putBoolean(KEY_PERMISSION_DIALOG, permStatus);
        editor.commit();
    }

    public void setOpponentID(String opponent_id) {
        editor.putString(KEY_OPPONENT_ID_CURRENT_USER, opponent_id);
        editor.commit();
    }

    public String getUserId() {
        return pref.getString(KEY_USER_ID, "");
    }


    public String getFCMToken() {
        return pref.getString(KEY_FCM_TOKEN, "");
    }

    public String getCurrentUserOpponentId() {
        return pref.getString(KEY_OPPONENT_ID_CURRENT_USER, "0");
    }

    public Boolean getPermissionDialogStatus() {
        return pref.getBoolean(KEY_PERMISSION_DIALOG, false);
    }

    /**
     * Clear session details
     */



    public void logout() { // Clearing all data from Shared

        editor.remove(IS_LOGIN);
        editor.commit();
    }




}
