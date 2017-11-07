package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 30/09/17.
 */

public interface ExpiryProductBrandView {
    public void onSuccessEPBrand(ArrayList<ExpiryProductBrandModel.Result> resultArrayList);

    public void onUnsuccessEPBrand(String message);

    public void onInternetError();
}
