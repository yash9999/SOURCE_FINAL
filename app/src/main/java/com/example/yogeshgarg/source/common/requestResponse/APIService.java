package com.example.yogeshgarg.source.common.requestResponse;



import com.example.yogeshgarg.source.mvp.login.LoginModel;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.example.yogeshgarg.source.mvp.stores.StoresModel;

import okhttp3.RequestBody;
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

}

