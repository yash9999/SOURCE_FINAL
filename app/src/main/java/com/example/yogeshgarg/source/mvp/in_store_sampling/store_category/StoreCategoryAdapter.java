package com.example.yogeshgarg.source.mvp.in_store_sampling.store_category;

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
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryAdapter;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product.ExpiryProduct_ProductActivity;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_brand.InStoreBrandActivity;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_product.StoreProductActivity;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 15/08/17.
 */

public class StoreCategoryAdapter extends RecyclerView.Adapter<StoreCategoryAdapter.Holder> {

    Activity activity;
    ArrayList<StoreCategoryModel.Result> resultArrayList;

    public StoreCategoryAdapter(Activity activity, ArrayList<StoreCategoryModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_instore_category, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        StoreCategoryModel.Result result = resultArrayList.get(position);

        String productQuantity = result.getProducts();

        String categoryName = result.getName();
        String brandQuantity = result.getBrands();

        final String categoryId = result.getCategoryId();

        holder.txtViewCategoryName.setText(Utils.camelCasing(categoryName));

        String productToShow = null;
        String brandToShow = null;

        if (Integer.parseInt(productQuantity) == 1 || Integer.parseInt(productQuantity) == 0) {
            productToShow = productQuantity + " Product";
        } else {
            productToShow = productQuantity + " Products";
        }

        if (Integer.parseInt(brandQuantity) <= 1) {
            brandToShow = brandQuantity + " brand";
        } else {
            brandToShow = brandQuantity + " brands";
        }

        holder.txtViewProductQuantity.setText(brandToShow + ", " + productToShow);

        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink();
        URL myURL = null;

        try {
            myURL = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        Picasso.with(activity).load(String.valueOf(myURL)).transform(new CircleTransform()).into(holder.imgViewCategory);

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, InStoreBrandActivity.class);
                intent.putExtra(ConstIntent.KEY_CATEGORY_ID, categoryId);
                activity.startActivity(intent);
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

        @BindView(R.id.imgViewCategory)
        ImageView imgViewCategory;

        @BindView(R.id.txtViewCategoryName)
        TextView txtViewCategoryName;

        @BindView(R.id.txtViewProductQuantity)
        TextView txtViewProductQuantity;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewCategoryName, FontHelper.FontType.FONT_Semi_Bold, activity);
            FontHelper.setFontFace(txtViewProductQuantity, FontHelper.FontType.FONT_Normal, activity);
        }


    }
}
