package com.example.yogeshgarg.source.mvp.in_store_sampling.store_product;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public interface StoreProductView {
    public void onSuccess(ArrayList<StoreProductModel.Result> results);
    public void onUnsuccess(String message);
    public void onInternetError();

}
