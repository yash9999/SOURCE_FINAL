package com.example.yogeshgarg.source.mvp.notification_act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity implements NotificationSettingView {


    @BindView(R.id.relLayout)
    RelativeLayout relLayout;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewInbox)
    TextView txtViewInbox;

    @BindView(R.id.txtViewPriceSurvey)
    TextView txtViewPriceSurvey;

    @BindView(R.id.txtViewNewProduct)
    TextView txtViewNewProduct;

    @BindView(R.id.txtViewExpiryProduct)
    TextView txtViewExpiryProduct;

    @BindView(R.id.txtViewInStoreSampling)
    TextView txtViewInStoreSampling;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    /* @BindView(R.id.imgViewInboxCheckForPhone)
     ImageView imgViewInboxCheckForPhone;

     @BindView(R.id.imgViewInboxCheckForMessage)
     ImageView imgViewInboxCheckForMessage;
 */
    @BindView(R.id.imgViewPriceSurveyCheckForPhone)
    ImageView imgViewPriceSurveyCheckForPhone;

    @BindView(R.id.imgViewPriceSurveyCheckForMessage)
    ImageView imgViewPriceSurveyCheckForMessage;


    @BindView(R.id.imgViewNewProductCheckForPhone)
    ImageView imgViewNewProductCheckForPhone;

    @BindView(R.id.imgViewNewProductCheckForMessage)
    ImageView imgViewNewProductCheckForMessage;


    @BindView(R.id.imgViewExpiryProductCheckForPhone)
    ImageView imgViewExpiryProductCheckForPhone;

    @BindView(R.id.imgViewExpiryProductCheckForMessage)
    ImageView imgViewExpiryProductCheckForMessage;


    @BindView(R.id.imgViewSamplingCheckForPhone)
    ImageView imgViewSamplingCheckForPhone;

    @BindView(R.id.imgViewSamplingCheckForMessage)
    ImageView imgViewSamplingCheckForMessage;


    boolean priceSurveyPhone = false, priceSurveyEmail = false;
    boolean newProductPhone = false, newProductEmail = false;
    boolean expiryProductPhone = false, expiryProductEmail = false;
    boolean inStoreSamplingPhone = false, inStoreSamplingEmail = false;


    NotificationSettingPresenterImpl notificationSettingPresenterImpl;
    String sampling;
    String expiry;
    String notification;
    String newProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        setFont();
        notificationSettingPresenterImpl = new NotificationSettingPresenterImpl(this, this);
        callingNotificationUpdate();
    }

    private void setFont() {
        txtViewTitle.setVisibility(View.VISIBLE);
        txtViewTitle.setText("Notification's Settings");
        FontHelper.applyFont(this, txtViewTitle, FontHelper.FontType.FONT_LIGHT);
        FontHelper.setFontFace(txtViewInbox, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewPriceSurvey, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewNewProduct, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewExpiryProduct, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewInStoreSampling, FontHelper.FontType.FONT_Normal, this);
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public void onSuccessNotificationUpdate() {

    }

    @Override
    public void onUnscuuessNotificationUpdate(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetErrorUpdate() {
        SnackNotify.checkConnection(onRetryUpdatingTheSetting, relLayout);
    }

    OnClickInterface onRetryUpdatingTheSetting = new OnClickInterface() {
        @Override
        public void onClick() {
            callingNotificationChanging();
        }
    };

    //-----------------------------------------------------------------------------------------------------

    @Override
    public void onSuccessNotification(NotificationSettingModel.Result result) {
        notification = result.getNotification();
        expiry = result.getExpiry();
        newProduct = result.getNewProduct();
        sampling = result.getSampling();


        if (notification.equals("2")) {
            priceSurveyEmail = true;
            priceSurveyPhone = true;
            imgViewPriceSurveyCheckForPhone.setImageResource(R.mipmap.ic_check_box);
            imgViewPriceSurveyCheckForMessage.setImageResource(R.mipmap.ic_check_box);
        } else if (notification.equals("0")) {
            priceSurveyPhone = true;
            priceSurveyEmail = false;
            imgViewPriceSurveyCheckForPhone.setImageResource(R.mipmap.ic_check_box);
            imgViewPriceSurveyCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
        } else if (notification.equals("1")) {
            priceSurveyEmail = true;
            priceSurveyPhone = false;
            imgViewPriceSurveyCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
            imgViewPriceSurveyCheckForMessage.setImageResource(R.mipmap.ic_check_box);
        } else if (notification.equals("3")) {
            priceSurveyEmail = false;
            priceSurveyPhone = false;
            imgViewPriceSurveyCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
            imgViewPriceSurveyCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
        }

        if (expiry.equals("2")) {
            expiryProductPhone = true;
            expiryProductEmail = true;
            imgViewExpiryProductCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            imgViewExpiryProductCheckForPhone.setImageResource(R.mipmap.ic_check_box);
        } else if (expiry.equals("0")) {
            expiryProductPhone = true;
            expiryProductEmail = false;
            imgViewExpiryProductCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            imgViewExpiryProductCheckForPhone.setImageResource(R.mipmap.ic_check_box);
        } else if (expiry.equals("1")) {
            expiryProductPhone = false;
            expiryProductEmail = true;
            imgViewExpiryProductCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            imgViewExpiryProductCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
        } else if (expiry.equals("3")) {
            expiryProductPhone = false;
            expiryProductEmail = false;
            imgViewExpiryProductCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            imgViewExpiryProductCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
        }


        if (newProduct.equals("2")) {
            newProductPhone = true;
            newProductEmail = true;
            imgViewNewProductCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            imgViewNewProductCheckForPhone.setImageResource(R.mipmap.ic_check_box);
        } else if (newProduct.equals("0")) {
            newProductPhone = true;
            newProductEmail = false;
            imgViewNewProductCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            imgViewNewProductCheckForPhone.setImageResource(R.mipmap.ic_check_box);
        } else if (newProduct.equals("1")) {
            newProductPhone = false;
            newProductEmail = true;
            imgViewNewProductCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            imgViewNewProductCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
        } else if (newProduct.equals("3")) {
            newProductPhone = false;
            newProductEmail = false;
            imgViewNewProductCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            imgViewNewProductCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
        }

        if (sampling.equals("2")) {
            inStoreSamplingEmail = true;
            inStoreSamplingPhone = true;
            imgViewSamplingCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            imgViewSamplingCheckForPhone.setImageResource(R.mipmap.ic_check_box);
        } else if (sampling.equals("0")) {
            inStoreSamplingPhone = true;
            inStoreSamplingEmail = false;
            imgViewSamplingCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            imgViewSamplingCheckForPhone.setImageResource(R.mipmap.ic_check_box);
        } else if (sampling.equals("1")) {
            inStoreSamplingPhone = false;
            inStoreSamplingEmail = true;
            imgViewSamplingCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            imgViewSamplingCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
        } else if (sampling.equals("3")) {
            inStoreSamplingEmail = false;
            inStoreSamplingPhone = false;
            imgViewSamplingCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            imgViewSamplingCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
        }

    }

    @Override
    public void onUnsuccessNotification(String message) {
        SnackNotify.showMessage(message, relLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryNotifiation, relLayout);
    }

    OnClickInterface onRetryNotifiation = new OnClickInterface() {
        @Override
        public void onClick() {
            callingNotificationUpdate();
        }
    };

    //-------------------------------------------------------------------------------------------------------

    //for changing tsetting on server
    private void callingNotificationChanging() {
        notificationSettingPresenterImpl.callingNotificationUpdateSettingApi(sampling, expiry, newProduct, notification);
    }

    //at local site
    private void callingNotificationUpdate() {
        notificationSettingPresenterImpl.callingNotificationUpdateApi();
    }


    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        getData();
    }

    public void getData() {
        notificationValue();
        inStoreSamplingValue();
        expiryProductValue();
        newProductValue();
        callingNotificationChanging();
    }

    @OnClick(R.id.imgViewPriceSurveyCheckForPhone)
    public void imgViewPriceSurveyCheckForPhoneClick() {
        if (priceSurveyPhone) {
            imgViewPriceSurveyCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
            priceSurveyPhone = false;
        } else {
            imgViewPriceSurveyCheckForPhone.setImageResource(R.mipmap.ic_check_box);
            priceSurveyPhone = true;
        }
    }

    @OnClick(R.id.imgViewPriceSurveyCheckForMessage)
    public void imgViewPriceSurveyCheckForMessageClick() {
        if (priceSurveyEmail) {
            imgViewPriceSurveyCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            priceSurveyEmail = false;
        } else {
            imgViewPriceSurveyCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            priceSurveyEmail = true;
        }
    }


    @OnClick(R.id.imgViewNewProductCheckForPhone)
    public void imgViewNewProductCheckForPhoneClikc() {
        if (newProductPhone) {
            imgViewNewProductCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
            newProductPhone = false;
        } else {
            imgViewNewProductCheckForPhone.setImageResource(R.mipmap.ic_check_box);
            newProductPhone = true;
        }
    }

    @OnClick(R.id.imgViewNewProductCheckForMessage)
    public void imgViewNewProductCheckForMessageClick() {
        if (newProductEmail) {
            imgViewNewProductCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            newProductEmail = false;
        } else {
            imgViewNewProductCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            newProductEmail = true;
        }
    }


    @OnClick(R.id.imgViewExpiryProductCheckForPhone)
    public void imgViewExpiryProductCheckForPhoneClick() {
        if (expiryProductPhone) {
            imgViewExpiryProductCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
            expiryProductPhone = false;
        } else {
            imgViewExpiryProductCheckForPhone.setImageResource(R.mipmap.ic_check_box);
            expiryProductPhone = true;
        }
    }

    @OnClick(R.id.imgViewExpiryProductCheckForMessage)
    public void imgViewExpiryProductCheckForMessageClick() {
        if (expiryProductEmail) {
            imgViewExpiryProductCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            expiryProductEmail = false;
        } else {
            imgViewExpiryProductCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            expiryProductEmail = true;
        }
    }


    @OnClick(R.id.imgViewSamplingCheckForPhone)
    public void imgViewSamplingCheckForPhoneClick() {
        if (inStoreSamplingPhone) {
            imgViewSamplingCheckForPhone.setImageResource(R.mipmap.ic_blank_cb);
            inStoreSamplingPhone = false;
        } else {
            imgViewSamplingCheckForPhone.setImageResource(R.mipmap.ic_check_box);
            inStoreSamplingPhone = true;
        }
    }

    @OnClick(R.id.imgViewSamplingCheckForMessage)
    public void imgViewSamplingCheckForMessageClick() {
        if (inStoreSamplingEmail) {
            imgViewSamplingCheckForMessage.setImageResource(R.mipmap.ic_blank_cb);
            inStoreSamplingEmail = false;
        } else {
            imgViewSamplingCheckForMessage.setImageResource(R.mipmap.ic_check_box);
            inStoreSamplingEmail = true;
        }
    }


    private void notificationValue() {
        if (priceSurveyPhone && priceSurveyEmail) {
            notification = "2";//means both
        } else if (priceSurveyPhone) {
            notification = "0";//means only notification
        } else if (priceSurveyEmail) {
            notification = "1";//means only email
        } else {
            notification = "3";//means nothing
        }
    }

    private void newProductValue() {
        if (newProductEmail && newProductPhone) {
            newProduct = "2";
        } else if (newProductPhone) {
            newProduct = "0";
        } else if (newProductEmail) {
            newProduct = "1";
        } else {
            newProduct = "3";
        }
    }

    private void expiryProductValue() {
        if (expiryProductPhone && expiryProductEmail) {
            expiry = "2";
        } else if (expiryProductPhone) {
            expiry = "0";
        } else if (expiryProductEmail) {
            expiry = "1";
        } else {
            expiry = "3";
        }
    }

    private void inStoreSamplingValue() {
        if (inStoreSamplingEmail && inStoreSamplingPhone) {
            sampling = "2";
        } else if (inStoreSamplingPhone) {
            sampling = "0";
        } else if (inStoreSamplingEmail) {
            sampling = "1";
        } else {
            sampling = "3";
        }
    }

}
