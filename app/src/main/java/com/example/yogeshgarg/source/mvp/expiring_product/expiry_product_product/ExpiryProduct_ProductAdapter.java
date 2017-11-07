package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_product;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_brand.ExpiryProductBrandModel;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_calendar.ExpiryProductCalendarActivity;
import com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_category.ExpiryProductCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class ExpiryProduct_ProductAdapter extends RecyclerView.Adapter<ExpiryProduct_ProductAdapter.Holder> implements Filterable{

    Activity activity;
    ArrayList<ExpiryProduct_ProductModel.Result> originalArrayList;
    ArrayList<ExpiryProduct_ProductModel.Result> filterResultArrayList;

    public ExpiryProduct_ProductAdapter(Activity activity, ArrayList<ExpiryProduct_ProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.originalArrayList = resultArrayList;
        this.filterResultArrayList=originalArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_expiry_category, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final ExpiryProduct_ProductModel.Result result = filterResultArrayList.get(position);
        String link = result.getImage();


        final String productName = result.getProductName();
        final String brandName = result.getBrandName();

        final int stock = result.getStock();
        final String stockUnit = result.getStockUnitMeasure();
        final String productId = result.getProductId();

        holder.txtViewCategoryName.setText(Utils.camelCasing(productName));
        holder.txtViewProductQuantity.setText(Utils.camelCasing(brandName));

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).transform(new CircleTransform()).into(holder.imgViewCategory);

        holder.relLayImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ExpiryProductCalendarActivity.class);
                intent.putExtra(ConstIntent.KEY_PRODUCT_ID, productId);
                intent.putExtra(ConstIntent.KEY_STOCK, String.valueOf(stock));
                intent.putExtra(ConstIntent.KEY_STOCK_UNIT, stockUnit);
                intent.putExtra(ConstIntent.PRODUCT_NAME, productName);
                intent.putExtra(ConstIntent.BRAND_NAME, brandName);
                intent.putExtra(ConstIntent.EXPIRY_PRODUCT_IMAGE, result.getImage());

                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return filterResultArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strCharSequence = charSequence.toString();
                if (strCharSequence.isEmpty()) {
                    filterResultArrayList = originalArrayList;
                } else {
                    ArrayList<ExpiryProduct_ProductModel.Result> filteringInnerArrayList = new ArrayList<>();

                    for (ExpiryProduct_ProductModel.Result result : originalArrayList) {
                        if (result.getProductName().toLowerCase().contains(strCharSequence)) {
                            filteringInnerArrayList.add(result);
                        }
                    }

                    filterResultArrayList = filteringInnerArrayList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterResultArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterResultArrayList = (ArrayList<ExpiryProduct_ProductModel.Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
