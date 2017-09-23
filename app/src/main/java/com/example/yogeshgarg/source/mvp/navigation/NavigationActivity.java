package com.example.yogeshgarg.source.mvp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.notification.NotificationFragment;
import com.example.yogeshgarg.source.mvp.price_analysis.PriceAnalysisFragment;
import com.example.yogeshgarg.source.mvp.dashboard.DashboardFragment;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home.ExpiringProductFragment;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_home.InStoreSamplingFragment;
import com.example.yogeshgarg.source.mvp.inbox.InboxFragment;
import com.example.yogeshgarg.source.mvp.new_product.new_product_home.NewProductFragment;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyFragment;
import com.example.yogeshgarg.source.mvp.profile.ProfileFragment;
import com.example.yogeshgarg.source.mvp.setting.SettingFragment;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.example.yogeshgarg.source.mvp.sync.SyncFragment;
import com.example.yogeshgarg.source.mvp.team.MyTeamFragment;
import com.example.yogeshgarg.source.mvp.vacation.vacation_home.VacationHomeFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.imgViewProfileDp)
    ImageView imgViewProfileDp;

    @BindView(R.id.txtViewName)
    TextView txtViewName;

    @BindView(R.id.txtViewLocation)
    TextView txtViewLocation;

    @BindView(R.id.txtViewDesignation)
    TextView txtViewDesignation;

//------------------------------------------------

    @BindView(R.id.imgViewMenu)
    ImageView imgViewMenu;


    @BindView(R.id.txtViewPageName)
    TextView txtViewPageName;

