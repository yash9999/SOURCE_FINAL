package com.example.yogeshgarg.source.mvp.price_survey_product;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 21/07/17.
 */

public interface PriceSurveyProductView {

    public void onSuccessPriceSurveyProduct(ArrayList<PriceSurveyProductModel.Result> resultArrayList);
    public void onUnsuccessPriceSurveyProduct(String message);
    public void onInternetErrorPriceSurveyProduct();
}
