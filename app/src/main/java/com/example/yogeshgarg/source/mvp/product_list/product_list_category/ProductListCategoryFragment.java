package com.example.yogeshgarg.source.mvp.product_list.product_list_category;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListCategoryFragment extends Fragment implements ProductListCategoryView {

    @BindView(R.id.txtViewMessage)
    TextView txtViewMessage;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    ArrayList<ProductListCategoryModel.Result> resultArrayList;

    ProductListCategoryPresenterImpl productListCategoryPresenterImpl;
    int publish, position;
    String categoryId;
    ProductListCategoryAdapter productListCategoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productListCategoryPresenterImpl = new ProductListCategoryPresenterImpl(getActivity(), this);
        callingProductListCAtegoryApi();
    }

    @Override
    public void onSuccessProductListCategory(ArrayList<ProductListCategoryModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        if (resultArrayList.size() > 0) {
            relLayout.setVisibility(View.VISIBLE);
            setLayoutManager();
        }
    }

    @Override
    public void onUnsuccessProductListCategory(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryProductListCategory, relLay);
    }


    OnClickInterface onRetryProductListCategory = new OnClickInterface() {
        @Override
        public void onClick() {
            callingProductListCAtegoryApi();
        }
    };

    @Override
    public void onSuccessPublish(int publish, int position) {
        productListCategoryAdapter.changeToggled(publish, position);
    }

    @Override
    public void onUnsuccessPublish(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetErrorPublish() {
        SnackNotify.checkConnection(onRetryPublish, relLay);
    }

    OnClickInterface onRetryPublish = new OnClickInterface() {
        @Override
        public void onClick() {
            publishApi(publish, position, categoryId);
        }
    };


    private void callingProductListCAtegoryApi() {
        productListCategoryPresenterImpl.callingProductListCategoryApi();
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        productListCategoryAdapter = new ProductListCategoryAdapter(getActivity(), resultArrayList, this);
        recyclerView.setAdapter(productListCategoryAdapter);
    }

    public void publishApi(int publish, int position, String categoryId) {
        this.publish = publish;
        this.position = position;
        this.categoryId = categoryId;
        productListCategoryPresenterImpl.callingPublishApi(publish, position, categoryId);
    }
}
