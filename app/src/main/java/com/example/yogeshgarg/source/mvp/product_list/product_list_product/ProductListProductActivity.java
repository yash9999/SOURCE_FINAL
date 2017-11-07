package com.example.yogeshgarg.source.mvp.product_list.product_list_product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductListProductActivity extends AppCompatActivity implements ProductListProductView {

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.txtViewMessage)
    TextView txtViewMessage;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.relLaySearch)
    RelativeLayout relLaySearch;

    String brandId;
    int publish, position;
    String productId;
    ProductListProductAdapter productListProductAdapter;
    ProductListProductPresenterImpl productListProductPresenterImpl;
    ArrayList<ProductListProductModel.Result> resultArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_product);
        ButterKnife.bind(this);

        imgViewSearch.setVisibility(View.VISIBLE);

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search");

        ImageView searchViewIcon =(ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);
        ViewGroup linearLayoutSearchView=(ViewGroup)searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);




        productListProductPresenterImpl = new ProductListProductPresenterImpl(this, this);

        Intent intent = getIntent();
        if (intent != null) {
            brandId = intent.getStringExtra(ConstIntent.KEy_BRAND_ID);
            setFont();
            callingProductListProductApi();
        }
    }

    @Override
    public void onSuccessPLProduct(ArrayList<ProductListProductModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        if (resultArrayList.size() > 0) {
            relLayout.setVisibility(View.VISIBLE);
            setLayoutManager();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    productListProductAdapter.getFilter().filter(newText);
                    return true;
                }
            });

        }
    }

    @Override
    public void onUnsuccessPlProduct(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryProductApi, relLay);
    }

    OnClickInterface onRetryProductApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingProductListProductApi();
        }
    };

    @Override
    public void onSuccessPlProductPublish(int publish, int position) {
        productListProductAdapter.changeToggled(publish,position);
    }

    @Override
    public void onUnsccuessPlProductPublish(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetErrorPublish() {
        SnackNotify.checkConnection(onRetryProductPublishApi, relLay);
    }

    OnClickInterface onRetryProductPublishApi = new OnClickInterface() {
        @Override
        public void onClick() {
            publishApi(publish, position, productId);
        }
    };


    private void callingProductListProductApi() {
        productListProductPresenterImpl.callingProductApi(brandId);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        productListProductAdapter = new ProductListProductAdapter(this, resultArrayList);
        recyclerView.setAdapter(productListProductAdapter);
    }

    private void setFont() {
        txtViewTitle.setText(getString(R.string.product_list));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewMessage, FontHelper.FontType.FONT_Normal, this);
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackClick() {
        onBackPressed();
    }

    @OnClick(R.id.imgViewSearch)
    public void searchViewClick() {
        relLaySearch.setVisibility(View.VISIBLE);
        searchView.setQueryHint("Search Product Name");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.imgViewCloseSV)
    public void imgViewCloseSVClick() {
        searchView.setQuery("",true);
        relLaySearch.setVisibility(View.GONE);
    }

    public void publishApi(int publish, int position, String productId) {
        this.publish = publish;
        this.position = position;
        this.productId = productId;
        productListProductPresenterImpl.callingProductPublish(publish, position, productId);
    }
}
