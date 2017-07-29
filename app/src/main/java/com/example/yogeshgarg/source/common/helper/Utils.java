package com.example.yogeshgarg.source.common.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.DecimalFormat;

/**
 * Created by Braintech on 9/12/2016.
 */
public class Utils {

    // for keyboard down
    public static void hideKeyboardIfOpen(Activity activity) {

        View view = activity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static Boolean isEmptyOrNull(String value) {
        if (value == null || value.isEmpty())
            return true;
        else
            return false;
    }

    public static String isEmptyOrNullSetData(String value) {

        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return value;
        }
    }


    public static String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public static Spanned fromHtml(String text) {
        Spanned result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(text);
        }
        return result;
    }

    public static Boolean isVisibleOrNull(Fragment fragment) {
        if (fragment != null) {
            if (fragment.isVisible())
                return true;
            else
                return false;
        } else
            return false;


    }


    public static void showError(AppCompatEditText editText, String msg, Activity activity) {
        editText.setError(msg);
        requestFocus(editText, activity);
    }

    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public static String currencyFormat(String sum) {
        Double doubleSum = Double.parseDouble(sum);
        String resultSum = null;

        if (doubleSum > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            resultSum = "$" + decimalFormat.format(doubleSum);
        } else {
            resultSum = "$0.00";
        }
        return resultSum;
    }

    public static String convertIntoDouble(String sum) {
        Double doubleSum = Double.parseDouble(sum);
        String resultSum = null;

        if (doubleSum > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            resultSum = decimalFormat.format(doubleSum);
        } else {
            resultSum = "0.00";
        }
        return resultSum;
    }

    public static String camelCasing(String categoryName) {

        String[] words = categoryName.split(" ");
        StringBuilder sb = new StringBuilder();
        if (words[0].length() > 0) {
            sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
            for (int i = 1; i < words.length; i++) {
                sb.append(" ");
                sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
            }
        }
        return sb.toString();
    }

}
