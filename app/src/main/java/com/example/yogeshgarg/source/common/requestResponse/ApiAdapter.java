package com.example.yogeshgarg.source.common.requestResponse;

import android.content.Context;


import com.example.yogeshgarg.source.BuildConfig;
import com.example.yogeshgarg.source.SourceApp;
import com.example.yogeshgarg.source.common.helper.NetworkUtil;
import com.example.yogeshgarg.source.common.session.UserSession;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Braintech on 6/10/2016.
 */
public class ApiAdapter {

    private static APIService sInstance;
    static String userToken = null;
    static UserSession userSession = null;

    private static Context sContext;

    public ApiAdapter(Context context) {
        sContext = context;
    }

    public static APIService getInstance(Context context) throws NoInternetException {
        userSession = new UserSession(context);

        if (userSession != null) {
            userToken = userSession.getUserToken();
        }

        if (NetworkUtil.isNetworkConnected(context)) {
            return getApiService();
        } else {
            throw new NoInternetException("No Internet connection available");
        }
    }


    public static class NoInternetException extends Exception {
        public NoInternetException(String message) {
            super(message);
        }
    }

    public static APIService getApiService() {
        if (sInstance == null) {
            synchronized (ApiAdapter.class) {
                if (sInstance == null) {

                    sInstance = new Retrofit.Builder()
                            .baseUrl(Const.Base_URL)
                            .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build()
                            .create(APIService.class);
                }
            }
        }
        return sInstance;
    }

    private static OkHttpClient getOkHttpClient() {
        File httpCacheDirectory = new File(SourceApp.getInstance().getCacheDir(), "responses");
        int cacheSize = 10*1024*1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().retryOnConnectionFailure(true).cache(cache);


        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                if (userSession != null) {
                    if (userSession.isUserLoggedIn()) {
                        requestBuilder.header(Const.KEY_X_API, Const.APP_X_API);
                        requestBuilder.header(Const.KEY_USER_TOKEN, userToken);
                    } else if(!userSession.isUserLoggedIn()) {
                        requestBuilder.header(Const.KEY_X_API, Const.APP_X_API);
                    }
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        builder.addInterceptor(interceptor);


        if (BuildConfig.DEBUG)
        {
            //Print Log
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.readTimeout(65000, TimeUnit.SECONDS)
                .

                        connectTimeout(65000, TimeUnit.SECONDS)
                .

                        build();

    }

}

