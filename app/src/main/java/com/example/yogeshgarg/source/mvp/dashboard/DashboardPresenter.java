package com.example.yogeshgarg.source.mvp.dashboard;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 30/07/17.
 */

public interface DashboardPresenter {

    public void newProductApi(String timeOneMonthAgo);

    public void recentPriceUpdateApi(String timeOneMonthAgo);

    public void storeSamplingProductApi(String timeOneMonthAgo);

    public void expiryProductApi(String timeOneMonthAgo);
}
