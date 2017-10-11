package com.example.yogeshgarg.source.mvp.price_survey_brand;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductActivity;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 03/10/17.
 */

public class PriceSurveyBrandAdapter  extends RecyclerView.Adapter<PriceSurveyBrandAdapter.Holder>{

    Activity activity;
    ArrayList<PriceSurveyBrandModel.Result> resultArrayList;
    String strPriceUpdateBy;
    String strPriceUpdateBetween;

    public PriceSurveyBrandAdapter(Activity activity,ArrayList<PriceSurveyBrandModel.Result> resultArrayList,
                                   String strPriceUpdateBy,String strPriceUpdateBetween){
        this.activity=activity;
        this.resultArrayList=resultArrayList;
        this.strPriceUpdateBy=strPriceUpdateBy;
        this.strPriceUpdateBetween=strPriceUpdateBetween;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_category_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final PriceSurveyBrandModel.Result result=resultArrayList.get(position);

        String completed = String.valueOf(result.getCompleted());
        String productQuantity = result.getProducts();
        String brandName = result.getName();

        final String brandId = result.getBrandId();


        holder.txtViewCategoryName.setText(Utils.camelCasing(brandName));

        holder.progressBar.setProgress(Integer.parseInt(completed));

        holder.txtViewCompleted.setText(completed + "% Done");

        String products;


        if (Integer.parseInt(productQuantity) == 1 || Integer.parseInt(productQuantity) == 0) {
            products = productQuantity + " Product";
        } else {
            products = productQuantity + " Products";
        }

        holder.txtViewProductQuantity.setText(products);

        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink();
        URL myURL = null;

        try {
            myURL = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        Picasso.with(activity).load(String.valueOf(myURL)).transform(new CircleTransform()).into(holder.imgViewCategory);

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PriceSurveyProductActivity.class);
                intent.putExtra(ConstIntent.KEy_BRAND_ID, brandId);
                intent.putExtra(ConstIntent.KEY_INITIAL_DATE_TO_SEND, strPriceUpdateBy);
                intent.putExtra(ConstIntent.KEY_FINAL_DATE_TO_SEND, strPriceUpdateBetween);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.relLayImages)
        RelativeLayout relLayImages;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        @BindView(R.id.imgViewCategory)
        ImageView imgViewCategory;

        @BindView(R.id.txtViewCategoryName)
        TextView txtViewCategoryName;

        @BindView(R.id.txtViewProductQuantity)
        TextView txtViewProductQuantity;

        @BindView(R.id.txtViewCompleted)
        TextView txtViewCompleted;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewCategoryName, FontHelper.FontType.FONT_Semi_Bold, activity);
            FontHelper.setFontFace(txtViewProductQuantity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewCompleted, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
