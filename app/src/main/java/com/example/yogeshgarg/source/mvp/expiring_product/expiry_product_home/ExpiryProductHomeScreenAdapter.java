package com.example.yogeshgarg.source.mvp.expiring_product.expiry_product_home;

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
import com.example.yogeshgarg.source.common.helper.DateMethods;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.new_product.new_product_home.NewProductHomeModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 13/08/17.
 */

public class ExpiryProductHomeScreenAdapter extends RecyclerView.Adapter<ExpiryProductHomeScreenAdapter.Holder> implements Filterable{

    Activity activity;
    ArrayList<ExpiryProductHomeModel.Result> originalArrayList;
    ArrayList<ExpiryProductHomeModel.Result> filterResultArrayList;

    public ExpiryProductHomeScreenAdapter(Activity activity, ArrayList<ExpiryProductHomeModel.Result> resultArrayList) {
        this.activity = activity;
        this.originalArrayList = resultArrayList;
        this.filterResultArrayList=originalArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_expiry_product_home, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ExpiryProductHomeModel.Result result = filterResultArrayList.get(position);

        String productName = result.getProductName();
        String link = ConstIntent.PREFIX_URL_OF_IMAGE + result.getImage();
        Picasso.with(activity).load(link).transform(new CircleTransform()).into(holder.imgViewProduct);

        //inserting data into fields
        holder.txtViewProductName.setText(Utils.camelCasing(productName));
        holder.txtViewDateAdded.setText(DateMethods.setDate(result.getDateadded()));
        holder.txtViewProductQuantity.setText(result.getStock());
        holder.txtViewUnit.setText(result.getStockUnitMeasure());
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
                    ArrayList<ExpiryProductHomeModel.Result> filteringInnerArrayList = new ArrayList<>();

                    for (ExpiryProductHomeModel.Result result : originalArrayList) {
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
                filterResultArrayList = (ArrayList<ExpiryProductHomeModel.Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.relLayImages)
        RelativeLayout relLayImages;

        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewProductName)
        TextView txtViewProductName;

        @BindView(R.id.txtViewDateAdded)
        TextView txtViewDateAdded;

        @BindView(R.id.txtViewProductQuantity)
        TextView txtViewProductQuantity;

        @BindView(R.id.txtViewUnit)
        TextView txtViewUnit;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Semi_Bold, activity);
            FontHelper.setFontFace(txtViewDateAdded, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewProductQuantity, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewUnit, FontHelper.FontType.FONT_Normal, activity);

        }
    }
}
