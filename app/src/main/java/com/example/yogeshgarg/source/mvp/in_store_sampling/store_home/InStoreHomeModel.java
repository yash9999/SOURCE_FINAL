package com.example.yogeshgarg.source.mvp.in_store_sampling.store_home;

/**
 * Created by yogeshgarg on 13/08/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InStoreHomeModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("successful")
    @Expose
    private Boolean successful;
    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result = null;

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

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("location_id")
        @Expose
        private String locationId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("upc")
        @Expose
        private String upc;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("sampling_date")
        @Expose
        private String samplingDate;
        @SerializedName("currency_name")
        @Expose
        private String currencyName;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("cost")
        @Expose
        private String cost;
        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("dateadded")
        @Expose
        private String dateadded;
        @SerializedName("stock_unit_measure")
        @Expose
        private String stockUnitMeasure;
        @SerializedName("item_unit_measure")
        @Expose
        private String itemUnitMeasure;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getSamplingDate() {
            return samplingDate;
        }

        public void setSamplingDate(String samplingDate) {
            this.samplingDate = samplingDate;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

        public String getStockUnitMeasure() {
            return stockUnitMeasure;
        }

        public void setStockUnitMeasure(String stockUnitMeasure) {
            this.stockUnitMeasure = stockUnitMeasure;
        }

        public String getItemUnitMeasure() {
            return itemUnitMeasure;
        }

        public void setItemUnitMeasure(String itemUnitMeasure) {
            this.itemUnitMeasure = itemUnitMeasure;
        }

    }


}
