package com.example.yogeshgarg.source.mvp.new_product.new_product_home;

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
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.example.yogeshgarg.source.mvp.new_product.new_product_update.NewProductUpdateActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewProductFragment extends Fragment implements NewProductHomeView {

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

    ArrayList<NewProductHomeModel.Result> resultArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_product, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFont();
        callingNewProductApi();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callingNewProductApi();
    }

    @Override
    public void onSuccess(ArrayList<NewProductHomeModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;

        if (resultArrayList.size() == 0) {
            relLay.setVisibility(View.GONE);
            relLayNoProductAdded.setVisibility(View.VISIBLE);
        } else {
            relLay.setVisibility(View.VISIBLE);
            relLayNoProductAdded.setVisibility(View.GONE);
            setLayoutManager();
        }
    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, frameLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryNewProductApi, frameLayout);
    }

    OnClickInterface onRetryNewProductApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingNewProductApi();
        }
    };

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {


        NewProductHomeAdapter newProductHomeAdapter = new NewProductHomeAdapter(getActivity(), resultArrayList);
        recyclerView.setAdapter(newProductHomeAdapter);
    }

    private void callingNewProductApi() {
        NewproductHomePresenterImpl newproductHomePresenterImpl = new NewproductHomePresenterImpl(getActivity(), this);
        newproductHomePresenterImpl.callingNewProductHomeApi();
    }

    private void setFont() {
        FontHelper.setFontFace(txtViewNoProductAdded, FontHelper.FontType.FONT_Normal, getActivity());
    }


    @OnClick(R.id.imgViewPlus)
    public void imgViewPlusClick() {
        Intent intent = new Intent(getActivity(), NewProductUpdateActivity.class);
        startActivity(intent);
    }


}
