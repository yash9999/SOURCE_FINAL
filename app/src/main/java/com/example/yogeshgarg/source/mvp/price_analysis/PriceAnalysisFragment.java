package com.example.yogeshgarg.source.mvp.price_analysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.XaxisValueFormatter;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.price_analysis.adapter.PABrandAdapter;
import com.example.yogeshgarg.source.mvp.price_analysis.adapter.PACategoryAdapter;
import com.example.yogeshgarg.source.mvp.price_analysis.adapter.PAProductAdapter;
import com.example.yogeshgarg.source.mvp.price_analysis.adapter.PAStoreAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM;


public class PriceAnalysisFragment extends Fragment implements PriceAnalysisView {


    LineChart chart;

    ArrayList<Entry> arrayList1;
    ArrayList<Entry> arrayList2;


    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;


    @BindView(R.id.txtViewTitleCategory)
    TextView txtViewTitleCategory;

    @BindView(R.id.txtViewTitleBrand)
    TextView txtViewTitleBrand;

    @BindView(R.id.txtViewTitleProduct)
    TextView txtViewTitleProduct;

    @BindView(R.id.txtViewTitleStore)
    TextView txtViewTitleStore;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;


    PriceAnalysisPresenterImpl priceAnalysisPresenterImpl;
    ArrayList<String> arrayListCategory;
    ArrayList<String> arrayListProduct;
    ArrayList<String> arrayListBrand;
    ArrayList<String> arrayListStore;

    PriceAnalysisModel.Result result;
    ArrayList<ILineDataSet> datasets;

    PACategoryAdapter paCategoryAdapter;
    PABrandAdapter paBrandAdapter;
    PAProductAdapter paProductAdapter;
    PAStoreAdapter paStoreAdapter;


