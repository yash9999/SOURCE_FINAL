package com.example.yogeshgarg.source.mvp.dashboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardContractor.View{

    @BindView(R.id.recyclerViewRecentProduct)
    RecyclerView recyclerViewRecentProduct;

    @BindView(R.id.recyclerViewNewProduct)
    RecyclerView recyclerViewNewProduct;


    @BindView(R.id.recyclerViewSampleProduct)
    RecyclerView recyclerViewSampleProduct;

    @BindView(R.id.recyclerViewExpiryProduct)
    RecyclerView recyclerViewExpiryProduct;

    @BindView(R.id.llayRecentPrice)
    LinearLayout llayRecentPrice;

    @BindView(R.id.llayTitleNewProductUpdate)
    LinearLayout llayTitleNewProductUpdate;

    @BindView(R.id.llayTitleExpiringProduct)
    LinearLayout llayTitleExpiringProduct;

    @BindView(R.id.llayTitlePopularProduct)
    LinearLayout llayTitlePopularProduct;


    public DashboardFragment() {
        // Required empty public constructor
    }

    DashboardContractor.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter=new DashboardPresenterImpl(this);
        presenter.getNewProduct();
        presenter.getRecentPriceChangeProduct();
        presenter.getSamplingProduct();
        presenter.getExpiryProduct();


    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAlert(String message) {

    }

    @Override
    public void getNewProductResponse(ArrayList<NewProductModel.Result> listProduct) {
        llayTitleNewProductUpdate.setVisibility(View.VISIBLE);
        NewProductAdapter adapter=new NewProductAdapter(getActivity(),listProduct);
        recyclerViewNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewNewProduct.setAdapter(adapter);
    }

    @Override
    public void getRecentPriceChangedProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct) {
        llayRecentPrice.setVisibility(View.VISIBLE);

        DashoardProductAdapter adapter=new DashoardProductAdapter(getActivity(),listProduct,1);
        recyclerViewRecentProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewRecentProduct.setAdapter(adapter);
    }

    @Override
    public void getExpiryProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct) {
        llayTitleExpiringProduct.setVisibility(View.VISIBLE);
        DashoardProductAdapter adapter=new DashoardProductAdapter(getActivity(),listProduct,2);
        recyclerViewExpiryProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewExpiryProduct.setAdapter(adapter);
    }

    @Override
    public void getSamplingProductResponse(ArrayList<PriceSurveyProductModel.Result> listProduct) {
        llayTitlePopularProduct.setVisibility(View.VISIBLE);
        DashoardProductAdapter adapter=new DashoardProductAdapter(getActivity(),listProduct,3);
        recyclerViewSampleProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewSampleProduct.setAdapter(adapter);
    }
}
