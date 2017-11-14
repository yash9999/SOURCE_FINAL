package com.example.yogeshgarg.source.mvp.login;

/**
 * Created by yogeshgarg on 06/06/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class LoginModel {



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

            @SerializedName("id")
            @Expose
            private String id;

            @SerializedName("Token")
            @Expose
            private String token;

            @SerializedName("quickblox_id")
            @Expose
            private String quickbloxId;

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("usertype")
            @Expose
            private String usertype;

            @SerializedName("quickblox_token")
            @Expose
            private String quickbloxToken;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getQuickbloxId() {
                return quickbloxId;
            }

            public void setQuickbloxId(String quickbloxId) {
                this.quickbloxId = quickbloxId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            public String getQuickbloxToken() {
                return quickbloxToken;
            }

            public void setQuickbloxToken(String quickbloxToken) {
                this.quickbloxToken = quickbloxToken;
            }

        }

    }






