package com.example.yogeshgarg.source.mvp.price_survey_brand;

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
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandModel;
import com.example.yogeshgarg.source.mvp.scan.QrScannerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PriceSurveyBrandActivity extends AppCompatActivity implements PriceSurveyBrandView {


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

    @BindView(R.id.imgViewBarReader)
    ImageView imgViewBarReader;


    @BindView(R.id.relLaySearch)
    RelativeLayout relLaySearch;

    @BindView(R.id.imgViewCloseSV)
    ImageView imgViewCloseSV;

    @BindView(R.id.searchView)
    SearchView searchView;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.txtViewPriceUpdateBy)
    TextView txtViewPriceUpdateBy;

    @BindView(R.id.txtViewPriceUpdateBetween)
    TextView txtViewPriceUpdateBetween;


    String categoryId;
    String strPriceUpdateBy;
    String strPriceUpdateBetween;
    PriceSurveyBrandAdapter priceSurveyBrandAdapter;

    ArrayList<PriceSurveyBrandModel.Result> resultArrayList;
    PriceSurveyBrandPresenterImpl priceSurveyBrandPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_survey_brand);
        ButterKnife.bind(this);

        imgViewBarReader.setVisibility(View.VISIBLE);
        imgViewSearch.setVisibility(View.VISIBLE);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search");

        ImageView searchViewIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);


        Intent intent = getIntent();
        if (intent != null) {

            categoryId = intent.getStringExtra(ConstIntent.KEY_CATEGORY_ID);
            strPriceUpdateBy = intent.getStringExtra(ConstIntent.KEY_INITIAL_DATE_TO_SEND);
            strPriceUpdateBetween = intent.getStringExtra(ConstIntent.KEY_FINAL_DATE_TO_SEND);
            setFont();
            callingPSBrandApi(categoryId);
        }
    }


    @Override
    public void onSuccessPLBrand(ArrayList<PriceSurveyBrandModel.Result> resultArrayList) {
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
                    priceSurveyBrandAdapter.getFilter().filter(newText);
                    return true;
                }
            });
        }
    }

    @Override
    public void onUnsuccessPLBrand(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetry, relLay);
    }

    OnClickInterface onRetry = new OnClickInterface() {
        @Override
        public void onClick() {
            callingPSBrandApi(categoryId);
        }
    };

    private void callingPSBrandApi(String categoryId) {
        priceSurveyBrandPresenterImpl = new PriceSurveyBrandPresenterImpl(this, this);
        priceSurveyBrandPresenterImpl.callingPSBrandApi(categoryId);
    }

    private void setFont() {
        txtViewTitle.setText(getString(R.string.price_survey));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewPriceUpdateBy, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewPriceUpdateBetween, FontHelper.FontType.FONT_Normal, this);

        txtViewPriceUpdateBy.setText(strPriceUpdateBy);
        txtViewPriceUpdateBetween.setText(strPriceUpdateBetween);
    }


    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        setAdapter();
    }

    private void setAdapter() {
        ArrayList<PriceSurveyBrandModel.Result> publishResultArrayList = new ArrayList<>();

        for (int i = 0; i < resultArrayList.size(); i++) {
            if (resultArrayList.get(i).getPublish() == 1) {
                publishResultArrayList.add(resultArrayList.get(i));
            }
        }

        priceSurveyBrandAdapter = new PriceSurveyBrandAdapter(this, publishResultArrayList, strPriceUpdateBy, strPriceUpdateBetween);
        recyclerView.setAdapter(priceSurveyBrandAdapter);
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
        searchView.setQueryHint("Search Brand Name");
    }

    @OnClick(R.id.imgViewCloseSV)
    public void imgViewCloseSVClick() {
        searchView.setQuery("",true);
        relLaySearch.setVisibility(View.GONE);
    }

    @OnClick(R.id.imgViewBarReader)
    public void imgViewBarReaderClick(){
        Intent intent=new Intent(this, QrScannerActivity.class);
        intent.putExtra(ConstIntent.BAR_READER_VALUE,true);
        startActivity(intent);
    }
}
