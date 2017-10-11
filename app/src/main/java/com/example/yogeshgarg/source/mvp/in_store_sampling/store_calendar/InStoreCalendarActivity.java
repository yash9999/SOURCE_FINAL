package com.example.yogeshgarg.source.mvp.in_store_sampling.store_calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.DateMethods;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class InStoreCalendarActivity extends AppCompatActivity implements InStoreCalendarView {

    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.txtViewTitleFromDate)
    TextView txtViewTitleFromDate;

    @BindView(R.id.txtViewFromDate)
    TextView txtViewFromDate;


    @BindView(R.id.txtViewTitleToday)
    TextView txtViewTitleToday;

    @BindView(R.id.txtViewTitleClear)
    TextView txtViewTitleClear;

    @BindView(R.id.edtTextComment)
    EditText edtTextComment;

    @BindView(R.id.imgViewProduct)
    ImageView imgViewProduct;

    @BindView(R.id.txtViewProductName)
    TextView txtViewProductName;

    @BindView(R.id.txtViewBrandName)
    TextView txtViewBrandName;


    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    SimpleDateFormat dateFormatter;
    String dateFromCalender;
    Calendar calendar;
    DatePickerDialog fromDatePickerDialog = null;


    String brandName;
    String productName;
    String link;

    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_store_calendar);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            productName = (String) getIntent().getExtras().get(ConstIntent.KEY_INSTORE_PRODUCT_NAME);
            brandName = (String) getIntent().getExtras().get(ConstIntent.KEY_INSTORE_BRAND_NAME);
            link = (String) getIntent().getExtras().get(ConstIntent.KEY_INSTORE_LINK);
            productId = (String) getIntent().getExtras().get(ConstIntent.KEY_PRODUCT_ID);
        }

        setFont();
    }


    private void setFont() {

        FontHelper.applyFont(this, txtViewTitle, FontHelper.FontType.FONT_Semi_Bold);
        txtViewTitle.setText("In Store Sampling");

        FontHelper.applyFont(this, txtViewProductName, FontHelper.FontType.FONT_Normal);

        txtViewProductName.setText(productName);
        txtViewBrandName.setText(brandName);

        Picasso.with(this).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).transform(new CircleTransform()).into(imgViewProduct);

        FontHelper.applyFont(this, txtViewBrandName, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleFromDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewFromDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleToday, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleClear, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextComment, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, btnSubmit, FontHelper.FontType.FONT_Normal);
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.txtViewFromDate)
    public void txtViewFromDateClick() {
        initializeFromDate();
        fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        fromDatePickerDialog.show();
    }

    private void initializeFromDate() {
        calendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                dateFromCalender = (dateFormatter.format(calendar.getTime()));
                txtViewFromDate.setText(dateFromCalender);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @OnClick(R.id.txtViewTitleToday)
    public void txtViewTitleTodayClick() {
        getCurrentDate();
    }

    private void getCurrentDate() {
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewFromDate.setText(dateFromCalender);
    }


    @OnClick(R.id.txtViewTitleClear)
    public void txtViewTitleClearClick() {
        txtViewFromDate.setText("");
    }


    @OnEditorAction(R.id.edtTextComment)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            getData();
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClcik() {
        getData();
    }

    private void getData() {
        String startDate = txtViewFromDate.getText().toString();
        startDate = DateMethods.dateConvertion(startDate);
        String comment = edtTextComment.getText().toString();
        callingInStoreCalendarApi(productId, startDate, comment);
    }

    private void callingInStoreCalendarApi(String productId, String start, String comment) {
        InStoreCalendarPresenterImpl inStoreCalendarPresenterImpl = new InStoreCalendarPresenterImpl(this, this);
        inStoreCalendarPresenterImpl.callingInStoreSamplingCalendarApi(productId, start, comment);
    }

    @Override
    public void onSuccess() {

        SnackNotify.showMessage("Successfully added.", relLayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InStoreCalendarActivity.this, NavigationActivity.class);
                intent.putExtra(ConstIntent.FRAGMENT_TYPE,"inStoreSamplingScreenOpen");
                startActivity(intent);
                finishAffinity();

            }
        }, 300);
    }


    @Override
    public void onUnsuccess(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryInStoreCalendarApi, relLayout);
    }

    OnClickInterface onRetryInStoreCalendarApi = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };
}
