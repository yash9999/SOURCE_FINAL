package com.example.yogeshgarg.source.mvp.price_analysis.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.mvp.price_analysis.PriceAnalysisFragment;
import com.example.yogeshgarg.source.mvp.price_analysis.PriceAnalysisModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 10/09/17.
 */

public class PAStoreAdapter extends RecyclerView.Adapter<PAStoreAdapter.Holder> implements Filterable {


    Activity activity;
    ArrayList<PriceAnalysisModel.Result.Original.Store> originalArrayList;
    ArrayList<PriceAnalysisModel.Result.Original.Store> filteringOriginalArrayList;
    PriceAnalysisFragment priceAnalysisFragment;

    public PAStoreAdapter(Activity activity, ArrayList<PriceAnalysisModel.Result.Original.Store> originalArrayList,
                          PriceAnalysisFragment priceAnalysisFragment) {
        this.activity = activity;
        this.priceAnalysisFragment=priceAnalysisFragment;
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
        final PriceAnalysisModel.Result.Original.Store item = filteringOriginalArrayList.get(position);
        holder.txtViewName.setText(item.getText());

        if (item.getStoreTick()) {
            holder.imgViewSelect.setVisibility(View.VISIBLE);
            holder.imgViewSelect.setImageResource(R.mipmap.ic_tick);
        } else {
            holder.imgViewSelect.setVisibility(View.INVISIBLE);
        }

        Log.e("StoreName", item.getStoreId());

        holder.relLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getStoreTick()) {
                    //priceAnalysisFragment.changeSelectAllIcon();
                    item.setStoreTick(false);
                    holder.imgViewSelect.setVisibility(View.INVISIBLE);
                } else {
                    item.setStoreTick(true);
                    holder.imgViewSelect.setVisibility(View.VISIBLE);
                    holder.imgViewSelect.setImageResource(R.mipmap.ic_tick);
                }
            }
        });

    }

    public void setCheckBox(boolean flag){
        if(flag){
            for (int i=0;i<filteringOriginalArrayList.size();i++){
             filteringOriginalArrayList.get(i).setStoreTick(true);
            }
        }else{
            for (int i=0;i<filteringOriginalArrayList.size();i++){
                filteringOriginalArrayList.get(i).setStoreTick(false);
            }
        }
        notifyDataSetChanged();
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
                    ArrayList<PriceAnalysisModel.Result.Original.Store> filteringInnerArrayList = new ArrayList<>();

                    for (PriceAnalysisModel.Result.Original.Store original : originalArrayList) {
                        if (original.getText().toLowerCase().contains(strCharSequence)) {
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
                filteringOriginalArrayList = (ArrayList<PriceAnalysisModel.Result.Original.Store>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtViewName)
        TextView txtViewName;

        @BindView(R.id.imgViewSelect)
        ImageView imgViewSelect;

        @BindView(R.id.relLay)
        RelativeLayout relLay;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            FontHelper.applyFont(activity, txtViewName, FontHelper.FontType.FONT_Normal);
        }
    }

}
