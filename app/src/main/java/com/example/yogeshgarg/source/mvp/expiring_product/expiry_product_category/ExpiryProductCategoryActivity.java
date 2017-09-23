package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category;

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
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpiryProductCategoryActivity extends AppCompatActivity implements ExpiryProductView {

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinatorLayout;

    ExpiryProductPresenterImpl expiryProductPresenterImpl = null;
    ArrayList<ExpiryProductCategoryModel.Result> resultArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_product_category);
        ButterKnife.bind(this);
        setFont();
        expiryProductPresenterImpl = new ExpiryProductPresenterImpl(this, this);
        callingExpiryProductCategoryApi();
    }


    @Override
    public void onSuccessExpiryCategory(ArrayList<ExpiryProductCategoryModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        setLayoutManager();
    }

    @Override
    public void onUnsuccessExpiryCategory(String message) {
        SnackNotify.showMessage(message, coordinatorLayout);
    }

    @Override
    public void onInternetErrorExpiryCategory() {
        SnackNotify.checkConnection(onRetryExpiryProduct, coordinatorLayout);
    }

    OnClickInterface onRetryExpiryProduct = new OnClickInterface() {
        @Override
        public void onClick() {
            callingExpiryProductCategoryApi();
        }
    };

    private void callingExpiryProductCategoryApi() {
        expiryProductPresenterImpl.callingExpiryCategoryApi();
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        ExpiryProductCategoryAdapter expiryProductCategoryAdapter = new ExpiryProductCategoryAdapter(this, resultArrayList);
        recyclerView.setAdapter(expiryProductCategoryAdapter);
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
        txtViewTitle.setText(getString(R.string.categories));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
    }
}
