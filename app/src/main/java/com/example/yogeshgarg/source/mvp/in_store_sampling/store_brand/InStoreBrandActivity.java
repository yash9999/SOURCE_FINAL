package com.example.yogeshgarg.source.mvp.in_store_sampling.store_brand;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InStoreBrandActivity extends AppCompatActivity implements InStoreBrandView {

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.relLaySearch)
    RelativeLayout relLaySearch;

    @BindView(R.id.imgViewCloseSV)
    ImageView imgViewCloseSV;

    @BindView(R.id.searchView)
    SearchView searchView;

    InStoreBrandAdapter inStoreBrandAdapter;
    String categoryId;
    ArrayList<InStoreBrandModel.Result> resultArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_store_brand);
        ButterKnife.bind(this);

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
            callingSSBrandApi();
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
        ArrayList<InStoreBrandModel.Result> publishResultArrayList = new ArrayList<>();
        for (int i = 0; i < resultArrayList.size(); i++) {
            if (resultArrayList.get(i).getPublish() == 1) {
                publishResultArrayList.add(resultArrayList.get(i));
            }
        }
          inStoreBrandAdapter = new InStoreBrandAdapter(this, publishResultArrayList);
        recyclerView.setAdapter(inStoreBrandAdapter);
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
        txtViewTitle.setText(getString(R.string.in_store_sampling));
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
    }


    @Override
    public void onSuccessSSBrand(ArrayList<InStoreBrandModel.Result> resultArrayList) {
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
                    inStoreBrandAdapter.getFilter().filter(newText);
                    return true;
                }
            });
        } else {
            relLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUnsuccessSSBrand(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetry, relLay);
    }

    OnClickInterface onRetry = new OnClickInterface() {
        @Override
        public void onClick() {
            callingSSBrandApi();
        }
    };

    private void callingSSBrandApi() {
        InStoreBrandPresenterImpl inStoreBrandPresenterImpl = new InStoreBrandPresenterImpl(this, this);
        inStoreBrandPresenterImpl.callingSSBrandApi(categoryId);
    }


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

}
