package com.example.yogeshgarg.source.mvp.dashboard_activities.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardPlanogramModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public class DashboardActivityPlanogramAdapter extends RecyclerView.Adapter<DashboardActivityPlanogramAdapter.Holder> {

    Activity activity;
    ArrayList<DashboardPlanogramModel.Result> resultArrayListPlanogram;

    public DashboardActivityPlanogramAdapter(Activity activity, ArrayList<DashboardPlanogramModel.Result> resultArrayListPlanogram) {
        this.activity = activity;
        this.resultArrayListPlanogram = resultArrayListPlanogram;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_db_activity_planogram, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final DashboardPlanogramModel.Result result = resultArrayListPlanogram.get(position);
        String message = result.getMessage();
        String link = result.getLink();
        String title = result.getTitle();
        String date = result.getDateadded();

        holder.txtViewPlanogramTitle.setText(Utils.camelCasing(title));
        holder.txtViewPlanogramMessage.setText(message);
        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).into(holder.imgViewProduct);
        holder.txtViewProductDate.setText(setDate(date));
    }

    @Override
    public int getItemCount() {
        return resultArrayListPlanogram.size();
    }

    private String setDate(String rawDate) {
        String dateToShow = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateInitial = null;
            try {
                dateInitial = simpleDateFormat.parse(rawDate);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SimpleDateFormat newFormatDate = new SimpleDateFormat("MMM dd, yyyy");
            dateToShow = newFormatDate.format(dateInitial);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dateToShow;
    }


    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewPlanogramTitle)
        TextView txtViewPlanogramTitle;

        @BindView(R.id.txtViewPlanogramMessage)
        TextView txtViewPlanogramMessage;

        @BindView(R.id.txtViewProductDate)
        TextView txtViewProductDate;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewPlanogramTitle, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewPlanogramMessage, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewProductDate, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
