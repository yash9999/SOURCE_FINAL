package com.example.yogeshgarg.source.mvp.faq;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public interface FAQView {

    public void onSuccessFaqApi(FAQModel.Result result);
    public void onUnsuccess(String message);
    public void onInternetError();
}
