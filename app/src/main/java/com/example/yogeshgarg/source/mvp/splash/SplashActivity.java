package com.example.yogeshgarg.source.mvp.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.mvp.ProductUpdate.ProductUpdateActivity;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 2000;

    @BindView(R.id.txtViewSource)
    TextView txtViewSource;

    @BindView(R.id.txtViewSplashMsg)
    TextView txtViewSplashMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setFont();

        final UserSession userSession = new UserSession(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userSession.isUserLoggedIn()) {
                    if (userSession.isLocationStatusSet()) {
                        Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, StoresActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    private void setFont() {
        FontHelper.applyFont(this, txtViewSource, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, txtViewSplashMsg, FontHelper.FontType.FONT_Normal);
    }

}
