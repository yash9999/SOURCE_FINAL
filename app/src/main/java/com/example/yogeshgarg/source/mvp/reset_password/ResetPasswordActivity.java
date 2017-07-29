package com.example.yogeshgarg.source.mvp.reset_password;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordView {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.txtViewResetPasswordMsg)
    TextView txtViewResetPasswordMsg;

    @BindView(R.id.edtTextPassword)
    EditText edtTextPassword;

    @BindView(R.id.edtTextResetPassword)
    EditText edtTextResetPassword;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        setFont();
    }


    @Override
    public void onSuccessResetPassword() {

    }

    @Override
    public void onUnsuccessResetPassword(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryResetPassword, coordinateLayout);
    }

    OnClickInterface onRetryResetPassword = new OnClickInterface() {
        @Override
        public void onClick() {
            callingResetPassword();
        }
    };

    private void setFont() {
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewResetPasswordMsg, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextPassword, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextResetPassword, FontHelper.FontType.FONT_Normal);
        FontHelper.setFontFace(btnSubmit, FontHelper.FontType.FONT_Normal, this);
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        callingResetPassword();
    }

    @OnEditorAction(R.id.edtTextResetPassword)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callingResetPassword();
            return true;
        }
        return false;

    }

    private void getData() {
        String password = edtTextPassword.getText().toString();
        String rePassword = edtTextResetPassword.getText().toString();
        ResetPasswordPresenterImpl resetPasswordPresenterImpl = new ResetPasswordPresenterImpl(this, this);
        resetPasswordPresenterImpl.callingResetPasswordApi(password, rePassword);
    }

    private void callingResetPassword() {
        getData();
    }
}
