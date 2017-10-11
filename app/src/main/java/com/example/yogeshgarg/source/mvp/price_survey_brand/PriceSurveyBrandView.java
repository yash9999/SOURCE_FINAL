package com.example.yogeshgarg.source.mvp.price_survey_brand;

import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandModel;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 03/10/17.
 */

public interface PriceSurveyBrandView {
    public void onSuccessPLBrand(ArrayList<PriceSurveyBrandModel.Result> resultArrayList);
    public void onUnsuccessPLBrand(String message);
    public void onInternetError();

}
