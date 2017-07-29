package com.example.yogeshgarg.source.mvp.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.mvp.banded.BandedProductFragment;
import com.example.yogeshgarg.source.mvp.change_store.ChangeStoreFragment;
import com.example.yogeshgarg.source.mvp.dashboard.DashboardFragment;
import com.example.yogeshgarg.source.mvp.expiring_product.ExpiringProductFragment;
import com.example.yogeshgarg.source.mvp.home.HomeFragment;
import com.example.yogeshgarg.source.mvp.in_store_sampling.InStoreSamplingFragment;
import com.example.yogeshgarg.source.mvp.inbox.InboxFragment;
import com.example.yogeshgarg.source.mvp.new_product.NewProductFragment;
import com.example.yogeshgarg.source.mvp.notification.NotificationFragment;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyFragment;
import com.example.yogeshgarg.source.mvp.profile.ProfileFragment;
import com.example.yogeshgarg.source.mvp.setting.SettingFragment;
import com.example.yogeshgarg.source.mvp.sync.SyncFragment;
import com.example.yogeshgarg.source.mvp.team.MyTeamFragment;
import com.example.yogeshgarg.source.mvp.vacation.VacationFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.imgViewMenu)
    ImageView imgViewMenu;

    @BindView(R.id.imgViewProfileDp)
    ImageView imgViewProfileDp;

    @BindView(R.id.txtViewSourceTitle)
    TextView txtViewSourceTitle;

    @BindView(R.id.imgViewHelp)
    ImageView imgViewHelp;

    @BindView(R.id.imgViewProfile)
    ImageView imgViewProfile;

    @BindView(R.id.txtViewName)
    TextView txtViewName;

    @BindView(R.id.txtViewLocation)
    TextView txtViewLocation;

    @BindView(R.id.txtViewDesignation)
    TextView txtViewDesignation;

    @BindView(R.id.txtViewHome)
    TextView txtViewHome;

    @BindView(R.id.txtViewProfile)
    TextView txtViewProfile;

    @BindView(R.id.txtViewInbox)
    TextView txtViewInbox;

    @BindView(R.id.txtViewNotifications)
    TextView txtViewNotifications;

    @BindView(R.id.txtViewProductUpdate)
    TextView txtViewProductUpdate;

    @BindView(R.id.txtViewPriceSurvey)
    TextView txtViewPriceSurvey;

    @BindView(R.id.txtViewNewProduct)
    TextView txtViewNewProduct;


    @BindView(R.id.txtViewBandedProduct)
    TextView txtViewBandedProduct;

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

    @BindView(R.id.txtViewSync)
    TextView txtViewSync;

    //-------------------------------------Linear layout -----------------------------------//

    @BindView(R.id.linLayHome)
    LinearLayout linLayHome;

    @BindView(R.id.linLayInbox)
    LinearLayout linLayInbox;

    @BindView(R.id.linLayNotifications)
    LinearLayout linLayNotifications;

    @BindView(R.id.linLayPriceSurvey)
    LinearLayout linLayPriceSurvey;

    @BindView(R.id.linLayNewProduct)
    LinearLayout linLayNewProduct;


    @BindView(R.id.linLayBandedProduct)
    LinearLayout linLayBandedProduct;

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

    @BindView(R.id.linLaySync)
    LinearLayout linLaySync;

    DrawerLayout drawer;
    // fragment instances

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ButterKnife.bind(this);


        fragmentManager = getSupportFragmentManager();

        DashboardFragment homeFragment=new DashboardFragment();
        addFragment(homeFragment,getString(R.string.label_home));

        Picasso.with(this).load(R.mipmap.ic_profile_dp).transform(new CircleTransform()).into(imgViewProfileDp);
        setFont();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                super.onBackPressed();
            } else {
                finish();
            }

        }
    }


    //-------------------------- custom methods----------------------------

    private void setFont() {

        FontHelper.setFontFace(txtViewSourceTitle, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewName, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewLocation, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewDesignation, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewHome, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewInbox, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewProfile, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewNotifications, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewProductUpdate, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewPriceSurvey, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewNewProduct, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewBandedProduct, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewExpiryProduct, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewInStoreProduct, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewGeneral, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewSetting, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewVacation, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewChangeStore, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewSync, FontHelper.FontType.FONT_Normal, this);

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


    @OnClick(R.id.linLayHome)
    public void linLayHomeClick() {
        DashboardFragment homeFragment = new DashboardFragment();
        replaceFragment(homeFragment, getString(R.string.label_home));
    }

    @OnClick(R.id.linLayProfile)
    public void linLayProfileClick() {
        ProfileFragment profileFragment = new ProfileFragment();
        replaceFragment(profileFragment, getString(R.string.label_profile));
    }

    @OnClick(R.id.linLayInbox)
    public void linLayInboxClick() {
        InboxFragment inboxFragment = new InboxFragment();
        replaceFragment(inboxFragment, getString(R.string.label_inbox));
    }

    @OnClick(R.id.linLayNotifications)
    public void linLayNotificationsClick() {
        MyTeamFragment fragment=new MyTeamFragment();
        replaceFragment(fragment, getString(R.string.lable_my_team));
       /* NotificationFragment notificationFragment = new NotificationFragment();
        replaceFragment(notificationFragment, getString(R.string.label_notification));*/
    }

    @OnClick(R.id.linLayPriceSurvey)
    public void linLayPriceSurveyClick() {
        PriceSurveyFragment priceSurveyFragment = new PriceSurveyFragment();
        replaceFragment(priceSurveyFragment, getString(R.string.label_price_survey));
    }

    @OnClick(R.id.linLayNewProduct)
    public void linLayNewProductClick() {
        NewProductFragment newProductFragment = new NewProductFragment();
        replaceFragment(newProductFragment, getString(R.string.label_new_product));
    }

    @OnClick(R.id.linLayBandedProduct)
    public void linLayBandedProductClick() {
        BandedProductFragment bandedProductFragment = new BandedProductFragment();
        replaceFragment(bandedProductFragment, getString(R.string.label_banded_product));
    }

    @OnClick(R.id.linLayExpiryProduct)
    public void linLayExpiryProductClick() {
        ExpiringProductFragment expiringProductFragment = new ExpiringProductFragment();
        replaceFragment(expiringProductFragment, getString(R.string.label_expiring_product));
    }

    @OnClick(R.id.linLayInStoreProduct)
    public void linLayInStoreProduct() {
        InStoreSamplingFragment inStoreSamplingFragment = new InStoreSamplingFragment();
        replaceFragment(inStoreSamplingFragment, getString(R.string.label_in_stroe_product));
    }

    @OnClick(R.id.linLaySetting)
    public void linLaySettingClick() {
        SettingFragment settingFragment = new SettingFragment();
        replaceFragment(settingFragment, getString(R.string.label_setting));
    }

    @OnClick(R.id.linLayVacation)
    public void linLayVacationClick() {
        VacationFragment vacationFragment = new VacationFragment();
        replaceFragment(vacationFragment, getString(R.string.label_vacation));
    }

    @OnClick(R.id.linLayChangeStore)
    public void setLinLayChangeStoreClick() {
        ChangeStoreFragment changeStoreFragment = new ChangeStoreFragment();
        replaceFragment(changeStoreFragment, getString(R.string.label_change_store));
    }

    @OnClick(R.id.linLaySync)
    public void linLaySyncClick() {
        SyncFragment syncFragment = new SyncFragment();
        replaceFragment(syncFragment, getString(R.string.label_sync));
    }

    private void drawerOpen() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

}
