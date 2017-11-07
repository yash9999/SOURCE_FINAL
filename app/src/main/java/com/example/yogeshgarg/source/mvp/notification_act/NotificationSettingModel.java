package com.example.yogeshgarg.source.mvp.notification_act;

/**
 * Created by yogeshgarg on 15/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationSettingModel {


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

        @SerializedName("sampling")
        @Expose
        private String sampling;
        @SerializedName("expiry")
        @Expose
        private String expiry;
        @SerializedName("new product")
        @Expose
        private String newProduct;
        @SerializedName("notification")
        @Expose
        private String notification;

        public String getSampling() {
            return sampling;
        }

        public void setSampling(String sampling) {
            this.sampling = sampling;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getNewProduct() {
            return newProduct;
        }

        public void setNewProduct(String newProduct) {
            this.newProduct = newProduct;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }


    }
}


