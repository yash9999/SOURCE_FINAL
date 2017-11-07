package com.example.yogeshgarg.source.mvp.in_store_sampling.store_calendar;

/**
 * Created by yogeshgarg on 31/08/17.
 */

public interface InStoreCalendarView {
    public void onSuccess();
    public void onUnsuccess(String message);
    public void onInternetError();
}
