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
        private ArrayList<ArrayList<ArrayList<Float>>> data = null;

        @SerializedName("title")
        @Expose
        private ArrayList<String> title = null;

        @SerializedName("original")
        @Expose
        private Original original;

        @SerializedName("creator")
        @Expose
        private ArrayList<Creator> creator = null;


        public ArrayList<ArrayList<ArrayList<Float>>> getData() {
            return data;
        }

        public void setData(ArrayList<ArrayList<ArrayList<Float>>> data) {
            this.data = data;
        }


        public ArrayList<String> getTitle() {
            return title;
        }

        public void setTitle(ArrayList<String> title) {
            this.title = title;
        }


        public Original getOriginal() {
            return original;
        }

        public void setOriginal(Original original) {
            this.original = original;
        }

        public ArrayList<Creator> getCreator() {
            return creator;
        }

        public void setCreator(ArrayList<Creator> creator) {
            this.creator = creator;
        }

        public class Creator {

            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("name")
            @Expose
            private String name;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

        public class Original {

            @SerializedName("brands")
            @Expose
            private ArrayList<Brand> brands = null;

            @SerializedName("stores")
            @Expose
            private ArrayList<Store> stores = null;

            @SerializedName("categories")
            @Expose
            private ArrayList<Category> categories = null;

            @SerializedName("products")
            @Expose
            private ArrayList<Product> products = null;

            public ArrayList<Brand> getBrands() {
                return brands;
            }

            public void setBrands(ArrayList<Brand> brands) {
                this.brands = brands;
            }

            public ArrayList<Store> getStores() {
                return stores;
            }

            public void setStores(ArrayList<Store> stores) {
                this.stores = stores;
            }

            public ArrayList<Category> getCategories() {
                return categories;
            }

            public void setCategories(ArrayList<Category> categories) {
                this.categories = categories;
            }

            public ArrayList<Product> getProducts() {
                return products;
            }

            public void setProducts(ArrayList<Product> products) {
                this.products = products;
            }

            public class Product {


                @SerializedName("product_id")
                @Expose
                private String productId;

                @SerializedName("brand_id")
                @Expose
                private String brandId;

                @SerializedName("category_id")
                @Expose
                private String category_id;

                @SerializedName("text")
                @Expose
                private String text;

                @SerializedName("tick")
                @Expose
                private boolean tick;


                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getBrandId() {
                    return brandId;
                }

                public void setBrandId(String brandId) {
                    this.brandId = brandId;
                }

                public String getCategory_id(){
                    return category_id;
                }

                public void setCategory_id(String category_id){
                    this.category_id=category_id;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public boolean getProductTick(){
                    return tick;
                }

                public  void setProductTick(boolean tick){
                    this.tick=tick;
                }


            }

            public class Store {

                @SerializedName("store_id")
                @Expose
                private String storeId;
                @SerializedName("text")
                @Expose
                private String text;
                @SerializedName("location_id")
                @Expose
                private String locationId;
                @SerializedName("tick")
                @Expose
                private boolean tick;

                public String getStoreId() {
                    return storeId;
                }

                public void setStoreId(String storeId) {
                    this.storeId = storeId;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getLocationId() {
                    return locationId;
                }

                public void setLocationId(String locationId) {
                    this.locationId = locationId;
                }

                public boolean getStoreTick(){
                    return tick;
                }

                public void setStoreTick(boolean tick){
                    this.tick=tick;
                }

            }

            public class Brand {

                @SerializedName("category_id")
                @Expose
                private String categoryId;
                @SerializedName("brand_id")
                @Expose
                private String brandId;
                @SerializedName("text")
                @Expose
                private String text;
                @SerializedName("tick")
                @Expose
                private boolean tick;


                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getBrandId() {
                    return brandId;
                }

                public void setBrandId(String brandId) {
                    this.brandId = brandId;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public boolean getBrandTick(){
                    return tick;
                }
                public void setBrandTick(boolean tick){
                    this.tick=tick;
                }

            }

            public class Category {

                @SerializedName("category_id")
                @Expose
                private String categoryId;

                @SerializedName("text")
                @Expose
                private String text;

                @SerializedName("tick")
                @Expose
                private boolean tick;


                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public boolean getCategoryTick(){
                    return tick;
                }
                public void setCategoryTick(boolean tick){
                    this.tick=tick;
                }

            }

        }
    }




}
