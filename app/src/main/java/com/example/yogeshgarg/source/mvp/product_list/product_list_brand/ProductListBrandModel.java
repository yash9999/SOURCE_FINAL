package com.example.yogeshgarg.source.mvp.product_list.product_list_brand;

/**
 * Created by yogeshgarg on 29/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductListBrandModel {


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

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("products")
        @Expose
        private String products;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("dateadded")
        @Expose
        private String dateadded;
        @SerializedName("completed")
        @Expose
        private Integer completed;
        @SerializedName("updateby")
        @Expose
        private String updateby;
        @SerializedName("updatefrom")
        @Expose
        private String updatefrom;
        @SerializedName("publish")
        @Expose
        private Integer publish;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

        public Integer getCompleted() {
            return completed;
        }

        public void setCompleted(Integer completed) {
            this.completed = completed;
        }

        public String getUpdateby() {
            return updateby;
        }

        public void setUpdateby(String updateby) {
            this.updateby = updateby;
        }

        public String getUpdatefrom() {
            return updatefrom;
        }

        public void setUpdatefrom(String updatefrom) {
            this.updatefrom = updatefrom;
        }

        public Integer getPublish() {
            return publish;
        }

        public void setPublish(Integer publish) {
            this.publish = publish;
        }

    }

}


