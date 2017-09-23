package com.example.yogeshgarg.source.mvp.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.AlertDialogManager;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.mvp.about.AboutFragment;
import com.example.yogeshgarg.source.mvp.faq.FAQFragment;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.notification.NotificationFragment;
import com.example.yogeshgarg.source.mvp.notification_act.NotificationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingFragment extends Fragment implements SettingView {


    @BindView(R.id.linLayNotifications)
    LinearLayout linLayNotifications;

    @BindView(R.id.txtViewNotifications)
    TextView txtViewNotifications;

    @BindView(R.id.linLayAbout)
    LinearLayout linLayAbout;

    @BindView(R.id.txtViewAbout)
    TextView txtViewAbout;

    @BindView(R.id.linLayFAQ)
    LinearLayout linLayFAQ;

    @BindView(R.id.txtViewFAQ)
    TextView txtViewFAQ;

    @BindView(R.id.linLayLogout)
    LinearLayout linLayLogout;

    @BindView(R.id.txtViewLogout)
    TextView txtViewLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        setFont();
        return view;
    }


    @Override
    public void onSuccessSettingApi() {

    }

    @Override
    public void onUnsuccessSettingApi(String message) {

    }

    @Override
    public void OnInternetErrorSettingApi() {

    }

    OnClickInterface onRetrySettingApi = new OnClickInterface() {
        @Override
        public void onClick() {

        }
    };

    private void setFont() {
        FontHelper.setFontFace(txtViewNotifications, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewAbout, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewFAQ, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewLogout, FontHelper.FontType.FONT_Normal, getActivity());
    }

    @OnClick(R.id.linLayNotifications)
    public void linLayNotificationsClick() {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linLayAbout)
    public void linLayAboutClick() {
        AboutFragment aboutFragment = new AboutFragment();
        ((NavigationActivity) getActivity()).replaceFragment(aboutFragment, getActivity().getString(R.string.lable_about));
    }

    @OnClick(R.id.linLayFAQ)
    public void linLayFAQClick() {
        FAQFragment faqFragment = new FAQFragment();
        ((NavigationActivity) getActivity()).replaceFragment(faqFragment, getActivity().getString(R.string.label_faq));
    }

    @OnClick(R.id.linLayLogout)
    public void linLayLogoutClick() {
        AlertDialogManager.showAlertLogout(getActivity());
    }


}
