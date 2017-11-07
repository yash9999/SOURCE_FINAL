package com.example.yogeshgarg.source.mvp.dashboard_activities.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardExpiryProductUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 02/08/17.
 */

public class DashboardActivityExpiryProductAdapter extends RecyclerView.Adapter<DashboardActivityExpiryProductAdapter.Holder> {


    Activity activity;
    ArrayList<DashboardExpiryProductModel.Result> resultArrayList;

    public DashboardActivityExpiryProductAdapter(Activity activity, ArrayList<DashboardExpiryProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_db_activity_expiry, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        DashboardExpiryProductModel.Result result = resultArrayList.get(position);

       Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage()).error(R.mipmap.ic_browser).into(holder.imgViewProduct);


        String productName = Utils.camelCasing(result.getProductName());
        holder.txtViewProductName.setText(productName);
        String categoryName = Utils.camelCasing(result.getCategoryName());
        holder.txtViewProductCategoryName.setText(categoryName);
        holder.txtViewStock.setText(result.getStock() + " " + result.getStockUnitMeasure());
        holder.txtViewProductQuantity.setText("UOM: " + result.getWeight() + result.getItemUnitMeasure());
        holder.txtViewStoreNameAndCity.setText(Utils.camelCasing(result.getStoreName() + "-" + Utils.camelCasing(result.getCity())));

        String dateadded = result.getDateadded();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String daysToShow = null;
        try {
            String strCurrentDate = simpleDateFormat.format(currentDate);
            Date onDateAdded = simpleDateFormat.parse(dateadded);
            Date currentUpdateDay = simpleDateFormat.parse(strCurrentDate);
            long diff = currentUpdateDay.getTime() - onDateAdded.getTime();
            daysToShow = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((Integer.parseInt(daysToShow) == 1) || (Integer.parseInt(daysToShow) == 0)) {
            holder.txtViewProductDate.setText(daysToShow + " Day ago");
        } else {
            holder.txtViewProductDate.setText(daysToShow + " Days ago");
        }

    }



    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {


        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewProductName)
        TextView txtViewProductName;

        @BindView(R.id.txtViewProductCategoryName)
        TextView txtViewProductCategoryName;

        @BindView(R.id.txtViewStock)
        TextView txtViewStock;

        @BindView(R.id.txtViewProductQuantity)
        TextView txtViewProductQuantity;

        @BindView(R.id.txtViewStoreNameAndCity)
        TextView txtViewStoreNameAndCity;

        @BindView(R.id.txtViewProductDate)
        TextView txtViewProductDate;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductCategoryName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewStock, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductQuantity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewStoreNameAndCity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductDate, FontHelper.FontType.FONT_Normal, activity);

        }
    }
}


