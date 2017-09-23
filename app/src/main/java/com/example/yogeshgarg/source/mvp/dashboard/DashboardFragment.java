package com.example.yogeshgarg.source.mvp.dashboard;


import android.content.Intent;
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
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardExpiryProductUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardInStoreUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashoardRecentProductUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.NewProductAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.example.yogeshgarg.source.mvp.dashboard_activities.DashboardExpiryProductActivity;
import com.example.yogeshgarg.source.mvp.dashboard_activities.DashboardInStoreSamplingActivity;
import com.example.yogeshgarg.source.mvp.dashboard_activities.DashboardNewProductActivity;
import com.example.yogeshgarg.source.mvp.dashboard_activities.DashboardRecentPriceUpdateActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    //----------------------------------------------------------
    @BindView(R.id.llayRecentPrice)
    LinearLayout llayRecentPrice;

    @BindView(R.id.llayViewAllRecent)
    LinearLayout llayViewAllRecent;
    //----------------------------------------------------
    @BindView(R.id.llayTitleNewProductUpdate)
    LinearLayout llayTitleNewProductUpdate;

    @BindView(R.id.llayViewAllNewProduct)
    LinearLayout llayViewAllNewProduct;
    //------------------------------------------
    @BindView(R.id.llayTitleExpiringProduct)
    LinearLayout llayTitleExpiringProduct;

    @BindView(R.id.llayViewAllExpiringProduct)
    LinearLayout llayViewAllExpiringProduct;
    //--------------------------------------------------
    @BindView(R.id.llayTitlePopularProduct)
    LinearLayout llayTitlePopularProduct;

    @BindView(R.id.llayViewAll)
    LinearLayout llayViewAll;
    //--------------------------------------------
    String timeOneMonthAgo = null;

    public DashboardFragment() {
        // Required empty public constructor
    }

    DashboardPresenterImpl dashboardPresenterImpl;

    //model result
    ArrayList<DashboardRecentUpdateModel.Result> resultRecentArrayList;
    ArrayList<NewProductModel.Result> resultNewArrayList;
    ArrayList<DashboardExpiryProductModel.Result> resultExpiryArrayList;
    ArrayList<DashboardInStoreModel.Result> resultInStoreArrayList;


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

        this.resultRecentArrayList = resultArrayList;
        callingNewProductUpdate();//calling New Product Api

        if(this.resultRecentArrayList.size()>0){
            llayRecentPrice.setVisibility(View.VISIBLE);
            llayViewAllRecent.setVisibility(View.VISIBLE);

            DashoardRecentProductUpdateAdapter adapter = new DashoardRecentProductUpdateAdapter(getActivity(), resultArrayList);
            recyclerViewRecentProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewNewProduct.setNestedScrollingEnabled(false);
            recyclerViewRecentProduct.setAdapter(adapter);
        }else{
            llayRecentPrice.setVisibility(View.GONE);
            llayViewAllRecent.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.llayViewAllRecent)
    public void llayViewAllRecentClick() {
        Intent intent = new Intent(getActivity(), DashboardRecentPriceUpdateActivity.class);
        if (resultRecentArrayList != null)
            intent.putExtra(ConstIntent.KEY_DB_RECENT_PRICE, resultRecentArrayList);
        startActivity(intent);
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

        this.resultNewArrayList = resultArrayList;
        callingExpiryProductApi();//calling expiry product api
        if (this.resultNewArrayList.size() > 0) {
            llayTitleNewProductUpdate.setVisibility(View.VISIBLE);
            llayViewAllNewProduct.setVisibility(View.VISIBLE);

            NewProductAdapter adapter = new NewProductAdapter(getActivity(), resultArrayList);
            recyclerViewNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewNewProduct.setNestedScrollingEnabled(false);
            recyclerViewNewProduct.setAdapter(adapter);
        }else{
            llayTitleNewProductUpdate.setVisibility(View.GONE);
            llayViewAllNewProduct.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.llayViewAllNewProduct)
    public void llayViewAllNewProduct() {
        Intent intent = new Intent(getActivity(), DashboardNewProductActivity.class);
        if (resultNewArrayList != null)
            intent.putExtra(ConstIntent.KEY_DB_NEW_PRODUCT, resultNewArrayList);
        startActivity(intent);
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

        this.resultExpiryArrayList = resultArrayList;
        callingInstoreSamplingApi();//calling instore sampling api

        if (this.resultExpiryArrayList.size() > 0) {
            llayTitleExpiringProduct.setVisibility(View.VISIBLE);
            llayViewAllExpiringProduct.setVisibility(View.VISIBLE);

            DashboardExpiryProductUpdateAdapter dashboardExpiryProductUpdateAdapter = new DashboardExpiryProductUpdateAdapter(getActivity(), resultArrayList);
            recyclerViewExpiryProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewNewProduct.setNestedScrollingEnabled(false);
            recyclerViewExpiryProduct.setAdapter(dashboardExpiryProductUpdateAdapter);
        } else {
            llayTitleExpiringProduct.setVisibility(View.GONE);
            llayViewAllExpiringProduct.setVisibility(View.GONE);


        }

    }

    @OnClick(R.id.llayViewAllExpiringProduct)
    public void llayViewAllExpiringProductClick() {
        Intent intent = new Intent(getActivity(), DashboardExpiryProductActivity.class);
        if (resultExpiryArrayList != null)
            intent.putExtra(ConstIntent.KEY_DB_EXPIRY_PRODUCT, resultExpiryArrayList);
        startActivity(intent);
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

        this.resultInStoreArrayList = resultArrayList;
        if (this.resultInStoreArrayList.size() > 0) {
            llayTitlePopularProduct.setVisibility(View.VISIBLE);
            llayViewAll.setVisibility(View.VISIBLE);
            DashboardInStoreUpdateAdapter dashboardInStoreUpdateAdapter = new DashboardInStoreUpdateAdapter(getActivity(), resultArrayList);
            recyclerViewSampleProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewNewProduct.setNestedScrollingEnabled(false);
            recyclerViewSampleProduct.setAdapter(dashboardInStoreUpdateAdapter);
        } else {
            llayTitlePopularProduct.setVisibility(View.GONE);
            llayViewAll.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.llayViewAll)
    public void llayViewAllClick() {
        Intent intent = new Intent(getActivity(), DashboardInStoreSamplingActivity.class);
        if (resultInStoreArrayList != null)
            intent.putExtra(ConstIntent.KEY_DB_INSTORE_SAMPLING, resultInStoreArrayList);
        startActivity(intent);
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
