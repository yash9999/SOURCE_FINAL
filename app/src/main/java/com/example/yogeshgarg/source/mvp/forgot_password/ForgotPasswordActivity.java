package com.example.yogeshgarg.source.mvp.forgot_password;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordActivity;
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordView {

    @BindView(R.id.relLay)
    RelativeLayout relLay;

    @BindView(R.id.txtViewForgotPasswordMsg)
    TextView txtViewForgotPasswordMsg;

    @BindView(R.id.edtTextEmailId)
    EditText edtTextEmailId;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        setFont();

    }

    //---------------------------------------------------------------------------------
    @Override
    public void onSuccessForgotPassword(final String message) {
        SnackNotify.showMessage("An OTP is sent.", relLay);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordOtpActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onUnsuccessForgotPassword(String message) {
        SnackNotify.showMessage(message, relLay);
    }

    @Override
    public void onInternetErrorForgotPassword() {
        SnackNotify.checkConnection(onRetryForgotPassword, relLay);
    }

    OnClickInterface onRetryForgotPassword = new OnClickInterface() {
        @Override
        public void onClick() {
            callingForgotPasswordApi();
        }
    };


    //---------------------------------------------------------------------------------
    private void setFont() {

        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
        txtViewTitle.setText(getString(R.string.forgot_password_));
        FontHelper.setFontFace(txtViewForgotPasswordMsg, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(edtTextEmailId, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(btnSubmit, FontHelper.FontType.FONT_Semi_Bold, this);
    }


    private void getData() {
        String emailId = edtTextEmailId.getText().toString();

        ForgotPasswordPresenterImpl forgotPasswordPresenterImpl = new ForgotPasswordPresenterImpl(this, this);
        forgotPasswordPresenterImpl.callingForgotPasswordApi(emailId);
    }

    private void callingForgotPasswordApi() {
        Utils.hideKeyboardIfOpen(this);
        getData();
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        callingForgotPasswordApi();
    }


    @OnEditorAction(R.id.edtTextEmailId)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {

            callingForgotPasswordApi();
            return true;
        }
        return false;
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
