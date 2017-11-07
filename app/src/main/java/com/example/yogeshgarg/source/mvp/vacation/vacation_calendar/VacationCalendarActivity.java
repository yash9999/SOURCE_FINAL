package com.example.yogeshgarg.source.mvp.vacation.vacation_calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
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

public class VacationCalendarActivity extends AppCompatActivity implements VacationCalendarView {


    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

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

    @BindView(R.id.txtViewVacation)
    TextView txtViewVacation;

    @BindView(R.id.txtViewSickLeave)
    TextView txtViewSickLeave;

    @BindView(R.id.txtViewReliefSales)
    TextView txtViewReliefSales;


    @BindView(R.id.edtTextComment)
    EditText edtTextComment;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    SimpleDateFormat dateFormatter;
    String dateFromCalender;
    Calendar calendar;
    DatePickerDialog fromDatePickerDialog = null;
    String type = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_calendar);
        ButterKnife.bind(this);
        setFont();
    }

    @Override
    public void onSuccessVC() {
        SnackNotify.showMessage("Vacation added successfully", relLayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(VacationCalendarActivity.this, NavigationActivity.class);
                intent.putExtra(ConstIntent.FRAGMENT_TYPE, "vacationScreenOpen");
                startActivity(intent);
                finishAffinity();
            }
        }, 300);


    }

    @Override
    public void onUnsuccessVC(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetErrorVC() {
        SnackNotify.checkConnection(onRetryVacation, relLayout);
    }


    OnClickInterface onRetryVacation = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };


    private void setFont() {

        FontHelper.applyFont(this, txtViewTitle, FontHelper.FontType.FONT_Normal);
        txtViewTitle.setText("Vacation");
        imgViewSearch.setVisibility(View.GONE);
        FontHelper.applyFont(this, txtViewTitleFromDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewFromDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleToDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewToDate, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleToday, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewTitleClear, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextComment, FontHelper.FontType.FONT_Normal);

        FontHelper.applyFont(this, txtViewVacation, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewSickLeave, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewReliefSales, FontHelper.FontType.FONT_Normal);

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

        String comment = edtTextComment.getText().toString();


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
        callingVacationApi(startDate, endDate, comment, days);


    }

    private void callingVacationApi(String start, String end, String comment, int days) {
        VacationCalendarPresenterImpl vacationCalendarPresenterImpl = new VacationCalendarPresenterImpl(this, this);
        vacationCalendarPresenterImpl.callingVacationApi(start, end, comment, type, days);
    }

    @OnClick(R.id.txtViewVacation)
    public void txtViewVacation() {
        type = "3";
        txtViewVacation.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_green_rect));
        txtViewSickLeave.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
        txtViewReliefSales.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));

    }

    @OnClick(R.id.txtViewSickLeave)
    public void txtViewSickLeave() {
        type = "0";
        txtViewVacation.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
        txtViewSickLeave.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_green_rect));
        txtViewReliefSales.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));

    }

    @OnClick(R.id.txtViewReliefSales)
    public void txtViewReliefSales() {
        type = "1";
        txtViewVacation.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
        txtViewSickLeave.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
        txtViewReliefSales.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_green_rect));
    }
}