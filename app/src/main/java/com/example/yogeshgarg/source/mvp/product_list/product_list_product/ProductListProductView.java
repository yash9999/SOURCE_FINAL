package com.example.yogeshgarg.source.mvp.product_list.product_list_product;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public interface ProductListProductView {
    public void onSuccessPLProduct(ArrayList<ProductListProductModel.Result> results);
    public void onUnsuccessPlProduct(String message);
    public void onInternetError();


    public void onSuccessPlProductPublish(int publish,int position);
    public void onUnsccuessPlProductPublish(String message);
    public void onInternetErrorPublish();
}
