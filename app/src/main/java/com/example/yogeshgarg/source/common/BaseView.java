package com.example.yogeshgarg.source.common;

/**
 * Created by Braintech on 23-03-2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showProgress();

    void hideProgress();

    void showAlert(String message);

}
