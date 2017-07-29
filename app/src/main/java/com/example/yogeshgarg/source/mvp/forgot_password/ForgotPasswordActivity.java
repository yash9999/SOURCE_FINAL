package com.example.yogeshgarg.source.mvp.forgot_password;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordView {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @BindView(R.id.txtViewForgotPasswordMsg)
    TextView txtViewForgotPasswordMsg;

    @BindView(R.id.edtTextEmailId)
    EditText edtTextEmailId;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        setFont();
    }

    @Override
    public void onSuccessForgotPassword(String message) {

    }

    @Override
    public void onUnsuccessForgotPassword(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternetErrorForgotPassword() {
        SnackNotify.checkConnection(onRetryForgotPassword, coordinateLayout);
    }

    OnClickInterface onRetryForgotPassword = new OnClickInterface() {
        @Override
        public void onClick() {
            callingForgotPasswordApi();
        }
    };

    private void setFont() {
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
        getData();
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        /*callingForgotPasswordApi();*/
        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }


    @OnEditorAction(R.id.edtTextEmailId)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {

            callingForgotPasswordApi();
            return true;
        }
        return false;
    }


}
