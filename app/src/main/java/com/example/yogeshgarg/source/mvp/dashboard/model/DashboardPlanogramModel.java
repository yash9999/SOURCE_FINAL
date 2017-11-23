package com.example.yogeshgarg.source.mvp.dashboard.model;

/**
 * Created by yogeshgarg on 29/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardPlanogramModel implements Serializable{


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

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("planogram_id")
        @Expose
        private String planogramId;

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("link")
        @Expose
        private String link;

        @SerializedName("dateadded")
        @Expose
        private String dateadded;

        byte[] linkByte;

        public byte[] getLinkByte() {
            return linkByte;
        }

        public void setLinkByte(byte[] linkByte) {
            this.linkByte = linkByte;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlanogramId() {
            return planogramId;
        }

        public void setPlanogramId(String planogramId) {
            this.planogramId = planogramId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }


        byte[] imageByte;

        public byte[] getImageByte() {
            return imageByte;
        }

        public void setImageByte(byte[] imageByte) {
            this.imageByte = imageByte;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

    }
}



