package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.calender.GridCellAdapter;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.DateMethods;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ExpiryProductCalendarActivity extends AppCompatActivity implements ExpiryProductCalendarView {


    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.txtViewProductName)
    TextView txtViewProductName;

    @BindView(R.id.txtViewBrandName)
    TextView txtViewBrandName;

    @BindView(R.id.txtViewTitleFromDate)
    TextView txtViewTitleFromDate;

    @BindView(R.id.txtViewFromDate)
    TextView txtViewFromDate;

    @BindView(R.id.imgView)
    ImageView imgView;


    @BindView(R.id.txtViewTitleToday)
    TextView txtViewTitleToday;

    @BindView(R.id.txtViewTitleClear)
    TextView txtViewTitleClear;

    @BindView(R.id.txtViewTitleStock)
    TextView txtViewTitleStock;

    @BindView(R.id.txtView30Days)
    TextView txtView30Days;

    @BindView(R.id.txtView60Days)
    TextView txtView60Days;

    @BindView(R.id.txtView90Days)
    TextView txtView90Days;

    @BindView(R.id.edtTextStock)
    EditText edtTextStock;

    @BindView(R.id.edtTextStockUnit)
    EditText edtTextStockUnit;

    @BindView(R.id.edtTextComment)
    EditText edtTextComment;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    SimpleDateFormat dateFormatter;
    String dateFromCalender;
    Calendar calendar;
    DatePickerDialog fromDatePickerDialog = null;

    String productId;
    String stock;
    String stockUnit;
    String productName, brandName;

    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_product_calendar);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            productId = (String) getIntent().getExtras().get(ConstIntent.KEY_PRODUCT_ID);
            stock = (String) getIntent().getExtras().get(ConstIntent.KEY_STOCK);
            stockUnit = (String) getIntent().getExtras().get(ConstIntent.KEY_STOCK_UNIT);
            productName = getIntent().getStringExtra(ConstIntent.PRODUCT_NAME);
            brandName = getIntent().getStringExtra(ConstIntent.BRAND_NAME);
            image = getIntent().getStringExtra(ConstIntent.EXPIRY_PRODUCT_IMAGE);

            edtTextStock.setText(stock);
            edtTextStockUnit.setText(stockUnit);
        }
        setFont();
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public void onSuccess() {
        SnackNotify.showMessage("Added successfully", relLayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ExpiryProductCalendarActivity.this, NavigationActivity.class);
                intent.putExtra(ConstIntent.FRAGMENT_TYPE, "openExpiryProductCalendar");
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
        SnackNotify.checkConnection(onRetryApi, relLayout);
    }

    OnClickInterface onRetryApi = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };

    //-------------------------------------------------------------------------------------------------

    private void setFont() {

        FontHelper.applyFont(this, txtViewTitle, FontHelper.FontType.FONT_Semi_Bold);
        txtViewTitle.setText("Expiry Product");
        txtViewProductName.setText(productName);
        txtViewBrandName.setText(brandName);
        Picasso.with(this).load(ConstIntent.PREFIX_URL_OF_IMAGE + image).transform(new CircleTransform()).into(imgView);
        FontHelper.applyFont(this, txtViewProductName, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewBrandName, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleToday, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleClear, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleStock, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtView30Days, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtView60Days, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtView90Days, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextStock, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextStockUnit, FontHelper.FontType.FONT_Normal);
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


    private void getCurrentDate() {
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewFromDate.setText(dateFromCalender);
    }


    @OnClick(R.id.txtViewTitleToday)
    public void txtViewTitleTodayClick() {
        getCurrentDate();
    }

    @OnClick(R.id.txtViewTitleClear)
    public void txtViewTitleClearClick() {
        txtViewFromDate.setText("");
    }

    @OnClick(R.id.txtView30Days)
    public void txtView30DaysClick() {
        getCurrentDate();
        calendar.add(Calendar.DATE, 30);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewFromDate.setText(dateFromCalender);
    }

    @OnClick(R.id.txtView60Days)
    public void txtView60DaysClick() {
        getCurrentDate();
        calendar.add(Calendar.DATE, 60);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewFromDate.setText(dateFromCalender);
    }

    @OnClick(R.id.txtView90Days)
    public void txtView90DaysClick() {
        getCurrentDate();
        calendar.add(Calendar.DATE, 90);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewFromDate.setText(dateFromCalender);
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

        String stock = edtTextStock.getText().toString();
        if (Utils.isEmptyOrNull(stock)) {
            stock = "0";
        }
        String stockUnit = edtTextStockUnit.getText().toString();
        String comment = edtTextComment.getText().toString();

        ExpiryProductCalendarPresenterImpl expiryProductCalendarPresenterImpl = new ExpiryProductCalendarPresenterImpl(this, this);
        expiryProductCalendarPresenterImpl.callingExpirtProductCalendarApi(productId, startDate, stock, stockUnit, comment);


    }


}