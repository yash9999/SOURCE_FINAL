package com.example.yogeshgarg.source.mvp.dashboard_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardInStoreUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashoardRecentProductUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard_activities.adapter.DashboardActivityInStoreProductAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardInStoreSamplingActivity extends AppCompatActivity {

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<DashboardInStoreModel.Result> resultArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_in_store_sampling);
        ButterKnife.bind(this);
        setData();
        Intent intent = getIntent();
        if (intent != null) {
            resultArrayList = (ArrayList<DashboardInStoreModel.Result>) intent.getSerializableExtra(ConstIntent.KEY_DB_INSTORE_SAMPLING);
            setLayoutManager();
        }
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setLayoutManager() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        setAdapter();
    }

    private void setAdapter() {
        DashboardActivityInStoreProductAdapter dashboardActivityInStoreProductAdapter = new DashboardActivityInStoreProductAdapter(this, resultArrayList);
        recyclerView.setAdapter(dashboardActivityInStoreProductAdapter);
    }

    private void setData() {
        imgViewSearch.setVisibility(View.GONE);
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
        txtViewTitle.setText("Instore Sampling");
    }
}
