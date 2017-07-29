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

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    Context context;
    ArrayList<NewProductModel.Result> products;


    public NewProductAdapter(Context context, ArrayList<NewProductModel.Result> products) {
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
        NewProductModel.Result product=products.get(position);

       Picasso.with(context).load("https://www.augmentedui.com/source/v1/image/"+product.getLink()).into(holder.imgProduct);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductCategoryName.setText(product.getStoreName());

        holder.txtProductMRP.setText(product.getCost());
        holder.txtProductMRP.setPaintFlags(holder.txtProductMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        try {
            Double cost = 0.00;
            cost = Double.valueOf(product.getCost());

            String discountedPrice = String.valueOf(Double.valueOf(product.getCost()) -
                    ((Double.valueOf(product.getCost()) * Double.valueOf(product.getCost())) / 100));

            holder.txtProductSellingPrice.setText(discountedPrice);
        }catch (NumberFormatException exp){
            exp.printStackTrace();
            holder.txtProductSellingPrice.setText("0.00");
        }

        holder.txtProductQuantity.setText(product.getWeight());
        holder.txtProductDate.setText(product.getDateadded());

        holder.txtProductBrand.setText(product.getAddress());
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
