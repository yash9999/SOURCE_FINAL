package com.example.yogeshgarg.source.mvp.in_store_sampling.store_home;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public interface InstoreHomeView {
    public void onSuccess(ArrayList<InStoreHomeModel.Result> resultArrayList);
    public void onUnsuccess(String message);
    public void onInternetError();
}
