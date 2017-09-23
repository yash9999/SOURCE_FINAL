package com.example.yogeshgarg.source.mvp.notification;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationFragment extends Fragment implements NotificationPushView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    ArrayList<NotificationPushModel.Result> resultArrayList;
    NotificationPushPresenterImpl notificationPushPresenterImpl;
    NotificationAdapter notificationAdapter;
    String messageId;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notificationPushPresenterImpl = new NotificationPushPresenterImpl(getActivity(), this);
        callingPushNotificationApi();

    }


    @Override
    public void onSuccess(ArrayList<NotificationPushModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        setLayoutManager();
    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryPushNotification, relLay);
    }


    OnClickInterface onRetryPushNotification = new OnClickInterface() {
        @Override
        public void onClick() {
            callingPushNotificationApi();
        }
    };


    @Override
    public void onSuccessMarkRead(int position) {
        notificationAdapter.markRead(position);
    }

    @Override
    public void onUnsuccessMarkRead(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetErrorMark() {
        SnackNotify.checkConnection(onRetryMarkRead, relLay);
    }

    OnClickInterface onRetryMarkRead = new OnClickInterface() {
        @Override
        public void onClick() {
            markReadNotification(messageId, position);
        }
    };


    private void callingPushNotificationApi() {
        notificationPushPresenterImpl.callingNotificationApi();
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        notificationAdapter = new NotificationAdapter(getActivity(), resultArrayList, this);
        recyclerView.setAdapter(notificationAdapter);
    }

    public void markReadNotification(String messageId, int position) {
        this.messageId = messageId;
        this.position = position;
        notificationPushPresenterImpl.callingMarkReadNotification(messageId, position);
    }

}
