package com.example.yogeshgarg.source.mvp.chatting;

/**
 * Created by yogeshgarg on 05/11/17.
 */

public interface ChattingPresenter {
    public void callingConversationApi(String userId);

    public void sendMessage(String message, String userId);

    public void receivedMessage(String userId);
}
