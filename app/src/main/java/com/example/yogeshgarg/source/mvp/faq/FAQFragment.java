package com.example.yogeshgarg.source.mvp.faq;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yogeshgarg.source.R;

public class FAQFragment extends Fragment implements FAQView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }


    @Override
    public void onSuccessFaqApi() {

    }

    @Override
    public void onUnsuccessFaqApi(String message) {

    }

    @Override
    public void onInternetErrorFaqApi() {

    }

}
