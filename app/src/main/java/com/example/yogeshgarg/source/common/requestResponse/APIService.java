package com.example.yogeshgarg.source.common.requestResponse;


import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.example.yogeshgarg.source.mvp.login.LoginModel;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.example.yogeshgarg.source.mvp.stores.StoresModel;
import com.example.yogeshgarg.source.mvp.team.MyTeamModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Braintech on 18-May-16.
 */
public interface APIService {


    //Api for login
    @POST("user/login ")
    Call<LoginModel> userLogin(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    //Api for fetching the stote name
    @POST("locations")
    Call<StoresModel> storesList(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //Api for category name at product update
    @POST("location/categories")
    Call<PriceSurveyModel> priceSurveyCategoryList(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //Api for product name after selecting the category
    @POST("location/products")
    Call<PriceSurveyProductModel> gettingResultOfProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //Dasboard APis

    @POST("dashboard/scans")
    Call<NewProductModel> gettingResultOfNewProductUpdate(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("dashboard/location/products")
    Call<DashboardRecentUpdateModel> gettingResultOfResentPriceUpdateProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("dashboard/location/expiry/products")
    Call<DashboardExpiryProductModel> gettingResultOfExpiryProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("dashboard/location/sampling/products")
    Call<DashboardInStoreModel> gettingResultOfSamplingProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);



    //Api for My Team.

    @POST("users")
    Call<MyTeamModel> gettingUsers(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);
}

