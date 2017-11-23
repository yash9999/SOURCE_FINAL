package com.example.yogeshgarg.source.mvp.new_product.new_product_update;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.adapter.AdapterSpinner;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.example.yogeshgarg.source.mvp.team.MyTeamContractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class NewProductUpdateActivity extends AppCompatActivity implements NewProductUpdateView {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinatorLayout;


    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.txtViewTitle)
    TextView txtViewTitle;

    @BindView(R.id.imgViewSearch)
    ImageView imgViewSearch;

    @BindView(R.id.imgViewDots)
    ImageView imgViewDots;

    @BindView(R.id.imgViewToDisplay)
    ImageView imgViewToDisplay;

    @BindView(R.id.txtViewTitleFinalPrice)
    TextView txtViewTitleFinalPrice;

    @BindView(R.id.txtViewFinalPrice)
    TextView txtViewFinalPrice;

    @BindView(R.id.txtViewTitleProductName)
    TextView txtViewTitleProductName;

    @BindView(R.id.edtTextProductName)
    EditText edtTextProductName;

    @BindView(R.id.txtViewTitleBrandName)
    TextView txtViewTitleBrandName;


    @BindView(R.id.edtTextBrandName)
    EditText edtTextBrandName;

    @BindView(R.id.txtViewTitleUOM)
    TextView txtViewTitleUOM;


    @BindView(R.id.edtTextUOM)
    EditText edtTextUOM;

    @BindView(R.id.txtViewTitleUPC)
    TextView txtViewTitleUPC;

    @BindView(R.id.edtTextUPC)
    EditText edtTextUPC;


    @BindView(R.id.txtViewTitleAddedOn)
    TextView txtViewTitleAddedOn;

    @BindView(R.id.txtViewAddedOn)
    TextView txtViewAddedOn;

    @BindView(R.id.txtViewTitleStock)
    TextView txtViewTitleStock;


    @BindView(R.id.edtTextStock)
    EditText edtTextStock;

    @BindView(R.id.edtTextStockUnit)
    EditText edtTextStockUnit;

    @BindView(R.id.edtTextComment)
    EditText edtTextComment;

    @BindView(R.id.txtViewTitlePrice)
    TextView txtViewTitlePrice;


    @BindView(R.id.edtTextPrice)
    EditText edtTextPrice;

    @BindView(R.id.txtViewTitleDiscount)
    TextView txtViewTitleDiscount;

    @BindView(R.id.edtTextDiscount)
    EditText edtTextDiscount;

    @BindView(R.id.spinnerDiscount)
    AppCompatSpinner spinnerDiscount;

    @BindView(R.id.txtViewTitleTax)
    TextView txtViewTitleTax;

    @BindView(R.id.spinnerTax)
    AppCompatSpinner spinnerTax;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;


    @BindView(R.id.bottom_sheet)
    View bottom_sheet;

    @BindView(R.id.touch_outside)
    View touch_outside;


    NewProductUpdatePresenterImpl newProductUpdatePresenterImpl;

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

    private SimpleDateFormat dateFormatter;
    String dateFromCalender;
    Calendar calendar;
    DatePickerDialog fromDatePickerDialog = null;

    ArrayList<HashMap<String, String>> hashMapTaxArrayList;
    ArrayList<HashMap<String, String>> hashMapDiscountArrayList;

    String finalPrice;

    boolean flag = false;
    int discountType=-1;
    int taxType = 1;

    File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_update);
        ButterKnife.bind(this);

        newProductUpdatePresenterImpl = new NewProductUpdatePresenterImpl(this, this);

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


        edtTextUPC.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prevL = edtTextUPC.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int length = editable.length();
                if ((prevL < length) && (length == 4 || length == 9 || length == 14)) {
                    String data = edtTextUPC.getText().toString();
                    edtTextUPC.setText(data + "-");
                    edtTextUPC.setSelection(length + 1);
                }


                if (editable.length() == 4 || editable.length() == 9 || editable.length() == 14) {
                    editable.append("-");
                }
            }
        });

        edtTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String price = edtTextPrice.getText().toString();
                if (!Utils.isEmptyOrNull(price)) {
                    txtViewFinalPrice.setText(Utils.currencyFormat(price));
                } else {
                    txtViewFinalPrice.setText("");
                    txtViewFinalPrice.setHint("$---.--");
                }


            }
        });

        setFont();
        getCurrentDate();
        createAdapter();
        createAdapterDiscount();
    }

    private void setFont() {

        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);
        txtViewTitle.setText("New Product Add");
        imgViewSearch.setVisibility(View.GONE);


        FontHelper.setFontFace(txtViewTitleDiscount, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewTitleFinalPrice, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, txtViewFinalPrice, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitleProductName, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextProductName, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitleBrandName, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextBrandName, FontHelper.FontType.FONT_Normal);


        FontHelper.setFontFace(txtViewTitleUOM, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextUOM, FontHelper.FontType.FONT_Normal);


        FontHelper.setFontFace(txtViewTitleUPC, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextUPC, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitleAddedOn, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, txtViewAddedOn, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitleStock, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextStock, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextStockUnit, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextComment, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitlePrice, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextPrice, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(edtTextDiscount, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextPrice, FontHelper.FontType.FONT_Normal);
        FontHelper.setFontFace(txtViewTitleTax, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(btnSubmit, FontHelper.FontType.FONT_Normal, this);

    }

    @OnClick(R.id.imgViewToDisplay)
    public void openBottomSheet() {
        Utils.hideKeyboardIfOpen(this);
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

        int permissionCheckCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        int permissionCheckRead = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((permissionCheckCamera == -1) || (permissionCheckRead == -1) || (permissionCheckWrite == -1)) {

                if (!Settings.System.canWrite(this)) {
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
            Toast.makeText(this, "Please check SD card! Image shot is impossible!", Toast.LENGTH_LONG);
        }

        mImageUri = FileProvider.getUriForFile(this.getApplicationContext(), this.getApplicationContext().getPackageName() + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        //start camera intent
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void selectFromGallery() {

        collapseBottomSheet();
        int permissionCheckRead = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((permissionCheckRead == -1) || (permissionCheckWrite == -1)) {

                if (!Settings.System.canWrite(this)) {
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

                int permissionCheckRead = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);


                if ((permissionCheckRead == 0) && (permissionCheckWrite == 0)) {
                    openGallery();
                } else {
                    SnackNotify.showMessage("Please provide security permission from app setting.", coordinatorLayout);
                }

                return;
            }

            case 2909: {

                int permissionCheckCamera = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA);

                int permissionCheckRead = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);


                if ((permissionCheckCamera == 0) && (permissionCheckRead == 0) && (permissionCheckWrite == 0)) {
                    openCamera();
                } else {
                    SnackNotify.showMessage("Please provide security permission from app setting.", coordinatorLayout);
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
            if (requestCode == RESULT_LOAD_IMG && resultCode == this.RESULT_OK && null != data) {

                //------------------->>> 1st method to get image from gallery <<<-------------------------------

                Uri filePath = data.getData();
                String path = Utils.getRealPathFromURI(filePath.toString(), this);


                file = new File(path);
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


                    imgViewToDisplay.setImageBitmap(bitmapResult);

                } catch (OutOfMemoryError ex) {
                    ex.printStackTrace();
                }


            } else if (requestCode == REQUEST_CAMERA) {
                // taking photo using hardware camera
                onCaptureImageResult(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
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


        String path = Utils.getRealPathFromURI(photo.getPath().toString(), this);
        file = new File(path);

        float rotate = 0;
        Bitmap bitmapResult = null;
        ContentResolver cr = this.getContentResolver();

        cr.notifyChange(mImageUri, null);

        rotate = getOrientation(photo.getPath());

        Bitmap bitmap = null;
        try {
            bitmap = Utils.rotateImage(android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri), rotate);
            bitmapResult = Utils.resizeImageForImageView(bitmap, rotate);
            imgViewToDisplay.setImageBitmap(bitmapResult);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
        }

        try {
            uploadedImage = Utils.resizeImageForImageView(bitmap, rotate);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void onSuccessPictureApi(String message) {

        SnackNotify.showMessage(message, coordinatorLayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(NewProductUpdateActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        }, 500);
    }

    @Override
    public void onUnsuccessPictureApi(String message) {
        SnackNotify.showMessage(message, coordinatorLayout);
    }

    @Override
    public void onInternetErrorPicture() {
        SnackNotify.checkConnection(onRetryUpdateApi, coordinatorLayout);
    }

    OnClickInterface onRetryUpdateApi = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };

    @OnClick(R.id.btnSubmit)
    public void btnSubmit() {
        Log.e("Button Pressed","Button pressed");
        getData();

    }

    private void getData() {

        String productName = edtTextProductName.getText().toString();
        String brandName = edtTextBrandName.getText().toString();
        String uom = edtTextUOM.getText().toString();
        String upc = edtTextUPC.getText().toString();
        String discount = edtTextDiscount.getText().toString();

        if (Utils.isEmptyOrNull(discount)) {
            discount = "0";
        }

        String stockMeasure = edtTextStock.getText().toString();
        if (Utils.isEmptyOrNull(stockMeasure)) {
            stockMeasure = "0";
        }

        String stockUnit = edtTextStockUnit.getText().toString();
        if (Utils.isEmptyOrNull(stockUnit)) {
            stockUnit = "";
        }

        String comment = edtTextComment.getText().toString();
        String price = edtTextPrice.getText().toString();

        int pos = spinnerDiscount.getSelectedItemPosition();


        /*if (pos == 0) {
            SnackNotify.showMessage("Please select discount type as discount percenatge or dollar.", coordinatorLayout);
            return;
        } else {*/
        if (pos == 2) {
            discountType = 0;
        } else if (pos == 1) {
            discountType = 1;
        }
        //}


        //0 for no,1 for yes

        int taxPos = spinnerTax.getSelectedItemPosition();

        if (taxPos == 0) {
            taxType = 0;//means no
        } else if (taxPos == 1) {
            taxType = 1;//means yes
        }

        if (discountType == 1) {
            if (Double.parseDouble(price) < Double.parseDouble(discount)) {
                onUnsuccessPictureApi("Price value can not be less than discount.");
            }else{
                newProductUpdatePresenterImpl.callingPictureApi(file, productName, brandName, uom, upc, stockMeasure, stockUnit, comment, price, String.valueOf(discountType), discount, String.valueOf(taxType));
            }
        } else {
            newProductUpdatePresenterImpl.callingPictureApi(file, productName, brandName, uom, upc, stockMeasure, stockUnit, comment, price, String.valueOf(discountType), discount, String.valueOf(taxType));
        }

    }


    @OnClick(R.id.txtViewAddedOn)
    public void txtViewAddedOnClick() {
        getCurrentDate();
        initializeFromDate();
        fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        fromDatePickerDialog.show();
    }


    private void getCurrentDate() {
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        dateFromCalender = (dateFormatter.format(calendar.getTime()));
        txtViewAddedOn.setText(dateFromCalender);
    }

    private void initializeFromDate() {
        calendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                //setting date;
                dateFromCalender = (dateFormatter.format(calendar.getTime()));
                txtViewAddedOn.setText(dateFromCalender);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


    private void createAdapter() {
        hashMapTaxArrayList = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> firstIndex = new HashMap<String, String>();
        firstIndex.put(Const.KEY_ID, "0");
        firstIndex.put(Const.KEY_NAME, getString(R.string.no));

        HashMap<String, String> secondIndex = new HashMap<String, String>();
        secondIndex.put(Const.KEY_ID, "1");
        secondIndex.put(Const.KEY_NAME, getString(R.string.yes));

        hashMapTaxArrayList.add(firstIndex);
        hashMapTaxArrayList.add(secondIndex);


        setTaxSpinnerAdapter();
    }

    private void createAdapterDiscount() {

        hashMapDiscountArrayList = new ArrayList<>();

        HashMap<String, String> discountFirst = new HashMap<String, String>();
        discountFirst.put(Const.KEY_ID, "0");
        discountFirst.put(Const.KEY_NAME, getString(R.string.type));

        HashMap<String, String> discountSecond = new HashMap<>();
        discountSecond.put(Const.KEY_ID, "1");
        discountSecond.put(Const.KEY_NAME, "$ OFF");


        HashMap<String, String> discountThird = new HashMap<>();
        discountThird.put(Const.KEY_ID, "2");
        discountThird.put(Const.KEY_NAME, "% OFF");

        hashMapDiscountArrayList.add(discountFirst);
        hashMapDiscountArrayList.add(discountSecond);
        hashMapDiscountArrayList.add(discountThird);

        setDiscountSpinnerAdapter();
    }

    private void setTaxSpinnerAdapter() {
        AdapterSpinner taxAdapterSpinner = new AdapterSpinner(this, R.layout.layout_spinner_dropdown, hashMapTaxArrayList);
        spinnerTax.setAdapter(taxAdapterSpinner);
    }

    private void setDiscountSpinnerAdapter() {
        AdapterSpinner discountAdapterSpinner = new AdapterSpinner(this, R.layout.layout_spinner_dropdown, hashMapDiscountArrayList);
        spinnerDiscount.setAdapter(discountAdapterSpinner);

    }


    @OnItemSelected(R.id.spinnerDiscount)
    public void spinnerDiscountClick(Spinner spinner, int position) {

        Utils.hideKeyboardIfOpen(this);

        String mrp = edtTextPrice.getText().toString();
        String discount = edtTextDiscount.getText().toString();

        double price;


        if (position == 1 || position == 2) {
            if (Utils.isEmptyOrNull(mrp)) {
                SnackNotify.showMessage("Price field is empty", coordinatorLayout);
            } else {
                if (Utils.isEmptyOrNull(discount)) {
                    SnackNotify.showMessage("Discount field is empty.", coordinatorLayout);
                } else {
                    double doubleMrp = Double.parseDouble(mrp);
                    double doubleDiscount = Double.parseDouble(discount);

                    if (doubleMrp <= 0) {
                        SnackNotify.showMessage("Please must be greater than  0.00.", coordinatorLayout);
                        return;
                    } else if (doubleDiscount > doubleMrp) {
                        SnackNotify.showMessage("Discount can not greater than Price.", coordinatorLayout);
                        return;
                    }


                    if (spinnerDiscount.getSelectedItemPosition() == 1) {
                        price = doubleMrp - doubleDiscount;
                        finalPrice = String.valueOf(price);
                        txtViewFinalPrice.setText(Utils.currencyFormat(finalPrice));
                    } else if (spinnerDiscount.getSelectedItemPosition() == 2) {
                        if (doubleDiscount > 100) {
                            SnackNotify.showMessage("Discount Percentage can not be greater than 100.", coordinatorLayout);
                        } else {
                            price = doubleMrp - ((doubleMrp * doubleDiscount) / 100);
                            finalPrice = (String.valueOf(price));
                            txtViewFinalPrice.setText(Utils.currencyFormat(finalPrice));
                        }

                    }

                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBackCLick() {
        onBackPressed();
    }
}



