package com.example.yogeshgarg.source.mvp.in_store_sampling.store_home;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_category.StoreCategoryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InStoreSamplingFragment extends Fragment implements InstoreHomeView {


    @BindView(R.id.imgViewPlus)
    ImageView imgViewPlus;

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @BindView(R.id.relLayNoProductAdded)
    RelativeLayout relLayNoProductAdded;

    @BindView(R.id.txtViewNoProductAdded)
    TextView txtViewNoProductAdded;


    InstoreHomePresenterImpl instoreHomePresenterImpl = null;

    ArrayList<InStoreHomeModel.Result> resultArrayList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_store_sampling, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFont();
        instoreHomePresenterImpl = new InstoreHomePresenterImpl(getActivity(), this);
        callingInStoreHomeApi();
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onSuccess(ArrayList<InStoreHomeModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;

        if (resultArrayList.size() == 0) {
            relLayNoProductAdded.setVisibility(View.VISIBLE);
            relLay.setVisibility(View.GONE);
        } else {
            relLayNoProductAdded.setVisibility(View.GONE);
            relLay.setVisibility(View.VISIBLE);
            setLayoutManager();
        }

    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, frameLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryInStoreApi, frameLayout);
    }

    OnClickInterface onRetryInStoreApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingInStoreHomeApi();
        }
    };

    //--------------------------------------------------------------------------------------------------------------------------------
    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        InstoreHomeAdapter instoreHomeAdapter = new InstoreHomeAdapter(getActivity(), resultArrayList);
        recyclerView.setAdapter(instoreHomeAdapter);
    }

    private void callingInStoreHomeApi() {
        instoreHomePresenterImpl.callingInStoreHomeApi();
    }

    @OnClick(R.id.imgViewPlus)
    public void imgViewPlusClick() {
        Intent intent = new Intent(getActivity(), StoreCategoryActivity.class);
        startActivity(intent);
    }

    private void setFont() {
        FontHelper.setFontFace(txtViewNoProductAdded, FontHelper.FontType.FONT_Semi_Bold, getActivity());
    }
}

