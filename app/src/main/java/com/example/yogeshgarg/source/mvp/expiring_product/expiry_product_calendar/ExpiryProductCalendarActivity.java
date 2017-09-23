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

    @BindView(R.id.txtViewTitleFromDate)
    TextView txtViewTitleFromDate;

    @BindView(R.id.txtViewFromDate)
    TextView txtViewFromDate;

    @BindView(R.id.txtViewTitleToDate)
    TextView txtViewTitleToDate;

    @BindView(R.id.txtViewToDate)
    TextView txtViewToDate;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_product_calendar);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            productId = (String) getIntent().getExtras().get(ConstIntent.KEY_PRODUCT_ID);
            stock = (String) getIntent().getExtras().get(ConstIntent.KEY_STOCK);
            stockUnit = (String) getIntent().getExtras().get(ConstIntent.KEY_STOCK_UNIT);

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

        FontHelper.applyFont(this, txtViewTitle, FontHelper.FontType.FONT_Normal);
        txtViewTitle.setText("Expiry Product");
        FontHelper.applyFont(this, txtViewTitleFromDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewFromDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleToDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewToDate, FontHelper.FontType.FONT_Normal);
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

    @OnClick(R.id.txtViewToDate)
    public void txtViewToDateClick() {
        initializeToDate();
        String strDate = txtViewFromDate.getText().toString();
        if (!Utils.isEmptyOrNull(strDate)) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(strDate);
                long timeInMillis = date.getTime();
                fromDatePickerDialog.getDatePicker().setMinDate(timeInMillis);
            } catch (Exception ex) {
            }
        } else {
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }

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

    private void initializeToDate() {
        calendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                dateFromCalender = (dateFormatter.format(calendar.getTime()));
                txtViewToDate.setText(dateFromCalender);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void getCurrentDate() {
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewFromDate.setText(dateFromCalender);
    }

    private void getCurrentDateForToDate() {
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewToDate.setText(dateFromCalender);
    }


    @OnClick(R.id.txtViewTitleToday)
    public void txtViewTitleTodayClick() {
        getCurrentDate();
        getCurrentDateForToDate();
    }

    @OnClick(R.id.txtViewTitleClear)
    public void txtViewTitleClearClick() {
        txtViewFromDate.setText("");
        txtViewToDate.setText("");
    }

    @OnClick(R.id.txtView30Days)
    public void txtView30DaysClick() {
        getCurrentDate();
        calendar.add(Calendar.DATE, 30);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewToDate.setText(dateFromCalender);
    }

    @OnClick(R.id.txtView60Days)
    public void txtView60DaysClick() {
        getCurrentDate();
        calendar.add(Calendar.DATE, 60);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewToDate.setText(dateFromCalender);
    }

    @OnClick(R.id.txtView90Days)
    public void txtView90DaysClick() {
        getCurrentDate();
        calendar.add(Calendar.DATE, 90);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewToDate.setText(dateFromCalender);
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
        String endDate = txtViewToDate.getText().toString();

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String inputString1 = startDate;
        String inputString2 = endDate;

        int days = -1;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        startDate = DateMethods.dateConvertion(startDate);
        endDate = DateMethods.dateConvertion(endDate);

        String stock = edtTextStock.getText().toString();
        if (Utils.isEmptyOrNull(stock)) {
            stock = "0";
        }
        String stockUnit = edtTextStockUnit.getText().toString();
        String comment = edtTextComment.getText().toString();

        ExpiryProductCalendarPresenterImpl expiryProductCalendarPresenterImpl = new ExpiryProductCalendarPresenterImpl(this, this);
        expiryProductCalendarPresenterImpl.callingExpirtProductCalendarApi(productId, startDate, endDate, stock, stockUnit, comment, days);


    }


}