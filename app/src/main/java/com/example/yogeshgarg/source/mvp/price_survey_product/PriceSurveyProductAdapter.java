package com.example.yogeshgarg.source.mvp.price_survey_product;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.ProductUpdate.ProductUpdateActivity;
import com.example.yogeshgarg.source.mvp.ProductUpdate.ProductUpdateModel;
import com.example.yogeshgarg.source.mvp.price_survey.PriceSurveyAdapter;
import com.example.yogeshgarg.source.mvp.price_survey_brand.PriceSurveyBrandModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 21/07/17.
 */

public class PriceSurveyProductAdapter extends RecyclerView.Adapter<PriceSurveyProductAdapter.Holder>
        implements Filterable {

    Activity activity;
    ArrayList<PriceSurveyProductModel.Result> originalArrayList;
    ArrayList<PriceSurveyProductModel.Result> filterResultArrayList;

    PriceSurveyProductAdapter(Activity activity, ArrayList<PriceSurveyProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.originalArrayList = resultArrayList;
        filterResultArrayList=originalArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_newproduct_home, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final PriceSurveyProductModel.Result result = filterResultArrayList.get(position);

        String productName = result.getProductName();
        String brandName = result.getBrandName();
        String price = result.getCost();

        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage();
        Picasso.with(activity).load(link).transform(new CircleTransform()).into(holder.imgViewProduct);

        //inserting data into fields

        holder.txtViewProductName.setText(Utils.camelCasing(productName));
        holder.txtViewBrandName.setText(Utils.camelCasing(brandName));
        holder.txtViewPrice.setText(Utils.currencyFormat(price));

        String strLastUpdated = result.getLastupdated();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String daysToShow = null;
        try {
            String strCurrentDate = simpleDateFormat.format(currentDate);
            Date lastUpdatedDay = simpleDateFormat.parse(strLastUpdated);
            Date currentUpdateDay = simpleDateFormat.parse(strCurrentDate);
            long diff = currentUpdateDay.getTime() - lastUpdatedDay.getTime();
            daysToShow = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((Integer.parseInt(daysToShow) == 1) || (Integer.parseInt(daysToShow) == 0)) {
            holder.txtViewDaysAgo.setText(daysToShow + " Day ago");
        } else {
            holder.txtViewDaysAgo.setText(daysToShow + " Days ago");
        }

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductUpdateActivity.class);
                intent.putExtra(ConstIntent.KEY_PRODUCT_UPDATE_INSTANCE, filterResultArrayList.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterResultArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strCharSequence = charSequence.toString();
                if (strCharSequence.isEmpty()) {
                    filterResultArrayList = originalArrayList;
                } else {
                    ArrayList<PriceSurveyProductModel.Result> filteringInnerArrayList = new ArrayList<>();

                    for (PriceSurveyProductModel.Result result : originalArrayList) {
                        if (result.getProductName().toLowerCase().contains(strCharSequence)) {
                            filteringInnerArrayList.add(result);
                        }
                    }

                    filterResultArrayList = filteringInnerArrayList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterResultArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterResultArrayList = (ArrayList<PriceSurveyProductModel.Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.relLayImages)
        RelativeLayout relLayImages;

        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewProductName)
        TextView txtViewProductName;

        @BindView(R.id.txtViewBrandName)
        TextView txtViewBrandName;

        @BindView(R.id.txtViewDaysAgo)
        TextView txtViewDaysAgo;

        @BindView(R.id.txtViewPrice)
        TextView txtViewPrice;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewBrandName, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewPrice, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewDaysAgo, FontHelper.FontType.FONT_Normal, activity);

        }
    }
}
