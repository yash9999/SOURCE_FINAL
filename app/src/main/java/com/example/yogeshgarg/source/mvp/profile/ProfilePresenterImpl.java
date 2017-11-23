package com.example.yogeshgarg.source.mvp.profile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.spdy.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yogeshgarg on 12/08/17.
 */

public class ProfilePresenterImpl implements ProfilePresenter {

    Activity activity;
    ProfileView profileView;
    JSONObject jsonObject;


    public ProfilePresenterImpl(Activity activity, ProfileView profileView) {
        this.activity = activity;
        this.profileView = profileView;
    }

    @Override
    public void callingProfileApi() {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfProfile();
        } catch (ApiAdapter.NoInternetException ex) {
            profileView.onInternetError();
        }

    }


    private void gettingResultOfProfile() {

        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProfileModel> getProfileResult = ApiAdapter.getApiService().resultOfProfile("application/json", "no-cache", body);

        getProfileResult.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {

                Progress.stop();
                try {
                    ProfileModel profileModel = response.body();


                    if (profileModel.getSuccessful()) {
                        profileView.onSuccessProfile(profileModel.getResult());
                    } else {
                        profileView.onUnsuccessProfile(activity.getString(R.string.user_is_not_authentic));
                    }
                } catch (NullPointerException exp) {
                    profileView.onUnsuccessProfile(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                profileView.onUnsuccessProfile(activity.getString(R.string.server_error));
            }
        });
    }


    @Override
    public void callingUploadProfilePicApi(File filePath) {
        try {
            ApiAdapter.getInstance(activity);
            gettingResultOfUploadingImage(filePath);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            profileView.onInternetErrorProfilePic();
        }
    }


    //multipart image upload api
    private void gettingResultOfUploadingImage(File filePath) {

        String uploadImageUrl = "image/user";


        String requestURL = Const.Base_URL + uploadImageUrl;
        RequestParams params = null;

        params = new RequestParams();

        try {
            params.put(Const.KEY_IMAGE, filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("exception", ex.getMessage());
        }

        final UserSession userSession = new UserSession(activity);
        String userToken = userSession.getUserToken();

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader(Const.KEY_X_API, Const.APP_X_API);
        client.addHeader(Const.KEY_USER_TOKEN, userToken);

        // client.addHeader("Content-Type", "multipart/form-data");

        final int DEFAULT_TIMEOUT = 90 * 1000;
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
                                profileView.onSuccessProfilePic("Product added successfully.");
                                callingProfileApi();
                            } else {
                                profileView.onUnsuccessProfilePic(message);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    profileView.onUnsuccessProfilePic("Not able to upload image this time, Please try again later.");
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Progress.stop();
                Log.e("status", "" + statusCode);
                if (headers != null) {
                    for (int i = 0; i < headers.length; i++)
                        Log.e("header", "" + headers[i]);
                    Log.e("finish", "finish");
                }
                profileView.onUnsuccessProfilePic("Not able to upload image this time, Please try again later.");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);


            }
        });

    }


    @Override
    public void callingUpdateProfileInfo(String firstName, String lastName, String email, String phone) {
        try {
            ApiAdapter.getInstance(activity);
            if (validation(firstName, lastName, email, phone)) {
                getResultOfUpdateInfo(firstName, lastName, email, phone);
            }
        } catch (ApiAdapter.NoInternetException ex) {
            profileView.onInternertErrorProfileInfo();
        }
    }

    private void getResultOfUpdateInfo(String firstName, String lastName, String email, String phone) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.KEY_FIRSTNAME, firstName);
            jsonObject.put(Const.KEY_LASTNAME, lastName);
            jsonObject.put(Const.KEY_EMAIL_ID, email);
            jsonObject.put(Const.KEY_PHONE, phone);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<ProfileInfoUpdateModel> getProfileResult = ApiAdapter.getApiService().getResultOfUpdateUserInfo("application/json", "no-cache", body);

        getProfileResult.enqueue(new Callback<ProfileInfoUpdateModel>() {
            @Override
            public void onResponse(Call<ProfileInfoUpdateModel> call, Response<ProfileInfoUpdateModel> response) {

                Progress.stop();
                try {
                    ProfileInfoUpdateModel profileInfoUpdateModel = response.body();


                    if (profileInfoUpdateModel.getSuccessful()) {
                        profileView.onSuccessProfileInfo();
                    } else {
                        profileView.onUnsuccessProfileInfo(profileInfoUpdateModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    profileView.onUnsuccessProfileInfo(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<ProfileInfoUpdateModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                profileView.onUnsuccessProfileInfo(activity.getString(R.string.server_error));
            }
        });
    }

    public boolean validation(String firstName, String lastName, String email, String phone) {

        if (Utils.isEmptyOrNull(firstName)) {
            profileView.onUnsuccessProfileInfo("Please enter your name.");
            return false;
        } else if (Utils.isEmptyOrNull(email)) {
            profileView.onUnsuccessProfileInfo("Please enter EmailId.");
            return false;
        } else if (Utils.isEmptyOrNull(phone)) {
            profileView.onUnsuccessProfileInfo("Please enter your phone number.");
            return false;
        }
        return true;
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }
}
