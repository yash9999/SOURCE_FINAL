package com.example.yogeshgarg.source.mvp.product_list.product_list_brand;

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
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.price_analysis.PriceAnalysisModel;
import com.example.yogeshgarg.source.mvp.product_list.product_list_category.ProductListCategoryAdapter;
import com.example.yogeshgarg.source.mvp.product_list.product_list_product.ProductListProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public class  ProductListBrandAdapter extends RecyclerView.Adapter<ProductListBrandAdapter.Holder> implements Filterable {

    Activity activity;
    ArrayList<ProductListBrandModel.Result> originalArrayList;
    ArrayList<ProductListBrandModel.Result> filterResultArrayList;

    public ProductListBrandAdapter(Activity activity, ArrayList<ProductListBrandModel.Result> resultArrayList) {
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
        final ProductListBrandModel.Result result = filterResultArrayList.get(position);
        String brandName = result.getName();
        String product = result.getProducts();
        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink();
        final int publish = result.getPublish();

        Picasso.with(activity).load(link).transform(new CircleTransform()).into(holder.imgView);
        holder.txtViewCategoryName.setText(Utils.camelCasing(brandName));
        if (Integer.parseInt(product) <= 1) {
            holder.txtViewBrandAndProduct.setText(product + " product");
        } else {
            holder.txtViewBrandAndProduct.setText(product + " products");
        }

        if (publish == 1) {
            holder.imgViewToggle.setImageResource(R.mipmap.ic_toggle_on);
        } else {
            holder.imgViewToggle.setImageResource(R.mipmap.ic_toggle_off);
        }

        holder.imgViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(publish==0){
                    ((ProductListBrandActivity)activity).publishApi(1,position,result.getBrandId());
                }else{
                    ((ProductListBrandActivity)activity).publishApi(0,position,result.getBrandId());
                }
            }
        });

        holder.relLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ProductListProductActivity.class);
                intent.putExtra(ConstIntent.KEy_BRAND_ID,result.getBrandId());
                activity.startActivity(intent);
            }
        });
    }

    public void changeToggled(int publish,int position){
        filterResultArrayList.get(position).setPublish(publish);
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
                    ArrayList<ProductListBrandModel.Result> filteringInnerArrayList = new ArrayList<>();

                    for (ProductListBrandModel.Result result : originalArrayList) {
                        if (result.getName().toLowerCase().contains(strCharSequence)) {
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
                filterResultArrayList = (ArrayList<ProductListBrandModel.Result>) filterResults.values;
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
            FontHelper.setFontFace(txtViewCategoryName, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewBrandAndProduct, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
