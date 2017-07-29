package com.example.yogeshgarg.source.mvp.dashboard;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshu on 29/07/17.
 */

public class DashoardProductAdapter extends RecyclerView.Adapter<DashoardProductAdapter.ViewHolder> {

    Context context;
    ArrayList<PriceSurveyProductModel.Result> products;


    public DashoardProductAdapter(Context context, ArrayList<PriceSurveyProductModel.Result> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_dash_product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PriceSurveyProductModel.Result product=products.get(position);

       Picasso.with(context).load("https://www.augmentedui.com/source/v1/image/"+product.getImage()).into(holder.imgProduct);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductCategoryName.setText(product.getCategoryName());

        holder.txtProductMRP.setText(product.getRangestart());
        holder.txtProductMRP.setPaintFlags(holder.txtProductMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtProductSellingPrice.setText(product.getRangeend());

        holder.txtProductQuantity.setText(product.getWeight());
        holder.txtProductDate.setText(product.getDateadded());

        holder.txtProductBrand.setText(product.getBrandName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProduct)
        ImageView imgProduct;

        @BindView(R.id.txtProductName)
        TextView txtProductName;

        @BindView(R.id.txtProductBrand)
        TextView txtProductBrand;

        @BindView(R.id.txtProductMRP)
        TextView txtProductMRP;

        @BindView(R.id.txtProductSellingPrice)
        TextView txtProductSellingPrice;

        @BindView(R.id.txtProductCategoryName)
        TextView txtProductCategoryName;

        @BindView(R.id.txtProductQuantity)
        TextView txtProductQuantity;

        @BindView(R.id.txtProductDate)
        TextView txtProductDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}