package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product;

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

public class ExpiryProduct_ProductActivity extends AppCompatActivity implements ExpiryProduct_ProductView {

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinatorLayout;


    String brandId = null;

    ArrayList<ExpiryProduct_ProductModel.Result> resultArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_product);
        ButterKnife.bind(this);
        setFont();
        if (getIntent() != null) {
            brandId = getIntent().getStringExtra(ConstIntent.KEy_BRAND_ID);
        }
        callingProductApi();
    }


    @Override
    public void onSuccess(ArrayList<ExpiryProduct_ProductModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        setLayoutManager();
    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, coordinatorLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryEP_ProductApi, coordinatorLayout);
    }

    OnClickInterface onRetryEP_ProductApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingProductApi();
        }
    };

    private void callingProductApi() {
        ExpiryProduct_ProductPresenterImpl expiryProduct_productPresenterImpl = new ExpiryProduct_ProductPresenterImpl(this, this);
        expiryProduct_productPresenterImpl.callingExpiryProduct_ProductApi(brandId);
    }


    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        ArrayList<ExpiryProduct_ProductModel.Result> publishResultArrayList = new ArrayList<>();

        for (int i = 0; i < resultArrayList.size(); i++) {
            if (Integer.parseInt(resultArrayList.get(i).getPublish()) == 1) {
                publishResultArrayList.add(resultArrayList.get(i));
            }
        }
        ExpiryProduct_ProductAdapter expiryProduct_productAdapter = new ExpiryProduct_ProductAdapter(this, publishResultArrayList);
        recyclerView.setAdapter(expiryProduct_productAdapter);
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
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
    }


}
