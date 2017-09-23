package com.example.yogeshgarg.source.mvp.dashboard.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 30/07/17.
 */

public class DashboardExpiryProductUpdateAdapter extends RecyclerView.Adapter<DashboardExpiryProductUpdateAdapter.Holder> {

    Activity activity;
    ArrayList<DashboardExpiryProductModel.Result> resultArrayList;

    public DashboardExpiryProductUpdateAdapter(Activity activity, ArrayList<DashboardExpiryProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_expiry_product, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final DashboardExpiryProductModel.Result result = resultArrayList.get(position);
        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage()).into(holder.imgViewProduct);

        final String productName = Utils.camelCasing(result.getProductName());
        holder.txtViewProductName.setText(productName);

        final String categoryName = Utils.camelCasing(result.getBrandName());//category name is changed into brandname
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_expiry);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(Color.BLACK)));

                ImageView imgViewProduct = (ImageView) dialog.findViewById(R.id.imgViewProduct);
                TextView txtViewProductCategoryName = (TextView) dialog.findViewById(R.id.txtViewProductCategoryName);
                TextView txtViewProductName = (TextView) dialog.findViewById(R.id.txtViewProductName);
                TextView txtViewStock = (TextView) dialog.findViewById(R.id.txtViewStock);
                TextView txtViewProductQuantity = (TextView) dialog.findViewById(R.id.txtViewProductQuantity);
                TextView txtViewStoreNameAndCity = (TextView) dialog.findViewById(R.id.txtViewStoreNameAndCity);
                TextView txtViewProductDate = (TextView) dialog.findViewById(R.id.txtViewProductDate);
                ImageView imgViewClose = (ImageView) dialog.findViewById(R.id.imgViewClose);


                FontHelper.applyFont(activity, txtViewProductCategoryName, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductName, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewStock, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductQuantity, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewStoreNameAndCity, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductDate, FontHelper.FontType.FONT_Normal);

                Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage()).into(imgViewProduct);
                txtViewProductCategoryName.setText(categoryName);
                txtViewProductName.setText(productName);


                txtViewStock.setText(result.getStock() + " " + result.getStockUnitMeasure());
                txtViewProductQuantity.setText("UOM: " + result.getWeight() + result.getItemUnitMeasure());
                txtViewStoreNameAndCity.setText(Utils.camelCasing(result.getStoreName() + "-" + Utils.camelCasing(result.getCity())));


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
                    txtViewProductDate.setText(daysToShow + " Day ago");
                } else {
                    txtViewProductDate.setText(daysToShow + " Days ago");
                }




                imgViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView cardView;

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
