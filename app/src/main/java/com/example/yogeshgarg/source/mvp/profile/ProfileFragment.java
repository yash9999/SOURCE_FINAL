package com.example.yogeshgarg.source.mvp.profile;

import android.Manifest;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.reset_password.ResetPasswordActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements ProfileView {


    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @BindView(R.id.imgViewProfile)
    CircleImageView imgViewProfile;

    @BindView(R.id.txtViewTitleName)
    TextView txtViewTitleName;

    @BindView(R.id.edtTextName)
    EditText edtTextName;


    @BindView(R.id.txtViewTitleEmail)
    TextView txtViewTitleEmail;

    @BindView(R.id.edtTextEmail)
    EditText edtTextEmail;


    @BindView(R.id.txtViewTitlePhoneNumber)
    TextView txtViewTitlePhoneNumber;

    @BindView(R.id.edtTextPhoneNumber)
    EditText edtTextPhoneNumber;


    @BindView(R.id.txtViewTitlePassword)
    TextView txtViewTitlePassword;


    @BindView(R.id.txtViewPassword)
    TextView txtViewPassword;

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

    @BindView(R.id.btnSave)
    Button btnSave;

    ProfilePresenterImpl profilePresenterImpl = null;


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;


    @BindView(R.id.bottom_sheet)
    View bottom_sheet;

    @BindView(R.id.touch_outside)
    View touch_outside;


    private BottomSheetBehavior mBottomSheetBehavior;

    private int REQUEST_CAMERA = 0;
    private static int RESULT_LOAD_IMG = 1;

    String imageUrl;

    File photo = null;
    Bitmap photoBitmap = null;
    Uri mImageUri;

    boolean isVideoUploading = false;


    String profileImageName = "Image";

    boolean isUploadingImage = false;

    Bitmap uploadedImage;

    String name;
    String email;
    String phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        setFont();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                    touch_outside.setVisibility(View.GONE);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    touch_outside.setVisibility(View.VISIBLE);
                } else {
                    touch_outside.setVisibility(View.GONE);

                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });


        edtTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = edtTextPhoneNumber.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int length = editable.length();
                if ((prevL < length) && (length == 1 || length == 5 || length == 9)) {
                    String data = edtTextPhoneNumber.getText().toString();
                    edtTextPhoneNumber.setText(data + "-");
                    edtTextPhoneNumber.setSelection(length + 1);
                }


                if (editable.length() == 1 || editable.length() == 5 || editable.length() == 9) {
                    editable.append("-");
                }
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        profilePresenterImpl = new ProfilePresenterImpl(getActivity(), this);
        callingProfileApi();
    }


    //-------------------------------------------------------------------------------------------------
    @Override
    public void onSuccessProfile(ProfileModel.Result result) {
        String firstName = result.getFirstName();
        String lastName = result.getLastName();
        String emailId = result.getEmail();
        String phoneNumber = result.getPhone();
        String user = result.getUser();

        UserSession userSession = new UserSession(getActivity());
        String store = userSession.getStoreAddress();
        String company = result.getCompany();
        userSession.setUserImage((ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage()));

        edtTextName.setText(Utils.camelCasing(firstName) + " " + Utils.camelCasing(lastName));
        edtTextEmail.setText(emailId);
        edtTextPhoneNumber.setText(phoneNumber);
        txtViewUser.setText(Utils.camelCasing(user));
        txtViewStore.setText(Utils.camelCasing(store));
        txtViewCompany.setText(Utils.camelCasing(company));

        String image=(ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage());
        Picasso.with(getActivity()).load(image).into(imgViewProfile);

    }

    @Override
    public void onUnsuccessProfile(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryProfileInfo, coordinateLayout);
    }

    OnClickInterface onRetryProfileInfo = new OnClickInterface() {
        @Override
        public void onClick() {
            callingProfileApi();
        }
    };

    //-------------------------------------------------------------------------------------------------

    @Override
    public void onSuccessProfilePic(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onUnsuccessProfilePic(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternetErrorProfilePic() {
        SnackNotify.showMessage("Internet Error Please update again.", coordinateLayout);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public void onSuccessProfileInfo() {
        SnackNotify.showMessage("Profile Info Successfully Updated.", coordinateLayout);
    }

    @Override
    public void onUnsuccessProfileInfo(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternertErrorProfileInfo() {
        SnackNotify.checkConnection(onRetryUpdateProfileInfo, coordinateLayout);
    }

    OnClickInterface onRetryUpdateProfileInfo = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };

    //-------------------------------------------------------------------------------------------------
    private void setFont() {

        FontHelper.setFontFace(txtViewTitleName, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewTitleEmail, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewTitlePhoneNumber, FontHelper.FontType.FONT_Normal, getActivity());


        FontHelper.applyFont(getActivity(), edtTextName, FontHelper.FontType.FONT_Normal);

        FontHelper.applyFont(getActivity(), edtTextEmail, FontHelper.FontType.FONT_Normal);

        FontHelper.applyFont(getActivity(), edtTextPhoneNumber, FontHelper.FontType.FONT_Normal);


        FontHelper.setFontFace(txtViewTitlePassword, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewPassword, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewTitleUser, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewUser, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewTitleStore, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewStore, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewTitleCompany, FontHelper.FontType.FONT_Normal, getActivity());
        FontHelper.setFontFace(txtViewCompany, FontHelper.FontType.FONT_Normal, getActivity());

        FontHelper.setFontFace(btnSave, FontHelper.FontType.FONT_Normal, getActivity());
    }


    @OnClick(R.id.txtViewPassword)
    public void txtViewPasswordClick() {
        Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
        startActivity(intent);
    }


    private void callingProfileApi() {
        profilePresenterImpl.callingProfileApi();
    }


    @OnClick(R.id.frameLayout)
    public void openBottomSheet() {
        Utils.hideKeyboardIfOpen(getActivity());
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBottomSheetBehavior.setPeekHeight(0);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }


    @OnClick(R.id.touch_outside)
    public void touch_outside_click() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @OnClick(R.id.linLayCamera)
    public void selectCamera() {
        selectFromCamera();
    }


    public void selectFromCamera() {

        collapseBottomSheet();

        int permissionCheckCamera = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);

        int permissionCheckRead = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((permissionCheckCamera == -1) || (permissionCheckRead == -1) || (permissionCheckWrite == -1)) {

                if (!Settings.System.canWrite(getActivity())) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 2909);
                } else {

                    openCamera();
                }
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    private void collapseBottomSheet() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        Log.e("tem", "" + tempDir);
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        Log.e("temdir", "" + tempDir);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }


    @OnClick(R.id.linLayGallery)
    public void selectGallery() {

        selectFromGallery();
    }

    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            // place where to store camera taken picture
            photo = this.createTemporaryFile("picture", ".JPEG");
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getActivity(), "Please check SD card! Image shot is impossible!", Toast.LENGTH_LONG);
        }

        mImageUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getApplicationContext().getPackageName() + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        //start camera intent
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void selectFromGallery() {

        collapseBottomSheet();
        int permissionCheckRead = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((permissionCheckRead == -1) || (permissionCheckWrite == -1)) {

                if (!Settings.System.canWrite(getActivity())) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 2908);
                } else {
                    openGallery();
                }
            } else {
                openGallery();
            }
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2908: {

                int permissionCheckRead = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                int permissionCheckWrite = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);


                if ((permissionCheckRead == 0) && (permissionCheckWrite == 0)) {
                    openGallery();
                } else {
                    SnackNotify.showMessage("Please provide security permission from app setting.", coordinateLayout);
                }

                return;
            }

            case 2909: {

                int permissionCheckCamera = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA);

                int permissionCheckRead = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                int permissionCheckWrite = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);


                if ((permissionCheckCamera == 0) && (permissionCheckRead == 0) && (permissionCheckWrite == 0)) {
                    openCamera();
                } else {
                    SnackNotify.showMessage("Please provide security permission from app setting.", coordinateLayout);
                }

                return;
            }

        }
    }


    private void openGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapResult = null;
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK && null != data) {

                //------------------->>> 1st method to get image from gallery <<<-------------------------------

                Uri filePath = data.getData();
                String path = Utils.getRealPathFromURI(filePath.toString(), getActivity());


                File file = new File(path);
                float rotate = getOrientation(path);
                try {

                    FileInputStream fis = new FileInputStream(file);
                    // filePath = file.getAbsolutePath();
                    Bitmap photo = BitmapFactory.decodeStream(fis);
                    //Bitmap bitmap=Utils.resizeImageForGallery(photo,rotate);
                    Matrix matrix = new Matrix();
                    matrix.preRotate(rotate); // clockwise by 90 degrees
                    Bitmap bitmapRotate = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);

                    bitmapResult = Utils.resizeImageForImageView(bitmapRotate, rotate);


                    Log.e(path, "this is the output of path");

                    profilePresenterImpl.callingUploadProfilePicApi(file);

                    imgViewProfile.setImageBitmap(bitmapResult);

                } catch (OutOfMemoryError ex) {
                    ex.printStackTrace();
                }


            } else if (requestCode == REQUEST_CAMERA) {
                // taking photo using hardware camera
                onCaptureImageResult(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


    private float getOrientation(String path) {
        float rotate = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
            //Log.d("imageUri", photo.getPath());
            //Toast.makeText(getActivity(), mImageUri.getPath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        return rotate;

    }


    // taking photo method
    private void onCaptureImageResult(Intent data) {

        File file = null;
        String path = Utils.getRealPathFromURI(photo.getPath().toString(), getActivity());
        file = new File(path);


        float rotate = 0;
        Bitmap bitmapResult = null;
        ContentResolver cr = getActivity().getContentResolver();

        cr.notifyChange(mImageUri, null);

        rotate = getOrientation(photo.getPath());

        Bitmap bitmap = null;
        try {
            bitmap = Utils.rotateImage(android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri), rotate);
            bitmapResult = Utils.resizeImageForImageView(bitmap, rotate);
            imgViewProfile.setImageBitmap(bitmapResult);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
        }

        try {
            uploadedImage = Utils.resizeImageForImageView(bitmap, rotate);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }

        if (file != null) {
            profilePresenterImpl.callingUploadProfilePicApi(file);
        } else {
            Toast.makeText(getActivity(), "Please try again something went wrong", Toast.LENGTH_LONG).show();
        }


    }


    @OnClick(R.id.btnSave)
    public void btnSaveClick() {
        if (btnSave.getText().toString().equals(getString(R.string.edit))) {
            edtTextName.setEnabled(true);

            edtTextName.requestFocus();
            edtTextName.setSelection(edtTextName.getText().toString().length());

            edtTextPhoneNumber.setEnabled(true);
            edtTextEmail.setEnabled(true);
            btnSave.setText(getString(R.string.save));
        } else {

            getData();
        }

    }

    private void getData() {
        name = edtTextName.getText().toString();
        email = edtTextEmail.getText().toString();
        phoneNumber = edtTextPhoneNumber.getText().toString();
        callingProfileUpdateInfo();
    }

    private void callingProfileUpdateInfo() {
        String strName[] = name.split(" ");

        String firstName = "";
        String lastName = "";

        try {
            if (strName.length == 1) {
                firstName = strName[0];
            } else if (strName.length == 2) {
                firstName = strName[0];
                lastName = strName[1];
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        profilePresenterImpl.callingUpdateProfileInfo(firstName, lastName, email, phoneNumber);
    }


}



