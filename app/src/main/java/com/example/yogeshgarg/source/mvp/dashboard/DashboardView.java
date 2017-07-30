package com.example.yogeshgarg.source.mvp.dashboard;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 30/07/17.
 */

public interface DashboardView {


    //recent price update
    public void onSuccessOfRecentPriceUpdate(ArrayList<DashboardCommonModel.Result> resultArrayList);
    public void onUnsccessOfRecentPriceUpdate(String message);
    public void onInternetErrorOfRecentPriceUpdate();

    //new product update
    public void onSuccessOfNewProductUpdate(ArrayList<NewProductModel.Result> resultArrayList);
    public void onUnsccessOfNewProductUpdate(String message);
    public void onInternetErrorOfNewProductUpdate();

    //expiry product
    public void onSuccessOfExpiryProduct(ArrayList<DashboardCommonModel.Result> resultArrayList);
    public void onUnsccessOfExpiryProduct(String message);
    public void onInternetErrorOfExpiryProduct();

    //in store sampling
    public void onSuccessOfInstoreSampling(ArrayList<DashboardCommonModel.Result> resultArrayList);
    public void onUnsccessOfInstoreSampling(String message);
    public void onInternetErrorOfInstoreSampling();



}
