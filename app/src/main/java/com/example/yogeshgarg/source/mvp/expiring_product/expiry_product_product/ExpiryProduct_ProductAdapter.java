package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product;

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
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar.ExpiryProductCalendarActivity;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class ExpiryProduct_ProductAdapter extends RecyclerView.Adapter<ExpiryProduct_ProductAdapter.Holder> {

    Activity activity;
    ArrayList<ExpiryProduct_ProductModel.Result> resultArrayList;

    public ExpiryProduct_ProductAdapter(Activity activity, ArrayList<ExpiryProduct_ProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_ep_product, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ExpiryProduct_ProductModel.Result result = resultArrayList.get(position);
        String link = result.getImage();

        String productName = result.getProductName();
        String categoryName = result.getCategoryName();
        final String stock = result.getStock();
        final String stockUnit = result.getStockUnitMeasure();
        final String productId = result.getProductId();

        holder.txtViewCategoryName.setText(Utils.camelCasing(categoryName));
        holder.txtViewProductName.setText(Utils.camelCasing(productName));

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).transform(new CircleTransform()).into(holder.imgViewProduct);

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ExpiryProductCalendarActivity.class);
                intent.putExtra(ConstIntent.KEY_PRODUCT_ID, productId);
                intent.putExtra(ConstIntent.KEY_STOCK, stock);
                intent.putExtra(ConstIntent.KEY_STOCK_UNIT, stockUnit);
                ((ExpiryProduct_ProductActivity) activity).startActivity(intent);
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
