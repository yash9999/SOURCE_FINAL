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
import android.widget.RelativeLayout;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardExpiryProductUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardInStoreUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashoardRecentProductUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.NewProductAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
//DashboardContractor.View
public class DashboardFragment extends Fragment implements DashboardView {

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

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

    String timeOneMonthAgo = null;

    public DashboardFragment() {
        // Required empty public constructor
    }

    DashboardPresenterImpl dashboardPresenterImpl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dashboardPresenterImpl = new DashboardPresenterImpl(getActivity(), this);

        calculatingTimeOneMonthAgo();//calculating the time

        callingRecentPriceUpdateApi();//Hitting first api

    }


    //first section recent price
    @Override
    public void onSuccessOfRecentPriceUpdate(ArrayList<DashboardRecentUpdateModel.Result> resultArrayList) {

        callingNewProductUpdate();//calling New Product Api

        llayRecentPrice.setVisibility(View.VISIBLE);

        DashoardRecentProductUpdateAdapter adapter = new DashoardRecentProductUpdateAdapter(getActivity(), resultArrayList);
        recyclerViewRecentProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewRecentProduct.setAdapter(adapter);
    }

    @Override
    public void onUnsccessOfRecentPriceUpdate(String message) {
        callingNewProductUpdate();//calling New Product Api
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetErrorOfRecentPriceUpdate() {
        SnackNotify.checkConnection(onRetryRecentProductUpdate, relLayout);
    }

    OnClickInterface onRetryRecentProductUpdate = new OnClickInterface() {
        @Override
        public void onClick() {
            callingRecentPriceUpdateApi();
        }
    };

    private void callingRecentPriceUpdateApi() {
        dashboardPresenterImpl.recentPriceUpdateApi(timeOneMonthAgo);
    }

    //second section

    @Override
    public void onSuccessOfNewProductUpdate(ArrayList<NewProductModel.Result> resultArrayList) {

        callingExpiryProductApi();//calling expiry product api

        llayTitleNewProductUpdate.setVisibility(View.VISIBLE);
        NewProductAdapter adapter = new NewProductAdapter(getActivity(), resultArrayList);
        recyclerViewNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewNewProduct.setAdapter(adapter);
    }

    @Override
    public void onUnsccessOfNewProductUpdate(String message) {

        callingExpiryProductApi();//calling expiry product api

        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetErrorOfNewProductUpdate() {
        SnackNotify.checkConnection(onRetryNewProductUpdate, relLayout);
    }

    OnClickInterface onRetryNewProductUpdate = new OnClickInterface() {
        @Override
        public void onClick() {
            callingNewProductUpdate();
        }
    };

    private void callingNewProductUpdate() {
        dashboardPresenterImpl.newProductApi(timeOneMonthAgo);
    }

    //third section
    @Override
    public void onSuccessOfExpiryProduct(ArrayList<DashboardExpiryProductModel.Result> resultArrayList) {

        callingInstoreSamplingApi();//calling instore sampling api

        llayTitleExpiringProduct.setVisibility(View.VISIBLE);
        DashboardExpiryProductUpdateAdapter dashboardExpiryProductUpdateAdapter=new DashboardExpiryProductUpdateAdapter(getActivity(),resultArrayList);
        recyclerViewExpiryProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewExpiryProduct.setAdapter(dashboardExpiryProductUpdateAdapter);
    }

    @Override
    public void onUnsccessOfExpiryProduct(String message) {

        callingInstoreSamplingApi();//calling instore sampling api

        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetErrorOfExpiryProduct() {
        SnackNotify.checkConnection(onRetryExpiryProduct, relLayout);
    }

    OnClickInterface onRetryExpiryProduct = new OnClickInterface() {
        @Override
        public void onClick() {
            callingExpiryProductApi();
        }
    };

    private void callingExpiryProductApi() {
        dashboardPresenterImpl.expiryProductApi(timeOneMonthAgo);
    }


    //fourth section
    @Override
    public void onSuccessOfInstoreSampling(ArrayList<DashboardInStoreModel.Result> resultArrayList) {

        llayTitlePopularProduct.setVisibility(View.VISIBLE);
        DashboardInStoreUpdateAdapter dashboardInStoreUpdateAdapter = new DashboardInStoreUpdateAdapter(getActivity(), resultArrayList);
        recyclerViewSampleProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNewProduct.setNestedScrollingEnabled(false);
        recyclerViewSampleProduct.setAdapter(dashboardInStoreUpdateAdapter);
    }

    @Override
    public void onUnsccessOfInstoreSampling(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetErrorOfInstoreSampling() {
        SnackNotify.checkConnection(onRetryInstoreSamplingApi, relLayout);
    }

    OnClickInterface onRetryInstoreSamplingApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingInstoreSamplingApi();
        }
    };

    private void callingInstoreSamplingApi() {
        dashboardPresenterImpl.storeSamplingProductApi(timeOneMonthAgo);
    }


    //local methods
    private void calculatingTimeOneMonthAgo() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeOneMonthAgo = simpleDateFormat.format(date);//this is the time which is extact one month ago in the above mention format

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
