package com.example.yogeshgarg.source.mvp.dashboard.model;

/**
 * Created by himanshu on 29/07/17.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewProductModel implements Serializable{


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

    public class Result implements Serializable{

        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("location_id")
        @Expose
        private String locationId;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("scan_id")
        @Expose
        private String scanId;
        @SerializedName("upc")
        @Expose
        private String upc;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("dateadded")
        @Expose
        private String dateadded;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("cost")
        @Expose
        private String cost;
        @SerializedName("tax")
        @Expose
        private String tax;
        @SerializedName("link")
        @Expose
        private String link;

        public byte[] getLinkByte() {
            return linkByte;
        }

        public void setLinkByte(byte[] linkByte) {
            this.linkByte = linkByte;
        }

        byte[] linkByte;

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getScanId() {
            return scanId;
        }

        public void setScanId(String scanId) {
            this.scanId = scanId;
        }

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

    }
}
