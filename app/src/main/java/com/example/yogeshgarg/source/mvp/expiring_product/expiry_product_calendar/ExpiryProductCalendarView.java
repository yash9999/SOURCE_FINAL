package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar;

/**
 * Created by yogeshgarg on 27/08/17.
 */

public interface ExpiryProductCalendarView {
    public void onSuccess();
    public void onUnsuccess(String message);
    public void onInternetError();
}
