package com.example.yogeshgarg.source.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Braintech on 20-Jul-16.
 */
public class Utility {
    // email Validation
    public static boolean emailValidation(String email) {
        boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (emailValid) {
            return true;
        } else {
            return false;
        }
    }

    public static void showError(EditText editText, String msg, Activity activity) {
        editText.setError(msg);
        requestFocus(editText, activity);
    }

    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // for keyboard down
    public static void hideKeyboardIfOpen(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            return true;
        else
            return false;
    }


    //Scroll to
    public static void scrollToView(final View view, NestedScrollView scrollView) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollView, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollView.smoothScrollTo(0, childOffset.y);
    }

    private static void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    public static boolean isEmptyString(String data) {

        if (TextUtils.isEmpty(data) || data == null || data.equalsIgnoreCase("null"))
            return true;

        return false;
    }


    public static boolean isValidDate(String input) {

        //checking date validity, that selected date is not above then current date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        Date currentDate = null;
        Date selectedDate = null;
        try {
            currentDate = df.parse(formattedDate);
            selectedDate = df.parse(input);
        } catch (ParseException e) {
            // e.printStackTrace();
        }


        if (selectedDate.after(currentDate)) {
            return false;
        }


       /* //checking other validation
        String formatString = "MM/dd/yyyy";

        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(input);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
*/
        return true;
    }


    public static String convertDateFormatPost(String date) {
        // String date = "2011/11/12 16:05:06";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (Exception ex) {
            // ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        String newFormat = formatter.format(testDate);
        System.out.println(".....Date..." + newFormat);
        return newFormat;
    }

    public static String convertDateFormatGet(String date) {
        try {
            // String date = "2011/11/12 16:05:06";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
            Date testDate = null;
            try {
                testDate = sdf.parse(date);
            } catch (Exception ex) {
                //  ex.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String newFormat = formatter.format(testDate);
            System.out.println(".....Date..." + newFormat);
            return newFormat;
        } catch (NullPointerException e) {
            //e.printStackTrace();
            return "";
        }
    }



    public static String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        } else if (str != null && str.length() > 0 && str.charAt(str.length() - 2) == ',') {
            str = str.substring(0, str.length() - 2);
        }


        return str;
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public static void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(Color.parseColor("#33FFFFFF"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }


    public static String getTimeHHMMFormat(String sDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        Date testDate = null;
        String newFormat = "";
        try {
            testDate = sdf.parse(sDate);
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
            newFormat = formatter.format(testDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // System.out.println(".....Date..."+newFormat);

        return newFormat;


    }



}
