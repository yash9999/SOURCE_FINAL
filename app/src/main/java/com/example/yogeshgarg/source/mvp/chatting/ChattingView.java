package com.example.yogeshgarg.source.mvp.chatting;

import java.util.ArrayList;

/**
 * Created by yogeshgarg on 05/11/17.
 */

public interface ChattingView {
    public void onSuccessConversation(ArrayList<ChattingModel.Result> results);
    public void onUnsuccessConversation(String message);
    public void onInternetError();

    public void onSuccessSendMessage(SendingModel.Result result);
    public void onUnsuccessSendMessage(String message);
    public void onInternetErrorSendMessage();

    public void onSuccessReceivedMessage();
    public void onUnsuccessReceivedMessage(String message);
    public void onInternetErrorReceivedMessage();
}
