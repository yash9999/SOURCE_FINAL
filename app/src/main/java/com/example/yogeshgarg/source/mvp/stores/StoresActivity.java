package com.example.yogeshgarg.source.mvp.stores;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.adapter.AdapterSpinner;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoresActivity extends AppCompatActivity implements StoresView {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @BindView(R.id.txtViewPickYourLocation)
    TextView txtViewPickYourLocation;

    @BindView(R.id.spinnerLocation)
    AppCompatSpinner spinnerLocation;

    @BindView(R.id.btnContinue)
    Button btnContinue;

    ArrayList<HashMap<String, String>> hashMapArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        ButterKnife.bind(this);

        setFont();
        createAdapter();

        callStoreApi();
    }

    @Override
    public void onSuccessStoresList(ArrayList<StoresModel.Result> resultArrayList) {

        for (int i = 0; i < resultArrayList.size(); i++) {

            String storeLocation = resultArrayList.get(i).getAddress();//location
            String storeName = resultArrayList.get(i).getName();//store name
            String completeLocation = storeName + "-" + storeLocation;

            HashMap<String, String> index = new HashMap<String, String>();
            index.put(Const.KEY_ID, resultArrayList.get(i).getLocationId());
            index.put(Const.KEY_NAME, completeLocation);
            hashMapArrayList.add(index);
        }
        setDataForSpinner();
    }

    @Override
    public void onUnsuccessStoresList(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryStoreApi, coordinateLayout);
    }

    OnClickInterface onRetryStoreApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callStoreApi();
        }
    };

    private void callStoreApi() {
        StoresPresenterImpl storesPresenterImpl = new StoresPresenterImpl(this, this);
        storesPresenterImpl.callingStoreListApi();
    }

    private void setFont() {
        FontHelper.setFontFace(txtViewPickYourLocation, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(btnContinue, FontHelper.FontType.FONT_Semi_Bold, this);
    }


    public void setDataForSpinner() {
        AdapterSpinner locationAdapterSpinner = new AdapterSpinner(this, R.layout.layout_spinner_dropdown, hashMapArrayList);
        spinnerLocation.setAdapter(locationAdapterSpinner);
    }

    private void createAdapter() {
        hashMapArrayList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> firstIndex = new HashMap<String, String>();
        firstIndex.put(Const.KEY_ID, "0");
        firstIndex.put(Const.KEY_NAME, getString(R.string.select_your_location));

        hashMapArrayList.add(firstIndex);
        setDataForSpinner();
    }


    private void saveLocationId() {

        Utils.hideKeyboardIfOpen(this);

        if (spinnerLocation.getSelectedItemPosition() == 0) {
            SnackNotify.showMessage(getString(R.string.please_select_a_valid_location), coordinateLayout);
            return;
        } else {
            String locationId = hashMapArrayList.get(spinnerLocation.getSelectedItemPosition()).get(Const.KEY_ID);
            String storeAddress = hashMapArrayList.get(spinnerLocation.getSelectedItemPosition()).get(Const.KEY_NAME);

            UserSession userSession = new UserSession(this);
            userSession.setLocationId(locationId);
            userSession.setStoreAddress(storeAddress);
            userSession.setLocationStatus();

            Intent intent = new Intent(StoresActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnClick(R.id.btnContinue)
    public void btnContinueClick() {
        saveLocationId();
    }
}




