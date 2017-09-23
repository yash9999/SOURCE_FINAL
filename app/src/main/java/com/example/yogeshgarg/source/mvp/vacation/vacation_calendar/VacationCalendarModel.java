package com.example.yogeshgarg.source.mvp.vacation.vacation_calendar;

/**
 * Created by yogeshgarg on 20/08/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VacationCalendarModel {

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

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("id")
        @Expose
        private String id;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }







    }


