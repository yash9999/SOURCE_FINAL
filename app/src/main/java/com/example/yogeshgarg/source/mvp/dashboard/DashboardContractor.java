package com.example.yogeshgarg.source.mvp.dashboard;

import com.example.yogeshgarg.source.common.BaseView;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;

import java.util.ArrayList;

/**
 * Created by himanshu on 29/07/17.
 */

public interface DashboardContractor {


    public interface Presenter {
        public void getNewProduct();
        public void getRecentPriceChangeProduct();
        public void getSamplingProduct();
        public void getExpiryProduct();
    }

    public interface View extends BaseView{
        public void getNewProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct);
        public void getRecentPriceChangedProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct);
        public void getExpiryProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct);
        public void getSamplingProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct);
    }
}
