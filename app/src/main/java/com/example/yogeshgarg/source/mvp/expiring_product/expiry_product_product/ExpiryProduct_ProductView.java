package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public interface ExpiryProduct_ProductView {
    public void onSuccess(ArrayList<ExpiryProduct_ProductModel.Result> results);
    public void onUnsuccess(String message);
    public void onInternetError();
}
