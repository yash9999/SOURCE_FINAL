package com.example.yogeshgarg.source.mvp.dashboard.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardPlanogramModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 29/09/17.
 */

public class DashboardPlanogramAdapter extends RecyclerView.Adapter<DashboardPlanogramAdapter.Holder> {

    Activity activity;
    ArrayList<DashboardPlanogramModel.Result> resultArrayListPlanogram;

    public DashboardPlanogramAdapter(Activity activity, ArrayList<DashboardPlanogramModel.Result> resultArrayListPlanogram) {
        this.activity = activity;
        this.resultArrayListPlanogram = resultArrayListPlanogram;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_planogram_updates, parent, false);
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

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).error(R.mipmap.ic_browser).into(holder.imgViewProduct);

        holder.txtViewProductDate.setText(setDate(date));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_planogram);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(Color.BLACK)));


                final ImageView imgViewProduct = (ImageView) dialog.findViewById(R.id.imgViewProduct);

                TextView txtViewPlanogramTitle = (TextView) dialog.findViewById(R.id.txtViewPlanogramTitle);
                TextView txtViewPlanogramMessage = (TextView) dialog.findViewById(R.id.txtViewPlanogramMessage);
                TextView txtViewProductDate = (TextView) dialog.findViewById(R.id.txtViewProductDate);
                ImageView imgViewClose = (ImageView) dialog.findViewById(R.id.imgViewClose);

                FontHelper.applyFont(activity, txtViewPlanogramTitle, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewPlanogramMessage, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductDate, FontHelper.FontType.FONT_Normal);

                txtViewPlanogramTitle.setText(Utils.camelCasing(result.getTitle()));
                txtViewPlanogramMessage.setText(result.getMessage());
                Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink()).error(R.mipmap.ic_browser).into(imgViewProduct);


                txtViewProductDate.setText(setDate(result.getDateadded()));
                dialog.show();

                imgViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });
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

        @BindView(R.id.cardView)
        CardView cardView;

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
