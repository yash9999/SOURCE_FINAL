package com.example.yogeshgarg.source.mvp.new_product.new_product_update;

import java.io.File;

/**
 * Created by yogeshgarg on 24/08/17.
 */

public interface NewProductUpdatePresenter {
    public void callingPictureApi(File filePath,String productName,String brandName,String uom,String upc,String stockMeasure,String stockUnit,String comment,String price,String discountType,String discount,String taxType);

}
