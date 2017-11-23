package com.example.yogeshgarg.source.mvp.price_analysis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.XaxisValueFormatter;
import com.example.yogeshgarg.source.common.helper.FontHelper;
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
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM;


public class PriceAnalysisFragment extends Fragment implements PriceAnalysisView {


    LineChart chart;
    String filterBrandId = null;
    String filterCategoryId = null;
    ArrayList<PriceAnalysisModel.Result.Original.Brand> arrayList = new ArrayList<>();
    ArrayList<PriceAnalysisModel.Result.Original.Product> productArrayList = new ArrayList<>();

    ArrayList<Entry> arrayList1;
    ArrayList<Entry> arrayList2;


    @BindView(R.id.relLatCheckBox)
    RelativeLayout relLatCheckBox;

    @BindView(R.id.checkboxSelectAll)
    CheckBox checkboxSelectAll;

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

    boolean isBtnSubmitPressed = false;

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
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, getActivity());


        checkboxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                paStoreAdapter.setCheckBox(b);
            }
        });
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
        relLatCheckBox.setVisibility(View.GONE);
        checkboxSelectAll.setChecked(false);
        this.result = result;
        setLayoutManager();
        if (isBtnSubmitPressed) {
            if (result.getData().size() > 0) {
                settingGraphData();
            } else {
                setData();
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data found to populate the chart.");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }

        } else {
            //not populate the graph
        }
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

        for (int i = 0; i < result.getOriginal().getCategories().size(); i++) {
            result.getOriginal().getCategories().get(i).setCategoryTick(false);
        }

        for (int i = 0; i < result.getOriginal().getBrands().size(); i++) {
            result.getOriginal().getBrands().get(i).setBrandTick(false);
        }

        for (int i = 0; i < result.getOriginal().getProducts().size(); i++) {
            result.getOriginal().getProducts().get(i).setProductTick(false);
        }


        for (int i = 0; i < result.getOriginal().getStores().size(); i++) {
            result.getOriginal().getStores().get(i).setStoreTick(false);
        }


        paCategoryAdapter = new PACategoryAdapter(getActivity(), result.getOriginal().getCategories());
        paBrandAdapter = new PABrandAdapter(getActivity(), result.getOriginal().getBrands());
        paProductAdapter = new PAProductAdapter(getActivity(), result.getOriginal().getProducts());
        paStoreAdapter = new PAStoreAdapter(getActivity(), result.getOriginal().getStores(),this);
        setAdapter();
    }

    private void setAdapter() {
        txtViewTitleCategory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleBrand.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleProduct.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

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
        relLatCheckBox.setVisibility(View.GONE);
        txtViewTitleCategory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleBrand.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleProduct.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

        setAdapter();
    }

    @OnClick(R.id.txtViewTitleBrand)
    public void txtViewTitleBrandClick() {
        relLatCheckBox.setVisibility(View.GONE);
        txtViewTitleCategory.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleBrand.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleProduct.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

        filterCategoryId=null;
        arrayList=new ArrayList<>();

        for (int i = 0; i < result.getOriginal().getCategories().size(); i++) {
            if (result.getOriginal().getCategories().get(i).getCategoryTick()) {
                filterCategoryId = result.getOriginal().getCategories().get(i).getCategoryId();
                break;
            }
        }

        if (filterCategoryId == null) {
            for (int i = 0; i < result.getOriginal().getBrands().size(); i++) {
                result.getOriginal().getBrands().get(i).setBrandTick(false);
            }
            paBrandAdapter = new PABrandAdapter(getActivity(), result.getOriginal().getBrands());
        } else {
            for (int i = 0; i < result.getOriginal().getBrands().size(); i++) {
                if (result.getOriginal().getBrands().get(i).getCategoryId().equals(filterCategoryId)) {
                    arrayList.add(result.getOriginal().getBrands().get(i));
                }
            }
            paBrandAdapter = new PABrandAdapter(getActivity(), arrayList);
        }


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
        relLatCheckBox.setVisibility(View.GONE);
        txtViewTitleCategory.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleBrand.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        txtViewTitleProduct.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edittext_green_rect));
        txtViewTitleStore.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

        filterBrandId=null;
        filterCategoryId=null;
        productArrayList = new ArrayList<>();


        for (int i = 0; i < result.getOriginal().getCategories().size(); i++) {
            if (result.getOriginal().getCategories().get(i).getCategoryTick()) {
                filterCategoryId = result.getOriginal().getCategories().get(i).getCategoryId();
                break;
            }
        }

        for (int i = 0; i < result.getOriginal().getBrands().size(); i++) {
            if (result.getOriginal().getBrands().get(i).getBrandTick()) {
                filterBrandId = result.getOriginal().getBrands().get(i).getBrandId();
                break;
            }
        }


        if (filterCategoryId != null) {
            if (filterBrandId == null) {
                for (int i = 0; i < result.getOriginal().getProducts().size(); i++) {
                    if (result.getOriginal().getProducts().get(i).getCategory_id().equals(filterCategoryId)) {
                        productArrayList.add(result.getOriginal().getProducts().get(i));
                    }
                }
                paProductAdapter = new PAProductAdapter(getActivity(), productArrayList);
            } else {
                for (int i = 0; i < result.getOriginal().getProducts().size(); i++) {
                    if (result.getOriginal().getProducts().get(i).getBrandId().equals(filterBrandId)
                            && (result.getOriginal().getProducts().get(i).getCategory_id().equals(filterCategoryId))) {
                        productArrayList.add(result.getOriginal().getProducts().get(i));
                    }
                }
                paProductAdapter = new PAProductAdapter(getActivity(), productArrayList);
            }

        } else {
            if (filterBrandId == null) {
                for (int i = 0; i < result.getOriginal().getProducts().size(); i++) {
                    result.getOriginal().getProducts().get(i).setProductTick(false);
                }
                paProductAdapter = new PAProductAdapter(getActivity(), result.getOriginal().getProducts());
            } else {
                for (int i = 0; i < result.getOriginal().getProducts().size(); i++) {
                    if (result.getOriginal().getProducts().get(i).getBrandId().equals(filterBrandId)) {
                        productArrayList.add(result.getOriginal().getProducts().get(i));
                    }
                }
                paProductAdapter = new PAProductAdapter(getActivity(), productArrayList);
            }
        }


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
        relLatCheckBox.setVisibility(View.VISIBLE);
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

    public void changeSelectAllIcon(){
        checkboxSelectAll.setChecked(false);
    }


    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        isBtnSubmitPressed = true;
        PriceAnalysisModel.Result.Original original = result.getOriginal();


        arrayListCategory = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        arrayListBrand = new ArrayList<>();
        arrayListStore = new ArrayList<>();

        for (int i = 0; i < original.getCategories().size(); i++) {

            if (original.getCategories().get(i).getCategoryTick()) {
                arrayListCategory.add(original.getCategories().get(i).getCategoryId());
            }
        }

        for (int i = 0; i < original.getBrands().size(); i++) {
            if (original.getBrands().get(i).getBrandTick()) {
                arrayListBrand.add(original.getBrands().get(i).getBrandId());
            }
        }

        for (int i = 0; i < original.getProducts().size(); i++) {
            if (original.getProducts().get(i).getProductTick()) {
                arrayListProduct.add(original.getProducts().get(i).getProductId());
            }
        }


        for (int i = 0; i < original.getStores().size(); i++) {
            if (original.getStores().get(i).getStoreTick()) {
                arrayListStore.add(original.getStores().get(i).getStoreId());
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
                    float x = result.getData().get(i).get(j).get(0);
                    float y = result.getData().get(i).get(j).get(1);
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

    private void setData() {

        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        Entry entry0 = new Entry(0, 0);
        arrayList1.add(entry0);

        Entry e0 = new Entry(0, 0);
        arrayList2.add(e0);


        LineDataSet firstSet = new LineDataSet(arrayList1, "");
        firstSet.setColor(ContextCompat.getColor(getActivity(), R.color.color_red));
        firstSet.setCircleColor(ContextCompat.getColor(getActivity(), R.color.color_red));
        firstSet.setCircleColorHole(ContextCompat.getColor(getActivity(), R.color.color_red));
        firstSet.setHighlightLineWidth(4f);
        firstSet.setCircleHoleRadius(10);

       /* LineDataSet secondSet = new LineDataSet(arrayList2, "");
        secondSet.setColor(ContextCompat.getColor(getActivity(), R.color.color_bg));
        secondSet.setCircleColor(ContextCompat.getColor(getActivity(), R.color.color_bg));
        secondSet.setCircleColorHole(ContextCompat.getColor(getActivity(), R.color.color_bg));
        secondSet.setHighlightLineWidth(4f);
        secondSet.setCircleHoleRadius(10);*/


        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        datasets.add(firstSet);
        //datasets.add(secondSet);

        // String[] values = new String[] { ... };


        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new XaxisValueFormatter());
        xAxis.setPosition(BOTTOM);
        //xAxis.setTextColor(R.color.color_white);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        /*xAxis.setAxisMinimum(0);
        xAxis.setLabelCount(12, true);*/
        xAxis.setAxisLineColor(R.color.color_bg);


        YAxis yAxis = chart.getAxisRight();
        yAxis.setDrawLabels(false); // no axis labels
        yAxis.setDrawAxisLine(false); // no axis line
        yAxis.setDrawGridLines(false); // no grid lines
        yAxis.setDrawZeroLine(true); // draw a zero line
        yAxis.setAxisLineColor(R.color.color_black);
        /*yAxis.setAxisLineWidth(2);
        yAxis.disableAxisLineDashedLine();*/

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawAxisLine(false); // no axis line
        yAxisLeft.setDrawGridLines(false); // no grid lines


        LineData data = new LineData(datasets);
        chart.setGridBackgroundColor(R.color.color_white);
        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();


    }
}
