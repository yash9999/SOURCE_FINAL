package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar;

/**
 * Created by yogeshgarg on 27/08/17.
 */

public interface ExpiryProductCalendarPresenter {
    public void callingExpirtProductCalendarApi(String productId,String start,String end,String stock,String stockUnit,String comment,int days);
}
