package com.example.yogeshgarg.source.mvp.product_list.product_list_category;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public interface ProductListCategoryView {

    public void onSuccessProductListCategory(ArrayList<ProductListCategoryModel.Result> resultArrayList);
    public void onUnsuccessProductListCategory(String message);
    public void onInternetError();

    public void onSuccessPublish(int publish,int position);
    public void onUnsuccessPublish(String message);
    public void onInternetErrorPublish();
}
