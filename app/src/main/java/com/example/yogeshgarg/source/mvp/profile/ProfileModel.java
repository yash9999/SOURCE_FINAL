package com.example.yogeshgarg.source.mvp.profile;

/**
 * Created by yogeshgarg on 12/08/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

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

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("lastnotificationseen")
        @Expose
        private String lastnotificationseen;
        @SerializedName("lastmessageseen")
        @Expose
        private String lastmessageseen;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("user")
        @Expose
        private String user;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLastnotificationseen() {
            return lastnotificationseen;
        }

        public void setLastnotificationseen(String lastnotificationseen) {
            this.lastnotificationseen = lastnotificationseen;
        }

        public String getLastmessageseen() {
            return lastmessageseen;
        }

        public void setLastmessageseen(String lastmessageseen) {
            this.lastmessageseen = lastmessageseen;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

    }


}
