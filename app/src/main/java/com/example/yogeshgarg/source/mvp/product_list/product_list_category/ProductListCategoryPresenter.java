package com.example.yogeshgarg.source.mvp.product_list.product_list_category;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public interface ProductListCategoryPresenter {
    public void callingProductListCategoryApi();
    public void callingPublishApi(int publish,int position,String categoryId);
}
