package com.example.yogeshgarg.source.mvp.product_list.product_list_brand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.product_list.product_list_category.ProductListCategoryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductListBrandActivity extends AppCompatActivity implements ProductListBrandView {

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    //-------------------------------------------------
    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.relLaySearch)
    RelativeLayout relLaySearch;

    @BindView(R.id.imgViewCloseSV)
    ImageView imgViewCloseSV;

    @BindView(R.id.searchView)
    SearchView searchView;

    //-------------------------------------------------------
    @BindView(R.id.txtViewMessage)
    TextView txtViewMessage;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int publish, position;
    String brandId;
    String categoryId;
    ArrayList<ProductListBrandModel.Result> resultArrayList;
    ProductListBrandPresenterImpl productListBrandPresenterImpl;
    ProductListBrandAdapter productListBrandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_brand);
        ButterKnife.bind(this);

        imgViewSearch.setVisibility(View.VISIBLE);

        productListBrandPresenterImpl = new ProductListBrandPresenterImpl(this, this);
        Intent intent = getIntent();
        if (intent != null) {
            categoryId = intent.getStringExtra(ConstIntent.KEY_CATEGORY_ID);
            setFont();
            callingProductListBrandApi();
        }
    }


    @Override
    public void onSuccessPLBrand(ArrayList<ProductListBrandModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        if (resultArrayList.size() > 0) {
            relLayout.setVisibility(View.VISIBLE);
            setLayoutManager();
        }
    }

    @Override
    public void onUnsuccessPLBrand(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryBrandApi, relLay);
    }


    OnClickInterface onRetryBrandApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingProductListBrandApi();
        }
    };


    @Override
    public void onSuccessPlBrandPublish(int publish, int position) {
        productListBrandAdapter.changeToggled(publish, position);
    }

    @Override
    public void onUnsuccessBrandPublish(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetErrorPublish() {
        SnackNotify.checkConnection(getOnRetryBrandPublish, relLay);
    }

    OnClickInterface getOnRetryBrandPublish = new OnClickInterface() {
        @Override
        public void onClick() {
            publishApi(publish, position, brandId);
        }
    };

    private void setFont() {
        txtViewTitle.setText(getString(R.string.product_list));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewMessage, FontHelper.FontType.FONT_Normal, this);
    }

    private void callingProductListBrandApi() {
        productListBrandPresenterImpl.callingPLBrandApi(categoryId);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        productListBrandAdapter = new ProductListBrandAdapter(this, resultArrayList);
        recyclerView.setAdapter(productListBrandAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackClick() {
        onBackPressed();
    }


    public void publishApi(int publish, int position, String brandId) {
        this.publish = publish;
        this.position = position;
        this.brandId = brandId;
        productListBrandPresenterImpl.callingPlBrandPublish(publish, position, brandId);
    }


    @OnClick(R.id.imgViewSearch)
    public void searchViewClick() {
        relLaySearch.setVisibility(View.VISIBLE);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Brand Name");
    }

    @OnClick(R.id.imgViewCloseSV)
    public void imgViewCloseSVClick() {
        relLaySearch.setVisibility(View.GONE);
    }


}
