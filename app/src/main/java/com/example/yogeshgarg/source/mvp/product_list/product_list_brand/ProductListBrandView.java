package com.example.yogeshgarg.source.mvp.product_list.product_list_brand;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public interface ProductListBrandView {
    public void onSuccessPLBrand(ArrayList<ProductListBrandModel.Result> resultArrayList);
    public void onUnsuccessPLBrand(String message);
    public void onInternetError();

    public void onSuccessPlBrandPublish(int publish,int position);
    public void onUnsuccessBrandPublish(String message);
    public void onInternetErrorPublish();

}
