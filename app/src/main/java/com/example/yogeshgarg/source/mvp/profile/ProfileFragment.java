package com.example.yogeshgarg.source.mvp.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ProfileFragment extends Fragment {

    @BindView(R.id.imgViewProfile)
    ImageView imgViewProfile;

    @BindView(R.id.txtViewTitleName)
    TextView txtViewTitleName;

    @BindView(R.id.edtTextName)
    EditText edtTextName;

    @BindView(R.id.imgViewNameEdit)
    ImageView imgViewNameEdit;

    @BindView(R.id.txtViewTitleEmail)
    TextView txtViewTitleEmail;

    @BindView(R.id.edtTextEmail)
    EditText edtTextEmail;

    @BindView(R.id.imgViewEmailEdit)
    ImageView imgViewEmailEdit;

    @BindView(R.id.txtViewTitlePassword)
    TextView txtViewTitlePassword;

    @BindView(R.id.edtTextPassword)
    EditText edtTextPassword;

    @BindView(R.id.imgViewPasswordEdit)
    ImageView imgViewPasswordEdit;

    @BindView(R.id.txtViewTitlePhoneNumber)
    TextView txtViewTitlePhoneNumber;

    @BindView(R.id.edtTextPhoneNumber)
    EditText edtTextPhoneNumber;

    @BindView(R.id.imgViewPhoneNumberEdit)
    ImageView imgViewPhoneNumberEdit;

    @BindView(R.id.txtViewTitleUser)
    TextView txtViewTitleUser;

    @BindView(R.id.txtViewUser)
    TextView txtViewUser;

    @BindView(R.id.txtViewTitleStore)
    TextView txtViewTitleStore;

    @BindView(R.id.txtViewStore)
    TextView txtViewStore;

    @BindView(R.id.txtViewTitleCompany)
    TextView txtViewTitleCompany;

    @BindView(R.id.txtViewCompany)
    TextView txtViewCompany;

    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


}
