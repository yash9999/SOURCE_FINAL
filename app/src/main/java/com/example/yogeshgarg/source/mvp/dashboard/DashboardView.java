package com.example.yogeshgarg.source.mvp.dashboard;

import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 30/07/17.
 */

public interface DashboardView {


    //recent price update
    public void onSuccessOfRecentPriceUpdate(ArrayList<DashboardRecentUpdateModel.Result> resultArrayList);
    public void onUnsccessOfRecentPriceUpdate(String message);
    public void onInternetErrorOfRecentPriceUpdate();

    //new product update
    public void onSuccessOfNewProductUpdate(ArrayList<NewProductModel.Result> resultArrayList);
    public void onUnsccessOfNewProductUpdate(String message);
    public void onInternetErrorOfNewProductUpdate();

    //expiry product
    public void onSuccessOfExpiryProduct(ArrayList<DashboardExpiryProductModel.Result> resultArrayList);
    public void onUnsccessOfExpiryProduct(String message);
    public void onInternetErrorOfExpiryProduct();

    //in store sampling
    public void onSuccessOfInstoreSampling(ArrayList<DashboardInStoreModel.Result> resultArrayList);
    public void onUnsccessOfInstoreSampling(String message);
    public void onInternetErrorOfInstoreSampling();



}
