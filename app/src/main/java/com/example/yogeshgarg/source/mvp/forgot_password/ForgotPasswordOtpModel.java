package com.example.yogeshgarg.source.mvp.forgot_password;

/**
 * Created by yogeshgarg on 12/08/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForgotPasswordOtpModel {


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
