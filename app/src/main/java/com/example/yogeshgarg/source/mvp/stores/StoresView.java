package com.example.yogeshgarg.source.mvp.stores;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 19/07/17.
 */

public interface StoresView {
    public void onSuccessStoresList(ArrayList<StoresModel.Result> resultArrayList);
    public void onUnsuccessStoresList(String message);
    public void onInternetError();

}
