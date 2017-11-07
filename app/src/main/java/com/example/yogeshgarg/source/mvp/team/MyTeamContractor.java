package com.example.yogeshgarg.source.mvp.team;

import com.example.yogeshgarg.source.common.BaseView;

import java.util.ArrayList;

/**
 * Created by himanshu on 29/07/17.
 */

public interface MyTeamContractor {

    public interface Presenter{
        void getTeam();
    }

    public interface View extends BaseView{
        void getTeam(ArrayList<MyTeamModel.Result> items);
        void getTeamFailure(String msg);
        void getTeamInternetError();
    }
}
