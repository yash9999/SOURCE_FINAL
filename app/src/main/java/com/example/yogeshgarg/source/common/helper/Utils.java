package com.example.yogeshgarg.source.common.helper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        try {
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
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } finally {
            return categoryName;
        }

    }

    public static boolean matchString(String password) {

        if (password.matches("^[A-Za-z\\d$#@$!%*?&]{8,}")) {
            Log.e("Match", "match");
            return true;
        } else {
            Log.e("not Match", "not match");
            return false;
        }
    }

    public static String getRealPathFromURI(String contentURI, Context context) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static byte[] convertToByte(Bitmap bmp) {
        byte[] byteArray = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return byteArray;
    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String image = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return image;
    }

    public static byte[] convertBaseToByte(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return data;
    }

    public static Bitmap resizeImageForImageView(Bitmap bitmap, float rotation) {

        Bitmap resizedBitmap = null;
        int scaleSize = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth) {
            newHeight = scaleSize;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight) {
            newWidth = scaleSize;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth) {
            newHeight = scaleSize;
            newWidth = scaleSize;
        }

        Log.e("rotation", "" + rotation);

        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        //return Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true);

        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        return resizedBitmap;
    }

    public static Bitmap rotateImage(Bitmap bitmap, float rotation) {

        Bitmap resizedBitmap = null;
        int scaleSize = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);

    }

}
