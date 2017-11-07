package com.example.yogeshgarg.source.mvp.ProductUpdate;

/**
 * Created by yogeshgarg on 22/07/17.
 */

public interface ProductUpdateView {
    public void onSuccessProductUpdate();

    public void onUnsuccessProductUpdate(String message);

    public void onInternetErro();
}
