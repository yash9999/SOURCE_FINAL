package com.example.yogeshgarg.source.mvp.dashboard_activities.adapter;

import android.app.Activity;
import android.graphics.Paint;
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
import com.example.yogeshgarg.source.mvp.dashboard.adapter.NewProductAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 02/08/17.
 */

public class DashboardActivityNewProductAdapter  extends  RecyclerView.Adapter<DashboardActivityNewProductAdapter.ViewHolder> {

    Activity activity;
    ArrayList<NewProductModel.Result> resultArrayList;


    public DashboardActivityNewProductAdapter(Activity activity, ArrayList<NewProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_db_activity_new_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewProductModel.Result result = resultArrayList.get(position);


        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink()).error(R.mipmap.ic_browser).into(holder.imgViewProduct);



        String productName = Utils.camelCasing(result.getProductName());
        holder.txtViewProductName.setText(productName);

        String brandName = Utils.camelCasing(result.getBrandName());
        holder.txtViewProductCategoryName.setText(brandName);

        if (result.getDiscount() == null) {
            holder.txtViewDiscount.setVisibility(View.GONE);
        } else {
            String discount = result.getDiscount();
            holder.txtViewDiscount.setText(discount + "%");
        }


        String price = Utils.currencyFormat(result.getCost());
        holder.txtViewProductMRP.setText(price);
        holder.txtViewProductMRP.setPaintFlags(holder.txtViewProductMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        try {
            double douPrice = Double.parseDouble(result.getCost());
            double douDiscount = Double.parseDouble(result.getDiscount());
            double discountPrice = (douPrice * douDiscount) / 100;

            double sellingPrice = douPrice - discountPrice;

            String strSellingPrice = String.valueOf(sellingPrice);

            holder.txtViewProductSellingPrice.setText(Utils.currencyFormat(strSellingPrice));

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        holder.txtViewProductQuantity.setText("UOM: " + result.getWeight());
        holder.txtViewStoreNameAndCity.setText(Utils.camelCasing(result.getStoreName() + "-" + Utils.camelCasing(result.getCity())));

        holder.txtViewProductDate.setText(setDate(result.getDateadded()));

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    private String setDate(String rawDate) {
        String dateToShow = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateInitial = null;
            try {
                dateInitial = simpleDateFormat.parse(rawDate);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SimpleDateFormat newFormatDate = new SimpleDateFormat("MMM dd, yyyy");
            dateToShow = newFormatDate.format(dateInitial);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dateToShow;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewDiscount)
        TextView txtViewDiscount;

        @BindView(R.id.txtViewProductName)
        TextView txtViewProductName;

        @BindView(R.id.txtViewProductCategoryName)
        TextView txtViewProductCategoryName;

        @BindView(R.id.txtViewProductMRP)
        TextView txtViewProductMRP;

        @BindView(R.id.txtViewProductSellingPrice)
        TextView txtViewProductSellingPrice;

        @BindView(R.id.txtViewProductQuantity)
        TextView txtViewProductQuantity;

        @BindView(R.id.txtViewStoreNameAndCity)
        TextView txtViewStoreNameAndCity;

        @BindView(R.id.txtViewProductDate)
        TextView txtViewProductDate;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {


            FontHelper.setFontFace(txtViewDiscount, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductCategoryName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductMRP, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductSellingPrice, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductQuantity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewStoreNameAndCity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductDate, FontHelper.FontType.FONT_Normal, activity);

        }
    }

}
