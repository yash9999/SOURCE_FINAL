package com.example.yogeshgarg.source.mvp.product_list.product_list_category;

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
import com.example.yogeshgarg.source.mvp.price_survey_product.PriceSurveyProductAdapter;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandActivity;
import com.example.yogeshgarg.source.mvp.product_list.product_list_product.ProductListProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public class ProductListCategoryAdapter extends RecyclerView.Adapter<ProductListCategoryAdapter.Holder> implements Filterable{

    Activity activity;
    ArrayList<ProductListCategoryModel.Result> originalArrayList;
    ArrayList<ProductListCategoryModel.Result> filterResultArrayList;

    ProductListCategoryFragment productListCategoryFragment;
    public ProductListCategoryAdapter(Activity activity, ArrayList<ProductListCategoryModel.Result> resultArrayList,
                                      ProductListCategoryFragment productListCategoryFragment) {
        this.activity = activity;
        this.originalArrayList = resultArrayList;
        filterResultArrayList=resultArrayList;
        this.productListCategoryFragment=productListCategoryFragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_product_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final ProductListCategoryModel.Result result=filterResultArrayList.get(position);
        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink();
        String brand=result.getBrands();
        String product=result.getProducts();
        String categoryName=result.getName();
        final int publish=result.getPublish();

        Picasso.with(activity).load(link).transform(new CircleTransform()).into(holder.imgView);
        holder.txtViewCategoryName.setText(Utils.camelCasing(categoryName));
        String brandsToshow;
        String productToShow;

        if(Integer.parseInt(brand)<=1){
            brandsToshow=brand+" brand, ";
        }else{
            brandsToshow=brand+" brands, ";
        }
        if(Integer.parseInt(product)<=1){
            productToShow=product+" product";
        }else{
            productToShow=product+" products";
        }
        holder.txtViewBrandAndProduct.setText(brandsToshow+productToShow);

        if (publish==1){
            holder.imgViewToggle.setImageResource(R.mipmap.ic_toggle_on);
        }else{
            holder.imgViewToggle.setImageResource(R.mipmap.ic_toggle_off);
        }


        holder.imgViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(publish==0){
                    productListCategoryFragment.publishApi(1,position,result.getCategoryId());
                }else{
                    productListCategoryFragment.publishApi(0,position,result.getCategoryId());
                }
            }
        });

        holder.relLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ProductListBrandActivity.class);
                intent.putExtra(ConstIntent.KEY_CATEGORY_ID, result.getCategoryId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterResultArrayList.size();
    }

    public void changeToggled(int publish,int position){
        filterResultArrayList.get(position).setPublish(publish);
        notifyDataSetChanged();
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
                    ArrayList<ProductListCategoryModel.Result> filteringInnerArrayList = new ArrayList<>();

                    for (ProductListCategoryModel.Result result : originalArrayList) {
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
                filterResultArrayList = (ArrayList<ProductListCategoryModel.Result>) filterResults.values;
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
            ButterKnife.bind(this,itemView);
            setFont();
        }
        private void setFont(){
            FontHelper.setFontFace(txtViewCategoryName, FontHelper.FontType.FONT_Normal,activity);
            FontHelper.setFontFace(txtViewBrandAndProduct, FontHelper.FontType.FONT_Normal,activity);
        }
    }
}
