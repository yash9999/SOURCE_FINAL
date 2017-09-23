package com.example.yogeshgarg.source.mvp.in_store_sampling.store_product;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product.ExpiryProduct_ProductAdapter;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_calendar.InStoreCalendarActivity;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_category.StoreCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.Holder> {

    Activity activity;
    ArrayList<StoreProductModel.Result> resultArrayList = null;

    public StoreProductAdapter(Activity activity, ArrayList<StoreProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_instore_product, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        StoreProductModel.Result result = resultArrayList.get(position);
        final String link = result.getImage();

        final String brandName = result.getBrandName();//brandName
        final String productName = result.getProductName();//productname
        final String productId = result.getProductId();

        holder.txtViewCategoryName.setText(Utils.camelCasing(productName));
        holder.txtViewProductName.setText(Utils.camelCasing(brandName));

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).transform(new CircleTransform()).into(holder.imgViewProduct);

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, InStoreCalendarActivity.class);
                intent.putExtra(ConstIntent.KEY_INSTORE_PRODUCT_NAME, productName);
                intent.putExtra(ConstIntent.KEY_INSTORE_BRAND_NAME, brandName);
                intent.putExtra(ConstIntent.KEY_PRODUCT_ID, productId);
                intent.putExtra(ConstIntent.KEY_INSTORE_LINK, link);
                ((StoreProductActivity) activity).startActivity(intent);

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

        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewCategoryName)
        TextView txtViewCategoryName;

        @BindView(R.id.txtViewProductName)
        TextView txtViewProductName;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewCategoryName, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
