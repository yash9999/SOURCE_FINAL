package com.example.yogeshgarg.source.mvp.new_product.new_product_home;

import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 20/08/17.
 */

public interface NewProductHomeView {
    public void onSuccess(ArrayList<NewProductHomeModel.Result> resultArrayList);
    public void onUnsuccess(String message);
    public void onInternetError();
}
