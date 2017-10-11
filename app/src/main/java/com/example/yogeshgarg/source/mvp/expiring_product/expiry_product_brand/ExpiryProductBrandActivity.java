package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpiryProductBrandActivity extends AppCompatActivity implements ExpiryProductBrandView {

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    String categoryId;
    ArrayList<ExpiryProductBrandModel.Result> resultArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_product_brand);
        ButterKnife.bind(this);
        setFont();
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra(ConstIntent.KEY_CATEGORY_ID);
            callingBrandApi();
        }

    }


    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {

        ArrayList<ExpiryProductBrandModel.Result> publishResultArrayList = new ArrayList<>();
        for (int i = 0; i < resultArrayList.size(); i++) {
            if (resultArrayList.get(i).getPublish() == 1) {
                        publishResultArrayList.add(resultArrayList.get(i));
            }
        }
        ExpiryProductBrandAdapter expiryProductBrandAdapter = new ExpiryProductBrandAdapter(this, publishResultArrayList);
        recyclerView.setAdapter(expiryProductBrandAdapter);
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
        txtViewTitle.setText(getString(R.string.brand));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
    }

    private void callingBrandApi() {
        ExpiryProductBrandPresenterImpl expiryProductBrandPresenterImpl = new ExpiryProductBrandPresenterImpl(this, this);
        expiryProductBrandPresenterImpl.callingEPBrandApi(categoryId);
    }


    @Override
    public void onSuccessEPBrand(ArrayList<ExpiryProductBrandModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        if (resultArrayList.size() > 0) {
            setLayoutManager();
        }
    }

    @Override
    public void onUnsuccessEPBrand(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryBrandApi, relLay);
    }

    OnClickInterface onRetryBrandApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingBrandApi();
        }
    };
}
