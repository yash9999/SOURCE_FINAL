package com.example.yogeshgarg.source.mvp.inbox;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 15/10/17.
 */

public interface InboxView {
    public void onSuccess(ArrayList<InboxModel.Result> resultArrayList);
    public void onUnsuccess(String message);
    public void onInternetError();
}
