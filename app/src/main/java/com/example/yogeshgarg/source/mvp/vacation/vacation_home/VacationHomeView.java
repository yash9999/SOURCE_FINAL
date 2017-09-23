package com.example.yogeshgarg.source.mvp.vacation.vacation_home;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 20/08/17.
 */

public interface VacationHomeView {
    public void onSuccessVH(ArrayList<VacationHomeModel.Result> resultArrayList);

    public void onUnsuccessVH(String message);

    public void onInternetError();


    public void onSuccessDelete();

    public void onUnsuccessDelete(String message);

    public void onInternetErrorDelete();
}
