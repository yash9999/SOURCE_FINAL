package com.example.yogeshgarg.source.mvp.price_survey;

/**
 * Created by yogeshgarg on 20/07/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PriceSurveyModel {


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
        @SerializedName("completed")
        @Expose
        private Integer completed;
        @SerializedName("updateby")
        @Expose
        private String updateby;
        @SerializedName("updatefrom")
        @Expose
        private String updatefrom;
        @SerializedName("brands")
        @Expose
        private String brands;

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

        public String getBrands() {
            return brands;
        }

        public void setBrands(String brands) {
            this.brands = brands;
        }

    }
}


