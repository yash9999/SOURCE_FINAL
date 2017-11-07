package com.example.yogeshgarg.source.mvp.ProductUpdate;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public interface ProductUpdatePresenter {

    public void callingProductUpdateApi(String productId,String stock,String stockUnit,String price,
                                        String discount,String discountType,
                                        String taxType,String comment,String instore);
}
