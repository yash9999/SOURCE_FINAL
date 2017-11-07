package com.example.yogeshgarg.source.mvp.price_analysis;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 10/09/17.
 */

public interface PriceAnalysisPresenter {
    public void callingPriceAnalysisApi(ArrayList<String> arrayListCategory,ArrayList<String> arrayListProduct,
                                        ArrayList<String> arrayListBrand,ArrayList<String> arrayListStore);


}
