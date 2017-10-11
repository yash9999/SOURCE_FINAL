package com.example.yogeshgarg.source.mvp.in_store_sampling.store_category;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreCategoryActivity extends AppCompatActivity implements StoreCategoryView {

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinatorLayout;

    ArrayList<StoreCategoryModel.Result> resultArrayList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_category);
        ButterKnife.bind(this);
        setFont();
        callingInStoreCategoryApi();
    }

    @Override
    public void onSuccess(ArrayList<StoreCategoryModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        if (resultArrayList.size() > 0) {
            relLay.setVisibility(View.VISIBLE);
            setLayoutManager();
        } else {
            relLay.setVisibility(View.GONE);
        }

    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, coordinatorLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryInStoreCategoryApi, coordinatorLayout);
    }

    OnClickInterface onRetryInStoreCategoryApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingInStoreCategoryApi();
        }
    };


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
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        ArrayList<StoreCategoryModel.Result> publishResultArrayList = new ArrayList<>();
        for (int i = 0; i < resultArrayList.size(); i++) {
            if (resultArrayList.get(i).getPublish() == 1) {
                publishResultArrayList.add(resultArrayList.get(i));
            }
        }
        StoreCategoryAdapter storeCategoryAdapter = new StoreCategoryAdapter(this, publishResultArrayList);
        recyclerView.setAdapter(storeCategoryAdapter);
    }


    private void callingInStoreCategoryApi() {
        StoreCategoryPresenterImpl storeCategoryPresenterImpl = new StoreCategoryPresenterImpl(this, this);
        storeCategoryPresenterImpl.callingStoreCategoryApi();
    }
}
