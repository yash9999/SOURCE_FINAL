package com.example.yogeshgarg.source.mvp.price_survey;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 20/07/17.
 */

public interface PriceSurveyView
{
    public void onSuccessCategory(ArrayList<PriceSurveyModel.Result> resultArrayList);
    public void onUnsccuessCategory(String message);
    public void onInternetError();
}
