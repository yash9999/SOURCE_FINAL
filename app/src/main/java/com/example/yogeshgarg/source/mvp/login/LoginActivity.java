package com.example.yogeshgarg.source.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.forgot_password.ForgotPasswordActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;


    @BindView(R.id.edtTextUsername)
    EditText edtTextUsername;

    @BindView(R.id.edtTextpassword)
    EditText edtTextpassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.txtViewForgotPassword)
    TextView txtViewForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setFont();

    }

    @Override
    public void onLoginSuccess(LoginModel.Result result) {
        String id = result.getId();
        String token = result.getToken();

        UserSession userSession = new UserSession(this);
        userSession.createUserSession(id, token);

        Intent intent=new Intent(LoginActivity.this, StoresActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginUnsuccess(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void internetError() {
        SnackNotify.checkConnection(onRetryLoginApi, coordinateLayout);
    }

    OnClickInterface onRetryLoginApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingLoginApi();
        }
    };

    //-------------------------------------- other method---------------------------------------
    private void callingLoginApi() {
        getData();
    }

    private void getData() {

        String username = edtTextUsername.getText().toString();
        String password = edtTextpassword.getText().toString();

        LoginPresenterImpl loginPresenterImpl = new LoginPresenterImpl(this, this);
        loginPresenterImpl.fetchingLoginApiData(username, password);
    }

    @OnClick(R.id.btnLogin)
    public void btnSigninClick() {
        callingLoginApi();
    }

    @OnEditorAction(R.id.edtTextpassword)
    public boolean actionDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            callingLoginApi();
            return true;
        }
        return false;
    }

    @OnClick(R.id.txtViewForgotPassword)
    public void txtViewForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void setFont() {
        FontHelper.setFontFace(edtTextUsername, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(edtTextpassword, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(btnLogin, FontHelper.FontType.FONT_Semi_Bold, this);
        FontHelper.setFontFace(txtViewForgotPassword, FontHelper.FontType.FONT_Normal, this);

    }
}
