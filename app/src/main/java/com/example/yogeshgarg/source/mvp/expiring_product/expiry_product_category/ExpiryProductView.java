package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category;

import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryModel;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public interface ExpiryProductView {

    public void onSuccessExpiryCategory(ArrayList<ExpiryProductCategoryModel.Result> resultArrayList);

    public void onUnsuccessExpiryCategory(String message);

    public void onInternetErrorExpiryCategory();

}
