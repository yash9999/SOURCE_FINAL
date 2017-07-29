package com.example.yogeshgarg.source.mvp.ProductUpdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductUpdateActivity extends AppCompatActivity {


    @BindView(R.id.txtViewProductUpdate)
    TextView txtViewProductUpdate;

    @BindView(R.id.imgViewToDisplay)
    ImageView imgViewToDisplay;

    @BindView(R.id.txtViewTitleProductName)
    TextView txtViewTitleProductName;

    @BindView(R.id.txtViewProductName)
    TextView txtViewProductName;

    @BindView(R.id.txtViewTitleUPC)
    TextView txtViewTitleUPC;

    @BindView(R.id.txtViewUPC)
    TextView txtViewUPC;

    @BindView(R.id.txtViewTitleFinalPrice)
    TextView txtViewTitleFinalPrice;

    @BindView(R.id.txtViewFinalPrice)
    TextView txtViewFinalPrice;

    @BindView(R.id.txtViewTitleLastUpdate)
    TextView txtViewTitleLastUpdate;

    @BindView(R.id.txtViewLastUpdate)
    TextView txtViewLastUpdate;

    @BindView(R.id.txtViewTitleStock)
    TextView txtViewTitleStock;

    @BindView(R.id.txtViewStock)
    TextView txtViewStock;

    @BindView(R.id.txtViewStockUnit)
    TextView txtViewStockUnit;

    @BindView(R.id.edtTextComment)
    EditText edtTextComment;

    @BindView(R.id.txtViewTitlePrice)
    TextView txtViewTitlePrice;


    @BindView(R.id.txtViewPrice)
    TextView txtViewPrice;

    @BindView(R.id.txtViewTitleDiscount)
    TextView txtViewTitleDiscount;

    @BindView(R.id.spinnerDiscount)
    AppCompatSpinner spinnerDiscount;

    @BindView(R.id.txtViewTitleTax)
    TextView txtViewTitleTax;


    @BindView(R.id.spinnerTax)
    AppCompatSpinner spinnerTax;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    PriceSurveyProductModel.Result priceSurveyProductModelResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);
        ButterKnife.bind(this);
        setFont();
        Intent intent = getIntent();
        if (intent != null) {
            priceSurveyProductModelResult = (PriceSurveyProductModel.Result) intent.getSerializableExtra(ConstIntent.KEY_PRODUCT_UPDATE_INSTANCE);
            setData();
        }

    }

    private void setFont() {

        FontHelper.setFontFace(txtViewProductUpdate, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleProductName, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, this);


        FontHelper.setFontFace(txtViewTitleUPC, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewUPC, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleFinalPrice, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewFinalPrice, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleLastUpdate, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewLastUpdate, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleStock, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewStock, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewStockUnit, FontHelper.FontType.FONT_Normal, this);

        FontHelper.applyFont(this, edtTextComment, FontHelper.FontType.FONT_Normal);
        FontHelper.setFontFace(txtViewTitlePrice, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewPrice, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleDiscount, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(txtViewTitleTax, FontHelper.FontType.FONT_Normal, this);

        FontHelper.setFontFace(btnSubmit, FontHelper.FontType.FONT_Normal, this);


    }

    private void setData() {
        String productName = priceSurveyProductModelResult.getProductName().toString();
        String upcNumber = priceSurveyProductModelResult.getUpc().toString();
        String lastUpdate = priceSurveyProductModelResult.getLastupdated();
        String stock = priceSurveyProductModelResult.getStock();
        String stockUnit = priceSurveyProductModelResult.getStockUnitMeasure();
        String price = priceSurveyProductModelResult.getCost();
        String link = ConstIntent.PREFIX_URL_OF_IMAGE + priceSurveyProductModelResult.getImage();
        Picasso.with(this).load(link).into(imgViewToDisplay);

        txtViewProductName.setText(Utils.camelCasing(productName));
        txtViewUPC.setText(upcNumber);

        txtViewStock.setText(stock);
        txtViewStockUnit.setText(stockUnit);
        txtViewPrice.setText(Utils.currencyFormat(price));

        FontHelper.setFontFace(txtViewFinalPrice, FontHelper.FontType.FONT_Normal, this);


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
}
