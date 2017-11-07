package com.example.yogeshgarg.source.mvp.in_store_sampling.store_brand;

import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand.ExpiryProductBrandModel;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 03/10/17.
 */

public interface InStoreBrandView {

    public void onSuccessSSBrand(ArrayList<InStoreBrandModel.Result> resultArrayList);

    public void onUnsuccessSSBrand(String message);

    public void onInternetError();
}
