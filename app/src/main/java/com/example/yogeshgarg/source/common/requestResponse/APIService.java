package com.example.yogeshgarg.source.common.requestResponse;


import com.example.yogeshgarg.source.mvp.ProductUpdate.ProductUpdateModel;
import com.example.yogeshgarg.source.mvp.about.AboutModel;
import com.example.yogeshgarg.source.mvp.chatting.ChattingModel;
import com.example.yogeshgarg.source.mvp.chatting.SendingModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardPlanogramModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand.ExpiryProductBrandModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar.ExpiryProductCalendarModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home.ExpiryProductHomeModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product.ExpiryProduct_ProductModel;
import com.example.yogeshgarg.source.mvp.faq.FAQModel;
import com.example.yogeshgarg.source.mvp.forgot_password.ForgotPasswordModel;
import com.example.yogeshgarg.source.mvp.forgot_password.ForgotPasswordOtpModel;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_brand.InStoreBrandModel;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_calendar.InStoreCalendarModel;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_category.StoreCategoryModel;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_home.InStoreHomeModel;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_product.StoreProductModel;
import com.example.yogeshgarg.source.mvp.inbox.InboxModel;
import com.example.yogeshgarg.source.mvp.login.LoginModel;
import com.example.yogeshgarg.source.mvp.new_product.new_product_home.NewProductHomeModel;
import com.example.yogeshgarg.source.mvp.notification.MarkReadModel;
import com.example.yogeshgarg.source.mvp.notification.NotificationPushModel;
import com.example.yogeshgarg.source.mvp.notification_act.NotificationSettingModel;
import com.example.yogeshgarg.source.mvp.notification_act.NotificationSettingUpdateModel;
import com.example.yogeshgarg.source.mvp.price_analysis.PriceAnalysisModel;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;
import com.example.yogeshgarg.source.mvp.price_survey_brand.PriceSurveyBrandModel;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.BrandPublishModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_category.CategoryPublishModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_category.ProductListCategoryModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_product.ProductListProductModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_product.ProductPublishModel;
import com.example.yogeshgarg.source.mvp.profile.ProfileInfoUpdateModel;
import com.example.yogeshgarg.source.mvp.profile.ProfileModel;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordModel;
import com.example.yogeshgarg.source.mvp.stores.StoresModel;
import com.example.yogeshgarg.source.mvp.team.MyTeamModel;
import com.example.yogeshgarg.source.mvp.vacation.vacation_calendar.VacationCalendarModel;
import com.example.yogeshgarg.source.mvp.vacation.vacation_home.VacationHomeModel;


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

    @POST("category/brands")
    Call<PriceSurveyBrandModel> priceSurveyBrandList(
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


    @POST("planograms")
    Call<DashboardPlanogramModel> gettingResultOfPlanogram(
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

    @POST("messenger/users")
    Call<InboxModel> gettingInboxList(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //forgot password Api
    @POST("user/password/reset")
    Call<ForgotPasswordModel> forgotPassword(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    //forgotPasswordOtp Api
    @POST("user/password/verify")
    Call<ForgotPasswordOtpModel> forgotPasswordOtp(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //getting the result for profile
    @POST("user/profile")
    Call<ProfileModel> resultOfProfile(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //getting expiry product category
    @POST("location/categories")
    Call<ExpiryProductCategoryModel> resultOf_EP_Category(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    //getting Result of expiry Brand
    @POST("category/brands")
    Call<ExpiryProductBrandModel> resultOf_EP_Brand(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //Api for product name after selecting the category
    @POST("location/products")
    Call<ExpiryProduct_ProductModel> expiryProduct_Product(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("location/expiry/products")
    Call<ExpiryProductHomeModel> expiryProductHome(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //api for inStoreSampling home
    @POST("location/sampling/products")
    Call<InStoreHomeModel> instoreProductHome(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("category/brands")
    Call<InStoreBrandModel> resultOfSSBrand(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);



    //getting expiry product category
    @POST("location/categories")
    Call<StoreCategoryModel> resultOfStoreCategory(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //Api for product name after selecting the category
    @POST("location/products")
    Call<StoreProductModel> inStoreProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("vacations")
    Call<VacationHomeModel> getVacationResult(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("scans")
    Call<NewProductHomeModel> getNewProductResult(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //for reset password api
    @POST("user/password/update")
    Call<ResetPasswordModel> getResetResult(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //for reset password api
    @POST("user/update")
    Call<ProfileInfoUpdateModel> getResultOfUpdateUserInfo(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("location/product/minimal/update")
    Call<ProductUpdateModel> getResultOfUpdateProductInfo(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("location/expiry/product/add")
    Call<ExpiryProductCalendarModel> getResultOfAddExpiryProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("location/sampling/product/add")
    Call<InStoreCalendarModel> getResultOfAddInStoreSampling(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("vacation/add")
    Call<VacationCalendarModel> getResultOfAddVacation(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("vacation/delete")
    Call<ResponseBody> getResultOfDeleteVacation(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("location/products/costs")
    Call<PriceAnalysisModel> getResultOfPriceAnalysis(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //Token notification
    @POST("firebase/android/token/add")
    Call<ResponseBody> generateToken(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //push notification
    @POST("messages")
    Call<NotificationPushModel> pushNotification(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("message/mark")
    Call<MarkReadModel> markRead(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //notification setting update

    @POST("user/settings/update")
    Call<NotificationSettingUpdateModel> pushNotificSetting(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //


    //notification setting
    @POST("user/settings")
    Call<NotificationSettingModel> pushNotificationUpdate(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //productlist's Api
    @POST("location/categories")
    Call<ProductListCategoryModel> resultOfPLCategory(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("category/publish")
    Call<CategoryPublishModel> resultOfPLCategoryPublish(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("category/brands")
    Call<ProductListBrandModel> resultOfPLBrand(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("brand/publish")
    Call<BrandPublishModel> resultOfPLBrandPublish(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("location/products")
    Call<ProductListProductModel> resultOfPLProduct(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);



    @POST("location/product/publish/update")
    Call<ProductPublishModel> resultOfPLProductPublish(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    @POST("media/aboutus")
    Call<AboutModel> resultAboutUS(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);

    @POST("media/faq")
    Call<FAQModel> resultFAQ(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //first api for conversation
    @POST("messenger/conversation")
    Call<ChattingModel> conversation(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);



    //send message
    @POST("messenger/send")
    Call<SendingModel> sendMessage(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


    //send message
    @POST("messenger/messages")
    Call<ResponseBody> receivedMessage(
            @Header("Content-Type") String contentType,
            @Header("Cache-Control") String cache,
            @Body RequestBody params);


}

