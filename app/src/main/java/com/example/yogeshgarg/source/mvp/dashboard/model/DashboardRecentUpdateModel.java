package com.example.yogeshgarg.source.mvp.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogeshgarg on 30/07/17.
 */

public class DashboardRecentUpdateModel {


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

        @SerializedName("category_name")
        @Expose
        private String categoryName;

        @SerializedName("category_id")
        @Expose
        private String categoryId;

        @SerializedName("brand_name")
        @Expose
        private String brandName;

        @SerializedName("brand_id")
        @Expose
        private String brandId;

        @SerializedName("product_name")
        @Expose
        private String productName;

        @SerializedName("product_id")
        @Expose
        private String productId;

        @SerializedName("rangetype")
        @Expose
        private String rangetype;

        @SerializedName("rangestart")
        @Expose
        private String rangestart;

        @SerializedName("rangeend")
        @Expose
        private String rangeend;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("upc")
        @Expose
        private String upc;

        @SerializedName("weight")
        @Expose
        private String weight;

        @SerializedName("cost")
        @Expose
        private String cost;

        @SerializedName("tax")
        @Expose
        private String tax;

        @SerializedName("discount")
        @Expose
        private String discount;

        @SerializedName("instore")
        @Expose
        private String instore;

        @SerializedName("tax_value")
        @Expose
        private String taxValue;

        @SerializedName("currency_name")
        @Expose
        private String currencyName;

        @SerializedName("currency_id")
        @Expose
        private String currencyId;

        @SerializedName("dateadded")
        @Expose
        private String dateadded;

        @SerializedName("user_id")
        @Expose
        private String userId;

        @SerializedName("first_name")
        @Expose
        private String firstName;

        @SerializedName("last_name")
        @Expose
        private String lastName;

        @SerializedName("profile_pic")
        @Expose
        private String profilePic;

        @SerializedName("lastupdated")
        @Expose
        private String lastupdated;

        @SerializedName("stocklastupdated")
        @Expose
        private String stocklastupdated;

        @SerializedName("stock")
        @Expose
        private String stock;

        @SerializedName("stock_unit_measure")
        @Expose
        private String stockUnitMeasure;

        @SerializedName("item_unit_measure")
        @Expose
        private String itemUnitMeasure;

        @SerializedName("type")
        @Expose
        private String type;


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

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

        public String getRangetype() {
            return rangetype;
        }

        public void setRangetype(String rangetype) {
            this.rangetype = rangetype;
        }

        public String getRangestart() {
            return rangestart;
        }

        public void setRangestart(String rangestart) {
            this.rangestart = rangestart;
        }

        public String getRangeend() {
            return rangeend;
        }

        public void setRangeend(String rangeend) {
            this.rangeend = rangeend;
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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getInstore() {
            return instore;
        }

        public void setInstore(String instore) {
            this.instore = instore;
        }

        public String getTaxValue() {
            return taxValue;
        }

        public void setTaxValue(String taxValue) {
            this.taxValue = taxValue;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getLastupdated() {
            return lastupdated;
        }

        public void setLastupdated(String lastupdated) {
            this.lastupdated = lastupdated;
        }

        public String getStocklastupdated() {
            return stocklastupdated;
        }

        public void setStocklastupdated(String stocklastupdated) {
            this.stocklastupdated = stocklastupdated;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }



}
