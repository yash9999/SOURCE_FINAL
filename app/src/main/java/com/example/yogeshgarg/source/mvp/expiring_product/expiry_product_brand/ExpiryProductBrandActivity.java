package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
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
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryAdapter;
import com.example.yogeshgarg.source.mvp.scan.QrScannerActivity;

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

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.imgViewBarReader)
    ImageView imgViewBarReader;


    @BindView(R.id.relLaySearch)
    RelativeLayout relLaySearch;

    @BindView(R.id.imgViewCloseSV)
    ImageView imgViewCloseSV;

    @BindView(R.id.searchView)
    SearchView searchView;

    ExpiryProductBrandAdapter expiryProductBrandAdapter;

    String categoryId;
    ArrayList<ExpiryProductBrandModel.Result> resultArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_product_brand);
        ButterKnife.bind(this);

        imgViewBarReader.setVisibility(View.VISIBLE);
        imgViewSearch.setVisibility(View.VISIBLE);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search");

        ImageView searchViewIcon =(ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);
        ViewGroup linearLayoutSearchView=(ViewGroup)searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);

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
          expiryProductBrandAdapter = new ExpiryProductBrandAdapter(this, publishResultArrayList);
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
        txtViewTitle.setText(getString(R.string.expiring_product));
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
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    expiryProductBrandAdapter.getFilter().filter(newText);
                    return true;
                }
            });
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

    @OnClick(R.id.imgViewSearch)
    public void searchViewClick() {
        relLaySearch.setVisibility(View.VISIBLE);
        searchView.setQueryHint("Search Brand Name");
    }

    @OnClick(R.id.imgViewCloseSV)
    public void imgViewCloseSVClick() {
        searchView.setQuery("", true);
        relLaySearch.setVisibility(View.GONE);
    }

    @OnClick(R.id.imgViewBarReader)
    public void imgViewBarReaderClick(){
        Intent intent=new Intent(this, QrScannerActivity.class);
        intent.putExtra(ConstIntent.BAR_READER_VALUE,false);
        startActivity(intent);
    }
}
