package com.example.yogeshgarg.source.mvp.ProductUpdate;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class ProductUpdateActivity extends AppCompatActivity implements ProductUpdateView {


    @BindView(R.id.touchOutSide)
    View touchOutSide;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

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

    @BindView(R.id.txtViewProductName)
    TextView txtViewProductName;


    @BindView(R.id.txtViewTitleBrandName)
    TextView txtViewTitleBrandName;

    @BindView(R.id.txtViewBrandName)
    TextView txtViewBrandName;

    @BindView(R.id.txtViewTitleUOM)
    TextView txtViewTitleUOM;

    @BindView(R.id.txtViewUOM)
    TextView txtViewUOM;


    @BindView(R.id.txtViewTitleUPC)
    TextView txtViewTitleUPC;

    @BindView(R.id.txtViewUPC)
    TextView txtViewUPC;


    @BindView(R.id.txtViewTitleLastUpdate)
    TextView txtViewTitleLastUpdate;

    @BindView(R.id.txtViewLastUpdate)
    TextView txtViewLastUpdate;

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


    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.relLayLastPrice)
    RelativeLayout relLayLastPrice;

    @BindView(R.id.txtViewLastPrice)
    TextView txtViewLastPrice;

    @BindView(R.id.imgViewLastPrice)
    ImageView imgViewLastPrice;

    @BindView(R.id.relLayShowStockLevel)
    RelativeLayout relLayShowStockLevel;

    @BindView(R.id.txtViewShowStockLevel)
    TextView txtViewShowStockLevel;

    @BindView(R.id.imgViewShowStockLevel)
    ImageView imgViewShowStockLevel;

    @BindView(R.id.relLayNotInStore)
    RelativeLayout relLayNotInStore;

    @BindView(R.id.txtViewNotInStore)
    TextView txtViewNotInStore;

    @BindView(R.id.imgViewNotInStore)
    ImageView imgViewNotInStore;

    @BindView(R.id.relLayReset)
    RelativeLayout relLayReset;

    @BindView(R.id.txtViewReset)
    TextView txtViewReset;

    @BindView(R.id.imgViewReset)
    ImageView imgViewReset;

    int discountType = 0;
    int taxType = 0;
    int inStore = 0;
    //0 means not in store 1 means instore
    // 0 for percenatge and 1 for cost

    ArrayList<HashMap<String, String>> hashMapTaxArrayList;
    ArrayList<HashMap<String, String>> hashMapDiscountArrayList;

    String finalPrice;

    int SPLASH_TIME_OUT = 400;


    PriceSurveyProductModel.Result priceSurveyProductModelResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);

        ButterKnife.bind(this);

        setFont();
        imgViewDots.setVisibility(View.VISIBLE);
        imgViewSearch.setVisibility(View.GONE);
        txtViewTitle.setText("Product Update");
        Intent intent = getIntent();

        createAdapter();
        createAdapterDiscount();


        if (intent != null) {
            priceSurveyProductModelResult = (PriceSurveyProductModel.Result) intent.getSerializableExtra(ConstIntent.KEY_PRODUCT_UPDATE_INSTANCE);
            setData();
        }

        touchOutSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
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


    }

    private void setFont() {
        imgViewDots.setVisibility(View.VISIBLE);
        FontHelper.setFontFace(txtViewTitle, FontHelper.FontType.FONT_Semi_Bold, this);

        FontHelper.setFontFace(txtViewTitleProductName, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewTitleBrandName, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewBrandName, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleUOM, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewUOM, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewTitleUPC, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewUPC, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleLastUpdate, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewLastUpdate, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewTitleFinalPrice, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewFinalPrice, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewTitleStock, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextStock, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtTextStockUnit, FontHelper.FontType.FONT_Normal);


        FontHelper.applyFont(this, edtTextComment, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitlePrice, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextPrice, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitleDiscount, FontHelper.FontType.FONT_Normal, this);
        FontHelper.applyFont(this, edtTextDiscount, FontHelper.FontType.FONT_Normal);

        FontHelper.setFontFace(txtViewTitleTax, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(btnSubmit, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewLastPrice, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewShowStockLevel, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewNotInStore, FontHelper.FontType.FONT_Normal, this);
        FontHelper.setFontFace(txtViewReset, FontHelper.FontType.FONT_Normal, this);

    }

    private void setData() {
        String productName = priceSurveyProductModelResult.getProductName().toString();
        String brandName = priceSurveyProductModelResult.getBrandName().toString();
        String weight = priceSurveyProductModelResult.getWeight();
        String itemUnitMeasure = priceSurveyProductModelResult.getItemUnitMeasure();
        String uom = weight + itemUnitMeasure;

        String upcNumber = priceSurveyProductModelResult.getUpc().toString();
        String lastUpdate = priceSurveyProductModelResult.getLastupdated();

        int stock = priceSurveyProductModelResult.getStock();


        String stockUnit = priceSurveyProductModelResult.getStockUnitMeasure();
        String price = priceSurveyProductModelResult.getCost();
        String discount = priceSurveyProductModelResult.getDiscount();


        String link = ConstIntent.PREFIX_URL_OF_IMAGE + priceSurveyProductModelResult.getImage();
        Picasso.with(this).load(link).into(imgViewToDisplay);

        txtViewProductName.setText(Utils.camelCasing(productName));
        txtViewBrandName.setText(Utils.camelCasing(brandName));
        txtViewUOM.setText(uom);
        txtViewUPC.setText(upcNumber);

        edtTextStock.setText(String.valueOf(stock));

        /*if (stock==0) {
            edtTextStock.setText("");
            edtTextStock.setHint("-------");
        } else {

        }*/

        if (Utils.isEmptyOrNull(stockUnit)) {
            edtTextStockUnit.setText("");
            edtTextStockUnit.setHint("------");
        } else {
            edtTextStockUnit.setText(stockUnit);
        }

        if (price.equals("0.00")) {
            edtTextPrice.setText("");
            edtTextPrice.setHint("0.00");
        } else {
            edtTextPrice.setText(price);
        }

        /*
        if(discount.equals("0.00")){
            edtTextDiscount.setText("");
            edtTextDiscount.setHint("0.00");
        }else{

        }*/

        edtTextDiscount.setText(discount);
        discountType = Integer.parseInt(priceSurveyProductModelResult.getType());
        // 0 for percenatge and 1 for cost
        //if cost is passed tax must be passed,0 for no 1 for yes
        taxType = Integer.parseInt(priceSurveyProductModelResult.getTax());
        if (taxType == 0) {
            spinnerTax.setSelection(1);
        } else if (taxType == 1) {
            spinnerTax.setSelection(0);
        }

        double doublePrice = Double.parseDouble(price);
        double doubleDiscount = Double.parseDouble(discount);

        if (discountType == 0) {
            finalPrice = String.valueOf(doublePrice - ((doublePrice * doubleDiscount) / 100));
            spinnerDiscount.setSelection(1);
        } else if (discountType == 1) {
            finalPrice = String.valueOf(doublePrice - doubleDiscount);
            spinnerDiscount.setSelection(0);
        }
        txtViewFinalPrice.setText(Utils.currencyFormat(finalPrice));

        inStore = Integer.parseInt(priceSurveyProductModelResult.getInstore());
        if (inStore == 0) {
            imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_dot_green));
        } else if (inStore == 1) {
            imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        }
        String lastUpdateToShow = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateInitial = null;
            try {
                dateInitial = simpleDateFormat.parse(lastUpdate);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            SimpleDateFormat newFormatDate = new SimpleDateFormat("MMM dd, yyyy");
            lastUpdateToShow = newFormatDate.format(dateInitial);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        txtViewLastUpdate.setText(lastUpdateToShow);

    }

    @OnClick(R.id.imgViewBack)
    public void imgViewBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void createAdapter() {
        hashMapTaxArrayList = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> firstIndex = new HashMap<String, String>();
        firstIndex.put(Const.KEY_ID, "0");
        firstIndex.put(Const.KEY_NAME, getString(R.string.yes));

        HashMap<String, String> secondIndex = new HashMap<String, String>();
        secondIndex.put(Const.KEY_ID, "1");
        secondIndex.put(Const.KEY_NAME, getString(R.string.no));

        hashMapTaxArrayList.add(firstIndex);
        hashMapTaxArrayList.add(secondIndex);


        setTaxSpinnerAdapter();
    }

    private void createAdapterDiscount() {

        hashMapDiscountArrayList = new ArrayList<>();


        HashMap<String, String> discountSecond = new HashMap<>();
        discountSecond.put(Const.KEY_ID, "0");
        discountSecond.put(Const.KEY_NAME, "$ OFF");


        HashMap<String, String> discountThird = new HashMap<>();
        discountThird.put(Const.KEY_ID, "1");
        discountThird.put(Const.KEY_NAME, "% OFF");

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

    @OnClick(R.id.imgViewDots)
    public void imgViewDotsClick() {
        cardView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.relLayLastPrice)
    public void relLayLastPriceClick() {
        inStore = 1;
        imgViewLastPrice.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_dot_green));
        edtTextPrice.setText(priceSurveyProductModelResult.getCost());
        txtViewFinalPrice.setText("");

        imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewReset.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewShowStockLevel.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.INVISIBLE);

            }
        }, SPLASH_TIME_OUT);
    }

    @OnClick(R.id.relLayShowStockLevel)
    public void relLayShowStockLevelClick() {
        inStore = 1;
        imgViewShowStockLevel.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_dot_green));

        imgViewLastPrice.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewReset.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.INVISIBLE);

            }
        }, SPLASH_TIME_OUT);
    }

    @OnClick(R.id.relLayNotInStore)
    public void relLayNotInStoreClick() {

        if (inStore == 0) {
            inStore = 1;
            imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        } else {
            inStore = 0;
            imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_dot_green));
        }


        imgViewLastPrice.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewReset.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewShowStockLevel.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.INVISIBLE);

            }
        }, SPLASH_TIME_OUT);
    }

    @OnClick(R.id.relLayReset)
    public void relLayResetClick() {
        inStore = 1;
        imgViewReset.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_dot_green));
        setData();

        imgViewLastPrice.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewNotInStore.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));
        imgViewShowStockLevel.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_circle_clear_green));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.INVISIBLE);

            }
        }, SPLASH_TIME_OUT);
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        cardView.setVisibility(View.GONE);
        getData();
    }

    private void getData() {

        String stock = edtTextStock.getText().toString();
        if (Utils.isEmptyOrNull(stock)) {
            stock = "0";
        }

        String stockUnit = edtTextStockUnit.getText().toString();
        if (Utils.isEmptyOrNull(stockUnit)) {
            stockUnit = "";
        }

        String price = edtTextPrice.getText().toString();
        if (Utils.isEmptyOrNull(price)) {
            price = "0";
        }

        String discount = edtTextDiscount.getText().toString();
        if (Utils.isEmptyOrNull(discount)) {
            discount = "0";
        }

        String comment = edtTextComment.getText().toString();
        if (Utils.isEmptyOrNull(comment)) {
            comment = "";
        }

        String productID = priceSurveyProductModelResult.getProductId();
        int pos = spinnerDiscount.getSelectedItemPosition();

        if (pos == 1) {
            discountType = 0;// means percentage
        } else if (pos == 0) {
            discountType = 1;//means dollar
        }


        //0 for no,1 for yes taxtype

        int taxPos = spinnerTax.getSelectedItemPosition();

        if (taxPos == 0) {
            taxType = 1;//yes
        } else if (taxPos == 1) {
            taxType = 0;//no
        }


        if (discountType == 1) {
            if (Double.parseDouble(price) < Double.parseDouble(discount)) {
                onUnsuccessProductUpdate("Price value can not be less than discount.");
            }
        } else {
            ProductUpdatePresenterImpl productUpdatePresenterImpl = new ProductUpdatePresenterImpl(this, this);
            productUpdatePresenterImpl.callingProductUpdateApi(productID, stock, stockUnit, price, discount, String.valueOf(discountType),
                    String.valueOf(taxType), comment, String.valueOf(inStore));
        }

    }

    @OnItemSelected(R.id.spinnerDiscount)
    public void spinnerDiscountClick(Spinner spinner, int position) {

        Utils.hideKeyboardIfOpen(this);

        String mrp = edtTextPrice.getText().toString();
        String discount = edtTextDiscount.getText().toString();

        double price;


        if (position == 0 || position == 1) {
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


                    if (spinnerDiscount.getSelectedItemPosition() == 0) {
                        price = doubleMrp - doubleDiscount;
                        finalPrice = String.valueOf(price);
                        txtViewFinalPrice.setText(Utils.currencyFormat(finalPrice));
                    } else if (spinnerDiscount.getSelectedItemPosition() == 1) {
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
    public void onSuccessProductUpdate() {

        SnackNotify.showMessage("Product info updated successfully.", coordinatorLayout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ProductUpdateActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onUnsuccessProductUpdate(String message) {
        SnackNotify.showMessage(message, coordinatorLayout);
    }

    @Override
    public void onInternetErro() {
        SnackNotify.checkConnection(onRetryProductUpdate, coordinatorLayout);
    }

    OnClickInterface onRetryProductUpdate = new OnClickInterface() {
        @Override
        public void onClick() {
            getData();
        }
    };
}
