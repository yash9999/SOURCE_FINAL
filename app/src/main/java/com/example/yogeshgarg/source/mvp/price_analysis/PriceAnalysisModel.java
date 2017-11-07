package com.example.yogeshgarg.source.mvp.price_analysis;

/**
 * Created by yogeshgarg on 10/09/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PriceAnalysisModel {


    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("successful")
    @Expose
    private Boolean successful;

    @SerializedName("Result")
    @Expose
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("data")
        @Expose
        private ArrayList<ArrayList<ArrayList<Integer>>> data = null;

        @SerializedName("title")
        @Expose
        private ArrayList<String> title = null;

        @SerializedName("original")
        @Expose
        private ArrayList<Original> original = null;

        public ArrayList<ArrayList<ArrayList<Integer>>> getData() {
            return data;
        }

        public void setData(ArrayList<ArrayList<ArrayList<Integer>>> data) {
            this.data = data;
        }

        public ArrayList<String> getTitle() {
            return title;
        }

        public void setTitle(ArrayList<String> title) {
            this.title = title;
        }

        public ArrayList<Original> getOriginal() {
            return original;
        }

        public void setOriginal(ArrayList<Original> original) {
            this.original = original;
        }

        public class Original {

            @SerializedName("name")
            @Expose
            private String name;


            @SerializedName("location_id")
            @Expose
            private String locationId;

            @SerializedName("dateadded")
            @Expose
            private String dateadded;


            @SerializedName("brand_id")
            @Expose
            private String brandId;

            @SerializedName("brand_name")
            @Expose
            private String brandName;

            @SerializedName("brand_name_tick")
            @Expose
            private boolean brand_name_tick;


            public boolean getBrandTick(){
                return brand_name_tick;
            }
            public void setBrandTick(boolean value){
                this.brand_name_tick=value;
            }

            @SerializedName("category_id")
            @Expose
            private String categoryId;

            @SerializedName("category_name")
            @Expose
            private String categoryName;

            @SerializedName("category_name_tick")
            @Expose
            private boolean category_name_tick;

            public boolean getCategoryTick(){
                return category_name_tick;
            }
            public void setCategoryTick(boolean value){
                this.category_name_tick=value;
            }


            @SerializedName("store_id")
            @Expose
            private String storeId;

            @SerializedName("store_name")
            @Expose
            private String storeName;

            @SerializedName("store_name_tick")
            @Expose
            private boolean store_name_tick;

            public boolean getStoreTick(){
                return store_name_tick;
            }
            public void setStoreTick(boolean value){
                this.store_name_tick=value;
            }


            @SerializedName("product_name")
            @Expose
            private String productName;

            @SerializedName("product_id")
            @Expose
            private String productId;



            @SerializedName("product_name_tick")
            @Expose
            private boolean product_name_tick;

            public boolean getProductTick(){
                return product_name_tick;
            }
            public void setProductTick(boolean value){
                this.product_name_tick=value;
            }


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }



            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }



            public String getLocationId() {
                return locationId;
            }

            public void setLocationId(String locationId) {
                this.locationId = locationId;
            }



            public String getDateadded() {
                return dateadded;
            }

            public void setDateadded(String dateadded) {
                this.dateadded = dateadded;
            }



            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }



            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }


            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }


            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }


            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }


            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }


            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

        }

    }

}
