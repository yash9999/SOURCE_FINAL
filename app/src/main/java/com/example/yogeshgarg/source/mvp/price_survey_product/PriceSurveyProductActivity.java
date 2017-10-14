package com.example.yogeshgarg.source.mvp.price_survey_product;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyAdapter;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PriceSurveyProductActivity extends AppCompatActivity implements PriceSurveyProductView {

    @BindView(R.id.relLayoutRoot)
    RelativeLayout relLayoutRoot;

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.relLaySearch)
    RelativeLayout relLaySearch;

    @BindView(R.id.imgViewCloseSV)
    ImageView imgViewCloseSV;

    @BindView(R.id.searchView)
    SearchView searchView;


    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.txtViewPriceUpdateBy)
    TextView txtViewPriceUpdateBy;

    @BindView(R.id.txtViewPriceUpdateBetween)
    TextView txtViewPriceUpdateBetween;

    String brandId = null;
    ArrayList<PriceSurveyProductModel.Result> resultArrayList;
    PriceSurveyProductAdapter priceSurveyProductAdapter;
    String strPriceUpdateBy;
    String strPriceUpdateBetween;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_survey_product);
        ButterKnife.bind(this);

        imgViewSearch.setVisibility(View.VISIBLE);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search");

        ImageView searchViewIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);



        txtViewTitle.setText(getString(R.string.product_name));
        Intent intent = getIntent();
        if (intent != null) {

            brandId = intent.getStringExtra(ConstIntent.KEy_BRAND_ID);
            strPriceUpdateBy = intent.getStringExtra(ConstIntent.KEY_INITIAL_DATE_TO_SEND);
            strPriceUpdateBetween = intent.getStringExtra(ConstIntent.KEY_FINAL_DATE_TO_SEND);

            setFont();
            callingPriceSurveyProductApi(brandId);
        }

    }


    @Override
    public void onSuccessPriceSurveyProduct(ArrayList<PriceSurveyProductModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;

        if(resultArrayList.size()>0){
            relLayout.setVisibility(View.VISIBLE);
            setLayoutManager();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    priceSurveyProductAdapter.getFilter().filter(newText);
                    return true;
                }
            });
        }

    }

    @Override
    public void onUnsuccessPriceSurveyProduct(String message) {
        SnackNotify.showMessage(message, relLayoutRoot);
    }

    @Override
    public void onInternetErrorPriceSurveyProduct() {
        SnackNotify.checkConnection(onRetryPriceSurvey, relLayoutRoot);
    }

    OnClickInterface onRetryPriceSurvey = new OnClickInterface() {
        @Override
        public void onClick() {
            callingPriceSurveyProductApi(brandId);
        }
    };

    private void callingPriceSurveyProductApi(String brandId) {
        PriceSurveyProductPresenterImpl priceSurveyProductPresenterImpl = new PriceSurveyProductPresenterImpl(this, this);
        priceSurveyProductPresenterImpl.callingPriceSurveyProductApi(brandId);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        setAdapter();
    }

    private void setAdapter() {
        ArrayList<PriceSurveyProductModel.Result> publishResultArrayList = new ArrayList<>();
        for (int i = 0; i < resultArrayList.size(); i++) {

            if (Integer.parseInt(resultArrayList.get(i).getPublish()) == 1) {
                publishResultArrayList.add(resultArrayList.get(i));
            }
        }
          priceSurveyProductAdapter = new PriceSurveyProductAdapter(this, publishResultArrayList);
        recyclerView.setAdapter(priceSurveyProductAdapter);
    }

    private void setFont() {
        txtViewTitle.setText(getString(R.string.price_survey));

        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewPriceUpdateBy, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewPriceUpdateBetween, FontHelper.FontType.FONT_Normal, this);

        txtViewPriceUpdateBy.setText(strPriceUpdateBy);
        txtViewPriceUpdateBetween.setText(strPriceUpdateBetween);
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @OnClick(R.id.imgViewSearch)
    public void searchViewClick() {
        relLaySearch.setVisibility(View.VISIBLE);
        searchView.setQueryHint("Search Product Name");
    }

    @OnClick(R.id.imgViewCloseSV)
    public void imgViewCloseSVClick() {
        searchView.setQuery("",true);
        relLaySearch.setVisibility(View.GONE);
    }
}
