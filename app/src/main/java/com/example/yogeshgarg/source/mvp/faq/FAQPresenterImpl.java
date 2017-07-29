package com.example.yogeshgarg.source.mvp.faq;

import android.app.Activity;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public class FAQPresenterImpl implements FAQPresenter {
    Activity activity;
    FAQView faqView;

    public FAQPresenterImpl(Activity activity, FAQView faqView) {
        this.activity = activity;
        this.faqView = faqView;
    }
}
