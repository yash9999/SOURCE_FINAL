package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public interface EPHomeView {
    public void onSuccess(ArrayList<ExpiryProductHomeModel.Result> results);

    public void onUnsuccess(String message);

    public void onInternetError();
}
