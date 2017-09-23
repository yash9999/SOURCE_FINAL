package com.example.yogeshgarg.source.mvp.in_store_sampling.store_category;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public interface StoreCategoryView {

    public void onSuccess(ArrayList<StoreCategoryModel.Result> results);
    public void onUnsuccess(String message);
    public void onInternetError();

}