    int colorArray[] = {R.color.color_red, R.color.color_bg, R.color.color_blue, R.color.color_orange, R.color.color_pink};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price_analysis, container, false);

        chart = (LineChart) view.findViewById(R.id.chart);
        chart.setClickable(false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrayListCategory = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        arrayListBrand = new ArrayList<>();
        arrayListStore = new ArrayList<>();

        priceAnalysisPresenterImpl = new PriceAnalysisPresenterImpl(getActivity(), this);
        callingPriceAnalysisApi();
    }

    //-------------------------------------------------------------------------------------------
    @Override
    public void onSuccess(PriceAnalysisModel.Result result) {
        this.result = result;
        setLayoutManager();
        //setData();
        settingGraphData();
    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryPriceAnalysisApi, relLayout);
    }

    OnClickInterface onRetryPriceAnalysisApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingPriceAnalysisApi();
        }
    };

    //-------------------------------------------------------------------------------------------

    private void callingPriceAnalysisApi() {
        priceAnalysisPresenterImpl.callingPriceAnalysisApi(arrayListCategory, arrayListProduct,
                arrayListBrand, arrayListStore);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        for (int i = 0; i < result.getOriginal().size(); i++) {
            result.getOriginal().get(i).setBrandTick(false);
            result.getOriginal().get(i).setProductTick(false);
            result.getOriginal().get(i).setStoreTick(false);
            result.getOriginal().get(i).setCategoryTick(false);
        }
        paCategoryAdapter = new PACategoryAdapter(getActivity(), result.getOriginal());
        paBrandAdapter = new PABrandAdapter(getActivity(), result.getOriginal());
        paProductAdapter = new PAProductAdapter(getActivity(), result.getOriginal());
        paStoreAdapter = new PAStoreAdapter(getActivity(), result.getOriginal());
        setAdapter();
    }

    private void setAdapter() {
        txtViewTitleCategory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));

        recyclerView.setAdapter(paCategoryAdapter);
        searchView.setQueryHint("Enter category name");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                paCategoryAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    @OnClick(R.id.txtViewTitleCategory)
    public void txtViewTitleCategoryClick() {
        txtViewTitleCategory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleBrand.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleProduct.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

        setAdapter();
    }

    @OnClick(R.id.txtViewTitleBrand)
    public void txtViewTitleBrandClick() {
        txtViewTitleCategory.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleBrand.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleProduct.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));


        recyclerView.setAdapter(paBrandAdapter);

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Enter brand name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                paBrandAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @OnClick(R.id.txtViewTitleProduct)
    public void setTxtViewTitleProductClick() {
        txtViewTitleCategory.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleBrand.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleProduct.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));


        recyclerView.setAdapter(paProductAdapter);

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Enter product name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                paProductAdapter.getFilter().filter(newText);
                return true;
            }
        });

    }

    @OnClick(R.id.txtViewTitleStore)
    public void setTxtViewTitleStoreClick() {
        txtViewTitleCategory.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleBrand.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleProduct.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleStore.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));


        recyclerView.setAdapter(paStoreAdapter);

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Enter store name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                paStoreAdapter.getFilter().filter(newText);
                return true;
            }
        });


    }


    private void setData() {

        arrayList1=new ArrayList<>();
        arrayList2=new ArrayList<>();

        Entry entry0 = new Entry(0, 40);
        Entry entry1 = new Entry(1, 40);
        Entry entry2 = new Entry(2, 60);
        Entry entry3 = new Entry(3, 80);
        Entry entry4 = new Entry(4, 40);
        Entry entry5 = new Entry(5, 140);
        Entry entry6 = new Entry(6, 100);
        Entry entry7 = new Entry(7, 120);
        Entry entry8 = new Entry(8, 100);
        Entry entry9 = new Entry(9, 80);
        Entry entry10 = new Entry(10, 140);
        Entry entry11 = new Entry(11, 160);
        arrayList1.add(entry0);
        arrayList1.add(entry1);
        arrayList1.add(entry2);
        arrayList1.add(entry3);
        arrayList1.add(entry4);
        arrayList1.add(entry5);
        arrayList1.add(entry6);
        arrayList1.add(entry6);
        arrayList1.add(entry7);
        arrayList1.add(entry8);
        arrayList1.add(entry9);
        arrayList1.add(entry10);
        arrayList1.add(entry11);

        Entry e0 = new Entry(1, 140);
        Entry e1 = new Entry(2, 80);
        Entry e2 = new Entry(3, 20);
        Entry e3 = new Entry(4, 10);
        Entry e4 = new Entry(5, 80);
        Entry e5 = new Entry(6, 180);
        Entry e6 = new Entry(7, 70);
        Entry e7 = new Entry(8, 50);
        Entry e8 = new Entry(9, 20);
        Entry e9 = new Entry(10, 60);
        Entry e10 = new Entry(11, 110);
        Entry e11 = new Entry(11, 25);
        arrayList2.add(e0);
        arrayList2.add(e1);
        arrayList2.add(e2);
        arrayList2.add(e3);
        arrayList2.add(e4);
        arrayList2.add(e5);
        arrayList2.add(e6);
        arrayList2.add(e6);
        arrayList2.add(e7);
        arrayList2.add(e8);
        arrayList2.add(e9);
        arrayList2.add(e10);
        arrayList2.add(e11);

        LineDataSet firstSet = new LineDataSet(arrayList1, "First Line");
        firstSet.setColor(ContextCompat.getColor(getActivity(), R.color.color_red));
        firstSet.setCircleColor(ContextCompat.getColor(getActivity(), R.color.color_red));
        firstSet.setCircleColorHole(ContextCompat.getColor(getActivity(), R.color.color_red));
        firstSet.setHighlightLineWidth(4f);
        firstSet.setCircleHoleRadius(10);

        LineDataSet secondSet = new LineDataSet(arrayList2, "second line");
        secondSet.setColor(ContextCompat.getColor(getActivity(), R.color.color_bg));
        secondSet.setCircleColor(ContextCompat.getColor(getActivity(), R.color.color_bg));
        secondSet.setCircleColorHole(ContextCompat.getColor(getActivity(), R.color.color_bg));
        secondSet.setHighlightLineWidth(4f);
        secondSet.setCircleHoleRadius(10);


        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        datasets.add(firstSet);
        datasets.add(secondSet);

        // String[] values = new String[] { ... };


        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new XaxisValueFormatter());
        xAxis.setPosition(BOTTOM);
        xAxis.setTextColor(R.color.color_white);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0);
        xAxis.setLabelCount(12, true);
        xAxis.setAxisLineColor(R.color.color_bg);


        YAxis yAxis = chart.getAxisRight();
        yAxis.setDrawLabels(false); // no axis labels
        yAxis.setDrawAxisLine(false); // no axis line
        yAxis.setDrawGridLines(false); // no grid lines
        yAxis.setDrawZeroLine(true); // draw a zero line
        yAxis.setAxisLineColor(R.color.color_black);
        yAxis.setAxisLineWidth(2);
        yAxis.disableAxisLineDashedLine();

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawAxisLine(false); // no axis line
        yAxisLeft.setDrawGridLines(false); // no grid lines


        LineData data = new LineData(datasets);
        chart.setGridBackgroundColor(R.color.color_white);
        chart.setData(data);


    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {

        ArrayList<PriceAnalysisModel.Result.Original> original = result.getOriginal();


        arrayListCategory = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        arrayListBrand = new ArrayList<>();
        arrayListStore = new ArrayList<>();

        for (int i = 0; i < original.size(); i++) {

            if (original.get(i).getCategoryTick()) {
                arrayListCategory.add(original.get(i).getCategoryId());
            }

            if (original.get(i).getBrandTick()) {
                arrayListBrand.add(original.get(i).getBrandId());
            }

            if (original.get(i).getProductTick()) {
                arrayListProduct.add(original.get(i).getProductId());
            }

            if (original.get(i).getStoreTick()) {
                arrayListStore.add(original.get(i).getStoreId());
            }
        }

        callingPriceAnalysisApi();
    }


    private void settingGraphData() {

        datasets = new ArrayList<>();

        if (result.getData().size() > 0) {
            for (int i = 0; i < result.getData().size(); i++) {
                arrayList1 = new ArrayList<>();
                for (int j = 0; j < result.getData().get(i).size(); j++) {
                    int x = result.getData().get(i).get(j).get(0);
                    int y = result.getData().get(i).get(j).get(1);
                    Entry entry = new Entry(x, y);
                    arrayList1.add(entry);
                }

                Random random = new Random();
                int number = random.nextInt(5);
                LineDataSet firstSet = new LineDataSet(arrayList1, result.getTitle().get(i));
                firstSet.setColor(ContextCompat.getColor(getActivity(), colorArray[number]));
                firstSet.setCircleColor(ContextCompat.getColor(getActivity(), colorArray[number]));
                firstSet.setCircleColorHole(ContextCompat.getColor(getActivity(), colorArray[number]));
                datasets.add(firstSet);
            }

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(BOTTOM);
            xAxis.setValueFormatter(new XaxisValueFormatter());
            // xAxis.setTextColor(R.color.color_white);
            xAxis.setGranularityEnabled(true);
            xAxis.setGranularity(1f);
            //xAxis.setAxisMinimum(2);
            //xAxis.setLabelCount(12, true);
            xAxis.setAxisLineColor(R.color.color_bg);


            YAxis yAxis = chart.getAxisRight();
            yAxis.setDrawLabels(false); // no axis labels
            yAxis.setDrawAxisLine(false); // no axis line
            yAxis.setDrawGridLines(false); // no grid lines
            yAxis.setDrawZeroLine(true); // draw a zero line
            yAxis.setAxisLineColor(R.color.color_black);
            // yAxis.setAxisLineWidth(2);
            // yAxis.disableAxisLineDashedLine();

            YAxis yAxisLeft = chart.getAxisLeft();
            yAxisLeft.setDrawAxisLine(false); // no axis line
            yAxisLeft.setDrawGridLines(false); // no grid lines


            LineData data = new LineData(datasets);
            chart.setData(data);
            chart.notifyDataSetChanged();
            chart.invalidate();

        }
    }
}
