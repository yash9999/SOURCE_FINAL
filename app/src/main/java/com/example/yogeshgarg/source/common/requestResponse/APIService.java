package com.example.yogeshgarg.source.common.requestResponse;



import com.example.yogeshgarg.source.mvp.dashboard.NewProductModel;
import com.example.yogeshgarg.source.mvp.login.LoginModel;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.example.yogeshgarg.source.mvp.stores.StoresModel;
import com.example.yogeshgarg.source.mvp.team.MyTeamModel;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Braintech on 18-May-16.
 */
public interface APIService {

   @POST("user/login ")
    Call<LoginModel> userLogin(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("locations")
    Call<StoresModel> storesList(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);



    @POST("location/categories")
    Call<PriceSurveyModel> priceSurveyCategoryList(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


 @POST("location/products")
 Call<PriceSurveyProductModel> gettingResultOfProduct(
         @Header("Content-Type") String contentType,
         @Header("Cache-Control") String cache,
         @Body RequestBody params);



 @POST("scans")
 Call<NewProductModel> gettingResultOfNewProductUpdate(
         @Header("Content-Type") String contentType,
         @Header("Cache-Control") String cache,
         @Body RequestBody params);

 @POST("location/products")
 Call<PriceSurveyProductModel> gettingResultOfResentPriceUpdateProduct(
         @Header("Content-Type") String contentType,
         @Header("Cache-Control") String cache,
         @Body RequestBody params);

 @POST("location/sampling/products")
 Call<PriceSurveyProductModel> gettingResultOfSamplingProduct(
         @Header("Content-Type") String contentType,
         @Header("Cache-Control") String cache,
         @Body RequestBody params);

 @POST("location/expiry/products")
 Call<PriceSurveyProductModel> gettingResultOfExpiryProduct(
         @Header("Content-Type") String contentType,
         @Header("Cache-Control") String cache,
         @Body RequestBody params);


 @POST("users")
 Call<MyTeamModel> gettingUsers(
         @Header("Content-Type") String contentType,
         @Header("Cache-Control") String cache,
         @Body RequestBody params);
}

