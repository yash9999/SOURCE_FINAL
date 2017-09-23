package com.example.yogeshgarg.source.mvp.price_survey_product;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.txtViewPriceUpdateBy)
    TextView txtViewPriceUpdateBy;

    @BindView(R.id.txtViewPriceUpdateBetween)
    TextView txtViewPriceUpdateBetween;

    String categoryId = null;
    ArrayList<PriceSurveyProductModel.Result> resultArrayList;
    String strPriceUpdateBy;
    String strPriceUpdateBetween;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_survey_product);
        ButterKnife.bind(this);
        txtViewTitle.setText(getString(R.string.product_name));
        Intent intent = getIntent();
        if (intent != null) {

            categoryId = intent.getStringExtra(ConstIntent.KEY_CATEGORY_ID);
            strPriceUpdateBy = intent.getStringExtra(ConstIntent.KEY_INITIAL_DATE_TO_SEND);
            strPriceUpdateBetween = intent.getStringExtra(ConstIntent.KEY_FINAL_DATE_TO_SEND);

            setFont();
            callingPriceSurveyProductApi(categoryId);
        }

    }


    @Override
    public void onSuccessPriceSurveyProduct(ArrayList<PriceSurveyProductModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        recyclerView.setVisibility(View.VISIBLE);
        setLayoutManager();
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
            callingPriceSurveyProductApi(categoryId);
        }
    };

    private void callingPriceSurveyProductApi(String categoryId) {
        PriceSurveyProductPresenterImpl priceSurveyProductPresenterImpl = new PriceSurveyProductPresenterImpl(this, this);
        priceSurveyProductPresenterImpl.callingPriceSurveyProductApi(categoryId);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        setAdapter();
    }

    private void setAdapter() {
        PriceSurveyProductAdapter priceSurveyProductAdapter = new PriceSurveyProductAdapter(this, resultArrayList);
        recyclerView.setAdapter(priceSurveyProductAdapter);
    }

    private void setFont() {

        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
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
}
