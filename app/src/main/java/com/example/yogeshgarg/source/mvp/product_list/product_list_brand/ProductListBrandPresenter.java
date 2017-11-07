package com.example.yogeshgarg.source.mvp.product_list.product_list_brand;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public interface ProductListBrandPresenter {
    public void callingPLBrandApi(String categoryId);
    public void callingPlBrandPublish(int publish,int position,String brandId);
}
