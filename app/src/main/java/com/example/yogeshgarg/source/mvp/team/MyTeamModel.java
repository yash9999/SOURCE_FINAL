package com.example.yogeshgarg.source.mvp.team;

/**
 * Created by himanshu on 29/07/17.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyTeamModel {

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

        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("store")
        @Expose
        private String store;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("imagelink")
        @Expose
        private String imagelink;
        @SerializedName("logged")
        @Expose
        private String logged;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("quickblox_id")
        @Expose
        private String quickbloxId;
        @SerializedName("role_name")
        @Expose
        private String roleName;
        @SerializedName("status")
        @Expose
        private String status;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getImagelink() {
            return imagelink;
        }

        public void setImagelink(String imagelink) {
            this.imagelink = imagelink;
        }

        public String getLogged() {
            return logged;
        }

        public void setLogged(String logged) {
            this.logged = logged;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQuickbloxId() {
            return quickbloxId;
        }

        public void setQuickbloxId(String quickbloxId) {
            this.quickbloxId = quickbloxId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
