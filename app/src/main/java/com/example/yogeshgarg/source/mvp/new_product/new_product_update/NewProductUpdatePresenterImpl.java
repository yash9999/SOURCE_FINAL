package com.example.yogeshgarg.source.mvp.new_product.new_product_update;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by yogeshgarg on 24/08/17.
 */

public class NewProductUpdatePresenterImpl implements NewProductUpdatePresenter {

    Activity activity;
    NewProductUpdateView newProductUpdateView;

    public NewProductUpdatePresenterImpl(Activity activity, NewProductUpdateView newProductUpdateView) {
        this.activity = activity;
        this.newProductUpdateView = newProductUpdateView;
    }


    @Override
    public void callingPictureApi(File filePath, String productName, String brandName, String uom, String upc, String stockMeasure, String stockUnit, String comment, String price, String discountType, String discount, String taxType) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(filePath, productName, brandName, uom, upc, stockMeasure, stockUnit, comment, price, discountType, discount, taxType)) {
                gettingResultOfUploadingImage(filePath, productName, brandName, uom, upc, stockMeasure + stockUnit, comment, price, discountType, discount, taxType);
            }
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            newProductUpdateView.onInternetErrorPicture();
        }
    }


    private void gettingResultOfUploadingImage(File filePath, String productName, String brandName,
                                               String uom, String upc, String stockMeasure,
                                               String comment, String price, String discountType,
                                               String discount, String taxType) {

        String uploadImageUrl = "scan/add";

        if (Utils.isEmptyOrNull(discountType)) {
            discountType = "0";
        }


        String requestURL = Const.Base_URL + uploadImageUrl;

        UserSession userSession = new UserSession(activity);
        final String locationId = userSession.getLocationId();
        String userToken = userSession.getUserToken();

        RequestParams params = null;

        params = new RequestParams();

        Log.e("path", "" + filePath);
        Log.e("product name", productName);
        Log.e("brandname", brandName);
        Log.e("uom", uom);
        Log.e("upc", upc);
        Log.e("stockMeasure", stockMeasure);
        Log.e("comment", comment);
        Log.e("price", price);
        Log.e("discoun type", discountType);
        Log.e("discount", discount);
        Log.e("Tax type", taxType);
        Log.e("Locationid", locationId);
        Log.e("user token", userToken);
        try {
            params.put(Const.KEY_IMAGE, filePath);
            params.put(Const.KEY_NAME, productName);
            params.put(Const.KEY_BRAND, brandName);
            params.put(Const.KEY_LOCATIONID_REAL, locationId);
            params.put(Const.KEY_UOM, uom);
            params.put(Const.KEY_UPC, upc);
            params.put(Const.KEY_STOCK, stockMeasure);
            params.put(Const.KEY_COMMENT, comment);
            params.put(Const.KEY_COST, price);
            params.put(Const.KEY_DISCOUNT_TYPE, discountType);
            params.put(Const.KEY_DISCOUNT, discount);
            params.put(Const.KEY_TAX_TYPE, taxType);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("exception", " Exception generated");
        }


        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader(Const.KEY_X_API, Const.APP_X_API);
        client.addHeader(Const.KEY_USER_TOKEN, userToken);

        // client.addHeader("Content-Type", "multipart/form-data");

        final int DEFAULT_TIMEOUT = 60 * 1000;
        client.setTimeout(DEFAULT_TIMEOUT);

        client.post(requestURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Progress.start(activity);

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Progress.stop();
                Log.e("status", "" + statusCode);
                Log.e("response", "" + responseBody.toString());
                if (headers != null) {
                    for (int i = 0; i < headers.length; i++)
                        Log.e("header", "" + headers[i]);
                    Log.e("success finish", "finish");

                    try {
                        String str = new String(responseBody, "UTF-8");
                        Log.e("responseBody", "" + str);
                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            String message = jsonObject.get("message").toString();
                            String success = jsonObject.get("successful").toString();
                            Boolean bit= Boolean.valueOf(success);
                            if (bit) {
                                newProductUpdateView.onSuccessPictureApi("Product added successfully.");
                            } else {
                                newProductUpdateView.onUnsuccessPictureApi(message);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    newProductUpdateView.onUnsuccessPictureApi("Not able to upload image this time, Please try again later.");
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers,
                                  byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Progress.stop();
                Log.e("status", "" + statusCode);
                if (headers != null) {
                    for (int i = 0; i < headers.length; i++)
                        Log.e("header", "" + headers[i]);
                    Log.e("finish", "finish");
                }
                newProductUpdateView.onUnsuccessPictureApi("Not able to upload image this time, Please try again later.");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);


            }
        });

    }


    private boolean validation(File filePath, String productName, String brandName, String uom, String upc, String stockMeasure, String stockUnit,
                               String comment, String price, String discountType,
                               String discount, String taxType) {

        if (filePath == null) {
            newProductUpdateView.onUnsuccessPictureApi("Please select Image.");
            return false;
        } else if (!filePath.exists()) {
            newProductUpdateView.onUnsuccessPictureApi("Please select Image.");
            return false;
        } else if (Utils.isEmptyOrNull(productName)) {
            newProductUpdateView.onUnsuccessPictureApi("Please enter product name.");
            return false;
        } else if (Utils.isEmptyOrNull(brandName)) {
            newProductUpdateView.onUnsuccessPictureApi("Please enter brand name.");
            return false;
        } else if (Utils.isEmptyOrNull(uom)) {
            newProductUpdateView.onUnsuccessPictureApi("Please enter UOM.");
            return false;
        } else if (Utils.isEmptyOrNull(upc)) {
            newProductUpdateView.onUnsuccessPictureApi("Please enter UPC.");
            return false;
        } else if (Utils.isEmptyOrNull(price)) {
            newProductUpdateView.onUnsuccessPictureApi("Please enter price.");
            return false;
        }
        return true;
    }


}

