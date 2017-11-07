package com.example.yogeshgarg.source.mvp.forgot_password;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class ForgotPasswordOtpActivity extends AppCompatActivity implements ForgotPasswordOtpView {


    @BindView(R.id.relLayRoot)
    RelativeLayout relLayRoot;

    @BindView(R.id.txtViewSixDigit)
    TextView txtViewSixDigit;

    @BindView(R.id.edtTextFirstDigit)
    EditText edtTextFirstDigit;

    @BindView(R.id.edtTextSecondDigit)
    EditText edtTextSecondDigit;

    @BindView(R.id.edtTextThirdDigit)
    EditText edtTextThirdDigit;

    @BindView(R.id.edtTextFourthDigit)
    EditText edtTextFourthDigit;

    @BindView(R.id.edtTextFivethDigit)
    EditText edtTextFivethDigit;

    @BindView(R.id.edtTextSixthDigit)
    EditText edtTextSixthDigit;

    @BindView(R.id.edtTextPassword)
    EditText edtTextPassword;

    @BindView(R.id.edtTextConfirmPassword)
    EditText edtTextConfirmPassword;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    char ch[] = new char[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp);
        ButterKnife.bind(this);
        setFont();

        edtTextFirstDigit.addTextChangedListener(new AddListenerOnTextChange(this, edtTextFirstDigit));
        edtTextSecondDigit.addTextChangedListener(new AddListenerOnTextChange(this, edtTextSecondDigit));
        edtTextThirdDigit.addTextChangedListener(new AddListenerOnTextChange(this, edtTextThirdDigit));
        edtTextFourthDigit.addTextChangedListener(new AddListenerOnTextChange(this, edtTextFourthDigit));
        edtTextFivethDigit.addTextChangedListener(new AddListenerOnTextChange(this, edtTextFivethDigit));
        edtTextSixthDigit.addTextChangedListener(new AddListenerOnTextChange(this, edtTextSixthDigit));
    }

    //--------------------------------------------------------------------------------
    @Override
    public void onSuccessForgotPasswordOtp(String message) {
        Intent intent = new Intent(ForgotPasswordOtpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUnsuccessForgotPasswordOtp(String message) {
        SnackNotify.showMessage(message, relLayRoot);
    }

    @Override
    public void onInternetErrorForgotPasswordApi() {
        SnackNotify.checkConnection(onRetyForgotPasswordOtpApi, relLayRoot);
    }

    OnClickInterface onRetyForgotPasswordOtpApi = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };
    //--------------------------------------------------------------------------------

    private void setFont() {

        FontHelper.setFontFace(txtViewSixDigit, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Normal, this);
        txtViewTitle.setText(getString(R.string.reset_password));

        FontHelper.applyFont(this, edtTextFirstDigit, FontHelper.FontType.FONT_Semi_Bold);
        FontHelper.applyFont(this, edtTextSecondDigit, FontHelper.FontType.FONT_Semi_Bold);
        FontHelper.applyFont(this, edtTextThirdDigit, FontHelper.FontType.FONT_Semi_Bold);
        FontHelper.applyFont(this, edtTextFourthDigit, FontHelper.FontType.FONT_Semi_Bold);
        FontHelper.applyFont(this, edtTextFivethDigit, FontHelper.FontType.FONT_Semi_Bold);
        FontHelper.applyFont(this, edtTextSixthDigit, FontHelper.FontType.FONT_Semi_Bold);

        FontHelper.applyFont(this, edtTextPassword, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextConfirmPassword, FontHelper.FontType.FONT_Normal);

        FontHelper.applyFont(this, btnSubmit, FontHelper.FontType.FONT_Normal);
    }


    private void getData() {
        String otp = String.valueOf(ch);
        String password = edtTextPassword.getText().toString();
        String confirmPassword = edtTextConfirmPassword.getText().toString();

        ForgotPasswordOtpPresenterImpl forgotPasswordOtpPresenterImpl = new ForgotPasswordOtpPresenterImpl(this, this);
        forgotPasswordOtpPresenterImpl.callingForgotPasswordOtpApi(otp,password,confirmPassword);
    }

    @OnEditorAction(R.id.edtTextConfirmPassword)
    protected boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            getData();
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        getData();
    }


    public class AddListenerOnTextChange implements TextWatcher {

        EditText meditText;
        Context context;

        public AddListenerOnTextChange(Context context, EditText editText) {
            this.meditText = editText;
            this.context = context;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (meditText == edtTextFirstDigit) {

                if (editable.length() > 0) {
                    ch[0] = editable.toString().charAt(0);
                    edtTextSecondDigit.requestFocus();
                } else {
                    ch[0] = ' ';
                    edtTextFirstDigit.requestFocus();
                }

            } else if (meditText == edtTextSecondDigit) {
                if (editable.length() > 0) {
                    ch[1] = editable.toString().charAt(0);
                    edtTextThirdDigit.requestFocus();
                } else {
                    ch[1] = ' ';
                    edtTextSecondDigit.requestFocus();
                }


            } else if (meditText == edtTextThirdDigit) {
                if (editable.length() > 0) {
                    ch[2] = editable.toString().charAt(0);
                    edtTextFourthDigit.requestFocus();
                } else {
                    ch[2] = ' ';
                    edtTextThirdDigit.requestFocus();
                }

            } else if (meditText == edtTextFourthDigit) {
                if (editable.length() > 0) {
                    ch[3] = editable.toString().charAt(0);
                    edtTextFivethDigit.requestFocus();
                } else {
                    ch[3] = ' ';
                    edtTextFourthDigit.requestFocus();
                }

            } else if (meditText == edtTextFivethDigit) {
                if (editable.length() > 0) {
                    ch[4] = editable.toString().charAt(0);
                    edtTextSixthDigit.requestFocus();
                } else {
                    ch[4] = ' ';
                    edtTextFivethDigit.requestFocus();
                }

            } else if (meditText == edtTextSixthDigit) {
                if (editable.length() > 0) {
                    ch[5] = editable.toString().charAt(0);
                    edtTextPassword.requestFocus();
                } else {
                    ch[5] = ' ';
                    edtTextSixthDigit.requestFocus();
                }

            }
        }
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
