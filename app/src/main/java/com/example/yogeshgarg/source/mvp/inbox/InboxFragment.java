package com.example.yogeshgarg.source.mvp.inbox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandAdapter;
import com.example.yogeshgarg.source.mvp.team.MyTeamContractor;
import com.example.yogeshgarg.source.mvp.team.MyTeamModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxFragment extends Fragment implements InboxView {

    @BindView(R.id.recyclerViewMyTeam)
    RecyclerView recyclerView;

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    ArrayList<InboxModel.Result> resultArrayList;
    InboxPresenterImpl inboxPresenterImpl;

    InboxAdapter inboxAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inboxPresenterImpl = new InboxPresenterImpl(getActivity(), this);
        callingListApi();
    }

    @Override
    public void onSuccess(ArrayList<InboxModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        if(resultArrayList.size()>0){
            setLayoutManager();
        }
    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, frameLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetry, frameLayout);
    }

    OnClickInterface onRetry = new OnClickInterface() {
        @Override
        public void onClick() {
            callingListApi();
        }
    };

    private void callingListApi() {
        inboxPresenterImpl.getOpponentList();
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        inboxAdapter = new InboxAdapter(getActivity(),resultArrayList);
        recyclerView.setAdapter(inboxAdapter);
    }
}
