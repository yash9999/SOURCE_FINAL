package com.example.yogeshgarg.source.mvp.dashboard_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardPlanogramModel;
import com.example.yogeshgarg.source.mvp.dashboard_activities.adapter.DashboardActivityInStoreProductAdapter;
import com.example.yogeshgarg.source.mvp.dashboard_activities.adapter.DashboardActivityPlanogramAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanogramActivity extends AppCompatActivity {

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<DashboardPlanogramModel.Result> resultArrayListPlanogram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planogram);
        ButterKnife.bind(this);
        setData();
        Intent intent = getIntent();
        if (intent != null) {
         resultArrayListPlanogram = (ArrayList<DashboardPlanogramModel.Result>) intent.getSerializableExtra(ConstIntent.KEY_DB_PLANOGRAM);
            setLayoutManager();
        }
    }

    private void setLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        setAdapter();
    }
    private void setAdapter() {
        DashboardActivityPlanogramAdapter dashboardActivityPlanogramAdapter = new DashboardActivityPlanogramAdapter(this,resultArrayListPlanogram);
        recyclerView.setAdapter(dashboardActivityPlanogramAdapter);
    }


    @OnClick(R.id.imgViewBack)
    public void imgViewBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setData(){
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal,this);
        txtViewTitle.setText("Planogram Updates");
    }
}
