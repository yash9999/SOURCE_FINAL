package com.example.yogeshgarg.source.mvp.inbox;

/**
 * Created by yogeshgarg on 15/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InboxModel {


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
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("totalunread")
        @Expose
        private Integer totalunread;
        @SerializedName("date")
        @Expose
        private String date;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getTotalunread() {
            return totalunread;
        }

        public void setTotalunread(Integer totalunread) {
            this.totalunread = totalunread;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
