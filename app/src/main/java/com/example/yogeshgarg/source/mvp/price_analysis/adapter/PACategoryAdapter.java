package com.example.yogeshgarg.source.mvp.price_analysis.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.mvp.price_analysis.PriceAnalysisModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 10/09/17.
 */

public class PACategoryAdapter extends RecyclerView.Adapter<PACategoryAdapter.Holder> implements Filterable {

    Activity activity;
    ArrayList<PriceAnalysisModel.Result.Original> originalArrayList;
    ArrayList<PriceAnalysisModel.Result.Original> filteringOriginalArrayList;

    public PACategoryAdapter(Activity activity, ArrayList<PriceAnalysisModel.Result.Original> originalArrayList) {
        this.activity = activity;
        this.originalArrayList = originalArrayList;
        filteringOriginalArrayList = originalArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_price_analysis, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final PriceAnalysisModel.Result.Original item = filteringOriginalArrayList.get(position);
        holder.txtViewName.setText(item.getCategoryName());
        Log.e("categoryId", item.getCategoryId());

        if (item.getCategoryTick()) {
            holder.imgViewSelect.setImageResource(R.mipmap.ic_tick);
            holder.imgViewSelect.setVisibility(View.VISIBLE);
        } else {
            holder.imgViewSelect.setVisibility(View.INVISIBLE);
        }

        holder.txtViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getCategoryTick()) {
                    item.setCategoryTick(false);
                    holder.imgViewSelect.setVisibility(View.INVISIBLE);
                } else {
                    item.setCategoryTick(true);
                    holder.imgViewSelect.setVisibility(View.VISIBLE);
                    holder.imgViewSelect.setImageResource(R.mipmap.ic_tick);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return filteringOriginalArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strCharSequence = charSequence.toString();
                if (strCharSequence.isEmpty()) {
                    filteringOriginalArrayList = originalArrayList;
                } else {
                    ArrayList<PriceAnalysisModel.Result.Original> filteringInnerArrayList = new ArrayList<>();

                    for (PriceAnalysisModel.Result.Original original : originalArrayList) {
                        if (original.getCategoryName().toLowerCase().contains(strCharSequence)) {
                            filteringInnerArrayList.add(original);
                        }
                    }

                    filteringOriginalArrayList = filteringInnerArrayList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteringOriginalArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteringOriginalArrayList = (ArrayList<PriceAnalysisModel.Result.Original>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtViewName)
        TextView txtViewName;

        @BindView(R.id.imgViewSelect)
        ImageView imgViewSelect;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            FontHelper.applyFont(activity, txtViewName, FontHelper.FontType.FONT_Normal);
        }
    }
}
