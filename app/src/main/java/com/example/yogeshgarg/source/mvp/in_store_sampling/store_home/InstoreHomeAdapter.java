package com.example.yogeshgarg.source.mvp.in_store_sampling.store_home;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home.ExpiryProductHomeScreenAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class InstoreHomeAdapter extends RecyclerView.Adapter<InstoreHomeAdapter.Holder> {

    Activity activity;
    ArrayList<InStoreHomeModel.Result> resultArrayList = null;

    public InstoreHomeAdapter(Activity activity, ArrayList<InStoreHomeModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_price_survey_product, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        InStoreHomeModel.Result result = resultArrayList.get(position);
        String productName = result.getProductName();
        String brandName = result.getBrandName();
        String price = result.getCost();

        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage();
        Picasso.with(activity).load(link).into(holder.imgViewProduct);

        //inserting data into fields

        holder.txtViewProductName.setText(Utils.camelCasing(productName));
        holder.txtViewBrandName.setText(Utils.camelCasing(brandName));
        holder.txtViewPrice.setText(Utils.currencyFormat(price));

        String strLastUpdated = result.getDateadded();

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

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
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

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Semi_Bold, activity);
            FontHelper.setFontFace(txtViewBrandName, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewPrice, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewDaysAgo, FontHelper.FontType.FONT_Normal, activity);

        }
    }
}
