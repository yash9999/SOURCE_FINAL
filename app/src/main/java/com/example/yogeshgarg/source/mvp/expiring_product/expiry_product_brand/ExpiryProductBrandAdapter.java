package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand;

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
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryAdapter;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product.ExpiryProduct_ProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 30/09/17.
 */

public class ExpiryProductBrandAdapter extends RecyclerView.Adapter<ExpiryProductBrandAdapter.Holder> {

    Activity activity;
    ArrayList<ExpiryProductBrandModel.Result> resultArrayList;

    public ExpiryProductBrandAdapter(Activity activity, ArrayList<ExpiryProductBrandModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_expiry_category, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final ExpiryProductBrandModel.Result result = resultArrayList.get(position);
        String brandName = result.getName();
        String products = result.getProducts();

        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink();
        Picasso.with(activity).load(link).transform(new CircleTransform()).into(holder.imgViewCategory);
        holder.txtViewCategoryName.setText(Utils.camelCasing(brandName));
        if(Integer.parseInt(products)<=1){
            holder.txtViewProductQuantity.setText(products+" product");
        }else{
            holder.txtViewProductQuantity.setText(products+" products");
        }

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ExpiryProduct_ProductActivity.class);
                intent.putExtra(ConstIntent.KEy_BRAND_ID,result.getBrandId());
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
