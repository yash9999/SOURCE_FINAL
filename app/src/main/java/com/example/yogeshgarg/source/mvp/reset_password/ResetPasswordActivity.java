package com.example.yogeshgarg.source.mvp.reset_password;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordView {

    @BindView(R.id.relLayRoot)
    RelativeLayout relLayRoot;


    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.txtViewForgotPasswordMsg)
    TextView txtViewForgotPasswordMsg;

    @BindView(R.id.edtTextCurrentPassword)
    EditText edtTextCurrentPassword;

    @BindView(R.id.edtTextPassword)
    EditText edtTextPassword;

    @BindView(R.id.edtTextConfirmPassword)
    EditText edtTextConfirmPassword;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    int SPLASH_TIME_OUT = 700;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        txtViewTitle.setText(R.string.reset_password);
        imgViewSearch.setVisibility(View.GONE);
        setFont();
    }


    @Override
    public void onSuccessResetPassword(String message) {
        final String msg = "Password Updated SuccessFully.";

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SnackNotify.showMessage(msg, relLayRoot);
            }
        }, SPLASH_TIME_OUT);

        onBackPressed();

    }

    @Override
    public void onUnsuccessResetPassword(final String message) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SnackNotify.showMessage(message, relLayRoot);
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryResetPassword, relLayRoot);
    }

    OnClickInterface onRetryResetPassword = new OnClickInterface() {
        @Override
        public void onClick() {
            callingResetPassword();
        }
    };

    private void setFont() {

        FontHelper.setFontFace(txtViewForgotPasswordMsg, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);

        FontHelper.applyFont(this, edtTextCurrentPassword, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextPassword, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextConfirmPassword, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(btnSubmit, FontHelper.FontType.FONT_Normal, this);
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        Utils.hideKeyboardIfOpen(this);
        callingResetPassword();
    }

    @OnEditorAction(R.id.edtTextConfirmPassword)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callingResetPassword();
            return true;
        }
        return false;

    }

    private void getData() {
        String currentPassword = edtTextCurrentPassword.getText().toString();
        String password = edtTextPassword.getText().toString();
        String confirm_password = edtTextConfirmPassword.getText().toString();

        ResetPasswordPresenterImpl resetPasswordPresenterImpl = new ResetPasswordPresenterImpl(this, this);
        resetPasswordPresenterImpl.callingResetPasswordApi(currentPassword, password, confirm_password);
    }

    private void callingResetPassword() {
        getData();
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
