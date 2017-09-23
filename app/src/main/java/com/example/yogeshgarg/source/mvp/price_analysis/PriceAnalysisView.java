package com.example.yogeshgarg.source.mvp.price_analysis;

/**
 * Created by yogeshgarg on 10/09/17.
 */

public interface PriceAnalysisView {
    public void onSuccess(PriceAnalysisModel.Result result);
    public void onUnsuccess(String message);
    public void onInternetError();
}
