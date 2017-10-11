package com.example.yogeshgarg.source.mvp.product_list.product_list_product;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public interface ProductListProductPresenter {
    public void callingProductApi(String brandId);
    public void callingProductPublish(int publish,int position,String productId);
}
