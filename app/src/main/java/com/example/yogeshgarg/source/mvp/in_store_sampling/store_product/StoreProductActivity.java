package com.example.yogeshgarg.source.mvp.in_store_sampling.store_product;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreProductActivity extends AppCompatActivity implements StoreProductView {

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinatorLayout;


    String categoryId = null;

    ArrayList<StoreProductModel.Result> resultArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_product);
        ButterKnife.bind(this);
        setFont();
        categoryId = getIntent().getStringExtra(ConstIntent.KEY_CATEGORY_ID);
        callingProductApi();

    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        StoreProductAdapter storeProductAdapter = new StoreProductAdapter(this, resultArrayList);
        recyclerView.setAdapter(storeProductAdapter);

    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setFont() {
        txtViewTitle.setText(getString(R.string.product_name));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
    }

    private void callingProductApi() {
        StoreProductPresenterImpl storeProductPresenterImpl = new StoreProductPresenterImpl(this, this);
        storeProductPresenterImpl.callingStoreProductApi(categoryId);
    }

    @Override
    public void onSuccess(ArrayList<StoreProductModel.Result> results) {
        this.resultArrayList = results;
        setLayoutManager();
    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, coordinatorLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryProductApi, coordinatorLayout);
    }

    OnClickInterface onRetryProductApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingProductApi();
        }
    };
}
