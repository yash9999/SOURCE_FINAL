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
import com.example.yogeshgarg.source.mvp.dashboard.adapter.DashboardInStoreUpdateAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
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

public class DashboardActivityInStoreProductAdapter extends RecyclerView.Adapter<DashboardActivityInStoreProductAdapter.Holder> {

    Activity activity;
    ArrayList<DashboardInStoreModel.Result> resultArrayList;

    public DashboardActivityInStoreProductAdapter(Activity activity, ArrayList<DashboardInStoreModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_db_activity_instore, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        DashboardInStoreModel.Result result = resultArrayList.get(position);

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage()).error(R.mipmap.ic_browser).into(holder.imgViewProduct);


        String productName = Utils.camelCasing(result.getProductName());
        holder.txtViewProductName.setText(productName);
        String categoryName = Utils.camelCasing(result.getCategoryName());
        holder.txtViewProductCategoryName.setText(categoryName);
        holder.txtViewStock.setText("Sampling");
        holder.txtViewProductQuantity.setText("UOM: " + result.getWeight() + result.getItemUnitMeasure());
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
