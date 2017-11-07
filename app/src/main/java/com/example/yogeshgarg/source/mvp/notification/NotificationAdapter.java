package com.example.yogeshgarg.source.mvp.notification;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v4.content.ContextCompat;
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
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.session.NotificationSession;
import com.example.yogeshgarg.source.mvp.in_store_sampling.store_category.StoreCategoryAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 14/09/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    Activity activity;
    ArrayList<NotificationPushModel.Result> resultArrayList;
    NotificationFragment notificationFragment;


    public NotificationAdapter(Activity activity, ArrayList<NotificationPushModel.Result> resultArrayList, NotificationFragment notificationFragment) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
        this.notificationFragment = notificationFragment;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_notification, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final NotificationPushModel.Result result = resultArrayList.get(position);

        holder.txtViewTitle.setText(result.getTitle());
        holder.txtViewDescription.setText(result.getMessage());
        String date = result.getDateadded();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date testDate = null;
        try {
            testDate = simpleDateFormat.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd @ hh:mm aaa");
        String newFormat = formatter.format(testDate);
        holder.txtViewTime.setText(newFormat);

        String link = result.getImage();
        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).transform(new CircleTransform()).into(holder.imgViewItem);

        //1 means read
        //0 means unread
        if (result.getRead() == 1) {

            holder.relLayRoot.setBackgroundColor(ContextCompat.getColor(activity, R.color.grey));
            holder.txtViewTitle.setTextColor(ContextCompat.getColor(activity, R.color.alpha_black));
            holder.txtViewDescription.setTextColor(ContextCompat.getColor(activity, R.color.alpha_black));
            holder.txtViewTime.setTextColor(ContextCompat.getColor(activity, R.color.alpha_black));

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.imgViewItem.setColorFilter(filter);

        } else if (result.getRead() == 0) {

            holder.relLayRoot.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_white));
            holder.txtViewTitle.setTextColor(ContextCompat.getColor(activity, R.color.color_black));
            holder.txtViewDescription.setTextColor(ContextCompat.getColor(activity, R.color.color_black));
            holder.txtViewTime.setTextColor(ContextCompat.getColor(activity, R.color.color_black));
            Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + link).transform(new CircleTransform()).into(holder.imgViewItem);
        }




        holder.relLayRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationFragment.markReadNotification(result.getId(), position);
            }
        });
    }

    public void markRead(int position) {
        resultArrayList.get(position).setRead(1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.relLayRoot)
        RelativeLayout relLayRoot;

        @BindView(R.id.imgViewItem)
        ImageView imgViewItem;

        @BindView(R.id.txtViewTime)
        TextView txtViewTime;


        @BindView(R.id.txtViewTitle)
        TextView txtViewTitle;

        @BindView(R.id.txtViewDescription)
        TextView txtViewDescription;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.applyFont(activity, txtViewTitle, FontHelper.FontType.FONT_Semi_Bold);
            FontHelper.applyFont(activity, txtViewDescription, FontHelper.FontType.FONT_LIGHT);
            FontHelper.applyFont(activity, txtViewTime, FontHelper.FontType.FONT_Normal);
        }
    }
}
