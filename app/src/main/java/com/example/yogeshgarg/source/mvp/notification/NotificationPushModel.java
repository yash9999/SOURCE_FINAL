package com.example.yogeshgarg.source.mvp.notification;

/**
 * Created by yogeshgarg on 14/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationPushModel {




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

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("store_name")
            @Expose
            private String storeName;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("dateadded")
            @Expose
            private String dateadded;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("read")
            @Expose
            private Integer read;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDateadded() {
                return dateadded;
            }

            public void setDateadded(String dateadded) {
                this.dateadded = dateadded;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getRead() {
                return read;
            }

            public void setRead(Integer read) {
                this.read = read;
            }

        }
    }

