package com.example.yogeshgarg.source.mvp.in_store_sampling.store_category;

import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public class StoreCategoryModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("successful")
    @Expose
    private Boolean successful;
    @SerializedName("Result")
    @Expose
    private ArrayList<StoreCategoryModel.Result> result = null;

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

    public ArrayList<StoreCategoryModel.Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<StoreCategoryModel.Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("products")
        @Expose
        private String products;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("dateadded")
        @Expose
        private String dateadded;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getProducts() {
            return products;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

    }

}
