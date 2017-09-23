package com.example.yogeshgarg.source.mvp.price_survey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PriceSurveyFragment extends Fragment implements PriceSurveyView {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.txtViewPriceUpdateBy)
    TextView txtViewPriceUpdateBy;

    @BindView(R.id.txtViewPriceUpdateBetween)
    TextView txtViewPriceUpdateBetween;

    String initialDateSend = null;
    String finalDateSend = null;


    ArrayList<PriceSurveyModel.Result> resultArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price_survey, container, false);
        ButterKnife.bind(this, view);
        setFont();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callingCategoryApi();
    }

    @Override
    public void onSuccessCategory(ArrayList<PriceSurveyModel.Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        setDate();
        recyclerView.setVisibility(View.VISIBLE);
        setLayoutManager();
    }

    @Override
    public void onUnsccuessCategory(String message) {
        SnackNotify.showMessage("need to insert message", frameLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryCategoryApi, frameLayout);
    }

    OnClickInterface onRetryCategoryApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingCategoryApi();
        }
    };

    private void callingCategoryApi() {
        PriceSurveyPresenterImpl priceSurveyPresenterImpl = new PriceSurveyPresenterImpl(getActivity(), this);
        priceSurveyPresenterImpl.callingCategoryApi();
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        PriceSurveyAdapter priceSurveyAdapter = new PriceSurveyAdapter(getActivity(), resultArrayList, initialDateSend, finalDateSend);
        recyclerView.setAdapter(priceSurveyAdapter);
    }

    private void setDate() {
        String initialDateToShow = null;
        String finalDateToShow = null;

        String initialDate = resultArrayList.get(0).getUpdatefrom();
        String finalDate = resultArrayList.get(0).getUpdateby();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateInitial = null;
            Date dateFinal = null;
            try {
                dateInitial = simpleDateFormat.parse(initialDate);
                dateFinal = simpleDateFormat.parse(finalDate);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SimpleDateFormat newFormatDate = new SimpleDateFormat("EEE MMM dd, yyyy");
            initialDateToShow = newFormatDate.format(dateInitial);
            finalDateToShow = newFormatDate.format(dateFinal);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        initialDateSend = "Please update prices by " + initialDateToShow;
        finalDateSend = "Update period " + initialDateToShow + " to " + finalDateToShow;

        txtViewPriceUpdateBy.setText(initialDateSend);
        txtViewPriceUpdateBetween.setText(finalDateSend);

    }

    private void setFont() {
        FontHelper.setFontFace(txtViewPriceUpdateBy, FontHelper.FontType.FONT_Semi_Bold, getActivity());
        FontHelper.setFontFace(txtViewPriceUpdateBetween, FontHelper.FontType.FONT_Normal, getActivity());
    }


}
