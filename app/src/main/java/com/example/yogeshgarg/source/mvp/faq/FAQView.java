package com.example.yogeshgarg.source.mvp.faq;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public interface FAQView {
    public void onSuccessFaqApi();
    public void onUnsuccessFaqApi(String message);
    public void onInternetErrorFaqApi();
}
