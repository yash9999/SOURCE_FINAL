package com.example.yogeshgarg.source.mvp.product_list.product_list_product;

import android.app.Activity;
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
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandAdapter;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public class ProductListProductAdapter extends RecyclerView.Adapter<ProductListProductAdapter.Holder> implements Filterable{

    Activity activity;
    ArrayList<ProductListProductModel.Result> originalArrayList;
    ArrayList<ProductListProductModel.Result> filterResultArrayList;

    public ProductListProductAdapter(Activity activity, ArrayList<ProductListProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.originalArrayList = resultArrayList;
        filterResultArrayList=resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_product_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final ProductListProductModel.Result result = filterResultArrayList.get(position);
        String brandName = result.getBrandName();
        String productName = result.getProductName();
        final String publish = result.getPublish();
        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage();

        holder.txtViewCategoryName.setText(Utils.camelCasing(productName));
        holder.txtViewBrandAndProduct.setText(Utils.camelCasing(brandName));
        Picasso.with(activity).load(link).transform(new CircleTransform()).into(holder.imgView);

        if (Integer.parseInt(publish) == 1) {
            holder.imgViewToggle.setImageResource(R.mipmap.ic_toggle_on);
        } else {
            holder.imgViewToggle.setImageResource(R.mipmap.ic_toggle_off);
        }

        holder.imgViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(publish) == 0) {
                    ((ProductListProductActivity)activity).publishApi(1,position,result.getProductId());
                } else {
                    ((ProductListProductActivity)activity).publishApi(0,position,result.getProductId());
                }
            }
        });
    }


    public void changeToggled(int publish,int position){
        filterResultArrayList.get(position).setPublish(String.valueOf(publish));
        notifyDataSetChanged();
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
                    ArrayList<ProductListProductModel.Result> filteringInnerArrayList = new ArrayList<>();

                    for (ProductListProductModel.Result result : originalArrayList) {
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
                filterResultArrayList = (ArrayList<ProductListProductModel.Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.relLay)
        RelativeLayout relLay;

        @BindView(R.id.imgView)
        ImageView imgView;

        @BindView(R.id.txtViewCategoryName)
        TextView txtViewCategoryName;

        @BindView(R.id.txtViewBrandAndProduct)
        TextView txtViewBrandAndProduct;

        @BindView(R.id.imgViewToggle)
        ImageView imgViewToggle;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewCategoryName, FontHelper.FontType.FONT_Semi_Bold, activity);
            FontHelper.setFontFace(txtViewBrandAndProduct, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