//------------------------------------------------


    @BindView(R.id.txtViewDashboard)
    TextView txtViewDashboard;

    @BindView(R.id.txtViewProfile)
    TextView txtViewProfile;

    @BindView(R.id.txtViewMyTeam)
    TextView txtViewMyTeam;

    @BindView(R.id.txtViewInbox)
    TextView txtViewInbox;

    @BindView(R.id.txtViewNotifications)
    TextView txtViewNotifications;


    @BindView(R.id.txtViewProductUpdate)
    TextView txtViewProductUpdate;


    @BindView(R.id.txtViewPriceSurvey)
    TextView txtViewPriceSurvey;

    @BindView(R.id.txtViewPriceAnalysis)
    TextView txtViewPriceAnalysis;

    @BindView(R.id.txtViewNewProduct)
    TextView txtViewNewProduct;

    @BindView(R.id.txtViewExpiryProduct)
    TextView txtViewExpiryProduct;

    @BindView(R.id.txtViewInStoreProduct)
    TextView txtViewInStoreProduct;


    @BindView(R.id.txtViewGeneral)
    TextView txtViewGeneral;


    @BindView(R.id.txtViewSetting)
    TextView txtViewSetting;

    @BindView(R.id.txtViewVacation)
    TextView txtViewVacation;


    @BindView(R.id.txtViewChangeStore)
    TextView txtViewChangeStore;


    //-------------------------------------Linear layout -----------------------------------//

    @BindView(R.id.linLayDashboard)
    LinearLayout linLayDashboard;

    @BindView(R.id.linLayProfile)
    LinearLayout linLayProfile;

    @BindView(R.id.linLayMyTeam)
    LinearLayout linLayMyTeam;

    @BindView(R.id.linLayInbox)
    LinearLayout linLayInbox;

    @BindView(R.id.linLayNotifications)
    LinearLayout linLayNotifications;


    @BindView(R.id.linLayPriceAnalysis)
    LinearLayout linLayPriceAnalysis;

    @BindView(R.id.linLayPriceSurvey)
    LinearLayout linLayPriceSurvey;

    @BindView(R.id.linLayNewProduct)
    LinearLayout linLayNewProduct;

    @BindView(R.id.linLayExpiryProduct)
    LinearLayout linLayExpiryProduct;

    @BindView(R.id.linLayInStoreProduct)
    LinearLayout linLayInStoreProduct;


    @BindView(R.id.linLaySetting)
    LinearLayout linLaySetting;

    @BindView(R.id.linLayVacation)
    LinearLayout linLayVacation;


    @BindView(R.id.linLayChangeStore)
    LinearLayout linLayChangeStore;


    DrawerLayout drawer;
    // fragment instances

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ButterKnife.bind(this);


        fragmentManager = getSupportFragmentManager();
        Picasso.with(this).load(R.mipmap.ic_profile_dp).transform(new CircleTransform()).into(imgViewProfileDp);
        setFont();


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            boolean openNotification = getIntent().getBooleanExtra("OpenNotification", false);

            if (openNotification) {
                NotificationFragment notificationFragment=new NotificationFragment();
                replaceFragment(notificationFragment,getString(R.string.label_notification));
            }

            String fragmentType = getIntent().getStringExtra(ConstIntent.FRAGMENT_TYPE);


            if (fragmentType.equals("openExpiryProductCalendar")) {
                ExpiringProductFragment expiringProductFragment = new ExpiringProductFragment();
                txtViewPageName.setText(R.string.expiring_product);
                addFragment(expiringProductFragment, getString(R.string.label_expiring_product));
            } else if (fragmentType.equals("inStoreSamplingScreenOpen")) {
                InStoreSamplingFragment inStoreSamplingFragment = new InStoreSamplingFragment();
                txtViewPageName.setText(R.string.in_store_sampling);
                addFragment(inStoreSamplingFragment, getString(R.string.label_in_stroe_product));
            } else if (fragmentType.equals("vacationScreenOpen")) {
                VacationHomeFragment vacationHomeFragment = new VacationHomeFragment();
                txtViewPageName.setText(R.string.vacation);
                addFragment(vacationHomeFragment, getString(R.string.label_vacation));
            }
        } else {
            DashboardFragment dashboardFragment = new DashboardFragment();
            addFragment(dashboardFragment, getString(R.string.label_dashboard));
            txtViewPageName.setText(R.string.dashboard);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }


    //-------------------------- custom methods----------------------------

    private void setFont() {

        FontHelper.setFontFace(txtViewPageName, FontHelper.FontType.FONT_LIGHT, this);

        FontHelper.setFontFace(txtViewName, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewLocation, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewDesignation, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewDashboard, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewProfile, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewMyTeam, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewInbox, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewNotifications, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewProductUpdate, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewPriceAnalysis, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewPriceSurvey, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewNewProduct, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewExpiryProduct, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewInStoreProduct, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewGeneral, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewSetting, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewVacation, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewChangeStore, FontHelper.FontType.FONT_Normal, this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    @OnClick(R.id.imgViewMenu)
    public void setImgViewMenuClick() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }


    public void addFragment(Fragment fragment, String tag) {
        drawerOpen();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        drawerOpen();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
    // click event for fragment replace


    @OnClick(R.id.linLayDashboard)
    public void linLayDashboardClick() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        txtViewPageName.setText(R.string.dashboard);
        replaceFragment(dashboardFragment, getString(R.string.label_dashboard));
    }

    @OnClick(R.id.linLayProfile)
    public void linLayProfileClick() {
        ProfileFragment profileFragment = new ProfileFragment();
        txtViewPageName.setText(R.string.profile);
        replaceFragment(profileFragment, getString(R.string.label_profile));
    }

    @OnClick(R.id.linLayMyTeam)
    public void linLayMyTeamClick() {
        MyTeamFragment myTeamFragment = new MyTeamFragment();
        txtViewPageName.setText(R.string.my_team);
        replaceFragment(myTeamFragment, getString(R.string.label_my_team));
    }

    @OnClick(R.id.linLayInbox)
    public void linLayInboxClick() {
        InboxFragment inboxFragment = new InboxFragment();
        txtViewPageName.setText(R.string.inbox);
        replaceFragment(inboxFragment, getString(R.string.label_inbox));
    }

    @OnClick(R.id.linLayNotifications)
    public void linLayNotificationsClick() {
        NotificationFragment notificationFragment = new NotificationFragment();
        txtViewPageName.setText(R.string.notifications);
        replaceFragment(notificationFragment, getString(R.string.label_notification));
    }


    @OnClick(R.id.linLayPriceAnalysis)
    public void linLayPriceAnalysisClick() {
        PriceAnalysisFragment priceAnalysisFragment = new PriceAnalysisFragment();
        txtViewPageName.setText(R.string.price_analysis);
        replaceFragment(priceAnalysisFragment, getString(R.string.label_price_analysis));
    }

    @OnClick(R.id.linLayPriceSurvey)
    public void linLayPriceSurveyClick() {
        PriceSurveyFragment priceSurveyFragment = new PriceSurveyFragment();
        txtViewPageName.setText(R.string.price_survey);
        replaceFragment(priceSurveyFragment, getString(R.string.label_price_survey));
    }

    @OnClick(R.id.linLayNewProduct)
    public void linLayNewProductClick() {
        NewProductFragment newProductFragment = new NewProductFragment();
        txtViewPageName.setText(R.string.new_product);
        replaceFragment(newProductFragment, getString(R.string.label_new_product));
    }


    @OnClick(R.id.linLayExpiryProduct)
    public void linLayExpiryProductClick() {
        ExpiringProductFragment expiringProductFragment = new ExpiringProductFragment();
        txtViewPageName.setText(R.string.expiring_product);
        replaceFragment(expiringProductFragment, getString(R.string.label_expiring_product));
    }

    @OnClick(R.id.linLayInStoreProduct)
    public void linLayInStoreProduct() {
        InStoreSamplingFragment inStoreSamplingFragment = new InStoreSamplingFragment();
        txtViewPageName.setText(R.string.in_store_sampling);
        replaceFragment(inStoreSamplingFragment, getString(R.string.label_in_stroe_product));
    }


    @OnClick(R.id.linLaySetting)
    public void linLaySettingClick() {
        SettingFragment settingFragment = new SettingFragment();
        txtViewPageName.setText(R.string.settings);
        replaceFragment(settingFragment, getString(R.string.label_setting));
    }

    @OnClick(R.id.linLayVacation)
    public void linLayVacationClick() {
        VacationHomeFragment vacationHomeFragment = new VacationHomeFragment();
        txtViewPageName.setText(R.string.vacation);
        replaceFragment(vacationHomeFragment, getString(R.string.label_vacation));
    }

    @OnClick(R.id.linLayChangeStore)
    public void setLinLayChangeStoreClick() {
        Intent intent = new Intent(NavigationActivity.this, StoresActivity.class);
        startActivity(intent);
    }


    private void drawerOpen() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


}
