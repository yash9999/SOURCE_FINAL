package com.example.yogeshgarg.source.mvp.stores;

/**
 * Created by yogeshgarg on 19/07/17.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoresModel {


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

        @SerializedName("enterprise_id")
        @Expose
        private String enterpriseId;

        @SerializedName("company_id")
        @Expose
        private String companyId;

        @SerializedName("company_name")
        @Expose
        private
        String companyName;

        @SerializedName("store_id")
        @Expose
        private String storeId;

        @SerializedName("location_id")
        @Expose
        private String locationId;

        @SerializedName("address")
        @Expose
        private String address;

        @SerializedName("longitude")
        @Expose
        private String longitude;

        @SerializedName("latitude")
        @Expose
        private String latitude;

        @SerializedName("city")
        @Expose
        private String city;

        @SerializedName("country")
        @Expose
        private String country;

        @SerializedName("imagelink")
        @Expose
        private String imagelink;

        @SerializedName("del")
        @Expose
        private String del;

        @SerializedName("dateadded")
        @Expose
        private String dateadded;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getImagelink() {
            return imagelink;
        }

        public void setImagelink(String imagelink) {
            this.imagelink = imagelink;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

    }





}
