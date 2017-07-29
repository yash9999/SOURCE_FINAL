package com.example.yogeshgarg.source.mvp.dashboard;

import android.content.Context;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
