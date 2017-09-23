package com.example.yogeshgarg.source.mvp.ProductUpdate;

/**
 * Created by yogeshgarg on 22/07/17.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductUpdateModel {


        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("successful")
        @Expose
        private Boolean successful;
        @SerializedName("Result")
        @Expose
        private ArrayList<Object> result = null;

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

        public ArrayList<Object> getResult() {
            return result;
        }

        public void setResult(ArrayList<Object> result) {
            this.result = result;
        }

    }

