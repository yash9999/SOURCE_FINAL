package com.example.yogeshgarg.source.mvp.faq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FaqActivity extends AppCompatActivity implements FAQView {

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        setFont();
        callingFaqApi();
    }


    @Override
    public void onSuccessFaqApi(FAQModel.Result result) {
        String html = result.getFaq();
        webView.loadData(html, "text/html; charset=utf-8", "UTF-8");

    }

    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetry, relLay);
    }

    OnClickInterface onRetry = new OnClickInterface() {
        @Override
        public void onClick() {
            callingFaqApi();
        }
    };

    private void callingFaqApi() {
        FAQPresenterImpl faqPresenterImpl = new FAQPresenterImpl(this, this);
        faqPresenterImpl.callingFaq();

    }

    private void setFont() {
        imgViewSearch.setVisibility(View.GONE);
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
        txtViewTitle.setText(getString(R.string.faq));
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
