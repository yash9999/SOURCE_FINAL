package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryActivity;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryAdapter;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductPresenterImpl;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpiringProductFragment extends Fragment implements EPHomeView {

    @BindView(R.id.imgViewPlus)
    ImageView imgViewPlus;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.relLayUpper)
    RelativeLayout relLayUpper;


    @BindView(R.id.relLayNoProductAdded)
    RelativeLayout relLayNoProductAdded;

    @BindView(R.id.txtViewNoProductAdded)
    TextView txtViewNoProductAdded;


    SearchView searchView;
    ExpiryProductHomeScreenAdapter expiryProductHomeScreenAdapter;

    EPHomePresenterImpl epHomePresenterImpl = null;
    ArrayList<ExpiryProductHomeModel.Result> resultArrayList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expiring_product, container, false);
        ButterKnife.bind(this, view);
        searchView = getActivity().findViewById(R.id.searchView);
        setFont();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        epHomePresenterImpl = new EPHomePresenterImpl(getActivity(), this);
        callingEP_Home_Product();
    }


    private void callingEP_Home_Product() {
        epHomePresenterImpl.callingExpiryProductHomeApi();
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        expiryProductHomeScreenAdapter = new ExpiryProductHomeScreenAdapter(getActivity(), resultArrayList);
        recyclerView.setAdapter(expiryProductHomeScreenAdapter);
    }


    @Override
    public void onSuccess(ArrayList<ExpiryProductHomeModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;

        if (resultArrayList.size() == 0) {
            relLayNoProductAdded.setVisibility(View.VISIBLE);
            relLayUpper.setVisibility(View.GONE);
        } else {
            relLayNoProductAdded.setVisibility(View.GONE);
            relLayUpper.setVisibility(View.VISIBLE);
            setLayoutManager();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    expiryProductHomeScreenAdapter.getFilter().filter(newText);
                    return true;
                }
            });
        }


    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryEPHomeApi, relLayout);
    }

    OnClickInterface onRetryEPHomeApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingEP_Home_Product();
        }
    };


    @OnClick(R.id.imgViewPlus)
    public void imgViewPlusClick() {
        Intent intent = new Intent(getActivity(), ExpiryProductCategoryActivity.class);
        startActivity(intent);
    }

    private void setFont() {
        FontHelper.setFontFace(txtViewNoProductAdded, FontHelper.FontType.FONT_Semi_Bold, getActivity());
    }
}
