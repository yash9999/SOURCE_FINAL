package com.example.yogeshgarg.source.mvp.vacation.vacation_home;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.mvp.login.LoginActivity;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.splash.SplashActivity;
import com.example.yogeshgarg.source.mvp.stores.StoresActivity;
import com.example.yogeshgarg.source.mvp.vacation.vacation_calendar.VacationCalendarActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 20/08/17.
 */

public class VacationHomeAdapter extends RecyclerView.Adapter<VacationHomeAdapter.Holder> {

    Activity activity;
    ArrayList<VacationHomeModel.Result> resultArrayList;
    VacationHomeFragment vacationHomeFragment;


    public VacationHomeAdapter(Activity activity, ArrayList<VacationHomeModel.Result> resultArrayList, VacationHomeFragment vacationHomeFragment) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
        this.vacationHomeFragment = vacationHomeFragment;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_vacation, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        VacationHomeModel.Result result = resultArrayList.get(position);

        holder.txtViewComment.setText(result.getComment());


        String initialDateToShow = null;
        String finalDateToShow = null;

        String initialDate = result.getStart();
        String finalDate = result.getEnd();
        result.getVacationId();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateInitial = null;
            Date dateFinal = null;
            try {
                dateInitial = simpleDateFormat.parse(initialDate);
                dateFinal = simpleDateFormat.parse(finalDate);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            SimpleDateFormat newFormatDate = new SimpleDateFormat("MMM dd, yyyy");
            initialDateToShow = newFormatDate.format(dateInitial);
            finalDateToShow = newFormatDate.format(dateFinal);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.txtViewDate.setText(initialDateToShow + " to " + finalDateToShow);


        String strLastUpdated = result.getDateadded();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String daysToShow = null;
        try {
            String strCurrentDate = simpleDateFormat.format(currentDate);
            Date lastUpdatedDay = simpleDateFormat.parse(strLastUpdated);
            Date currentUpdateDay = simpleDateFormat.parse(strCurrentDate);
            long diff = currentUpdateDay.getTime() - lastUpdatedDay.getTime();
            daysToShow = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((Integer.parseInt(daysToShow) == 1) || (Integer.parseInt(daysToShow) == 0)) {
            holder.txtViewAdded.setText("Added - " + daysToShow + " Day ago");
        } else {
            holder.txtViewAdded.setText("Added - " + daysToShow + " Days ago");
        }

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.relLayDelete));


        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        holder.relLayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public void removeItem(int position) {

        String vacationId = resultArrayList.get(position).getVacationId();
        vacationHomeFragment.callingVacationDelete(vacationId);

        resultArrayList.remove(position);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (resultArrayList.size() == 0) {
                    vacationHomeFragment.relLayNoVacationTaken.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        }, 1000);
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        @BindView(R.id.relLayDelete)
        RelativeLayout relLayDelete;


        @BindView(R.id.relLaVacation)
        RelativeLayout relLaVacation;


        @BindView(R.id.txtViewVacation)
        TextView txtViewVacation;

        @BindView(R.id.txtViewDate)
        TextView txtViewDate;

        @BindView(R.id.txtViewComment)
        TextView txtViewComment;

        @BindView(R.id.txtViewAdded)
        TextView txtViewAdded;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewVacation, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewDate, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewComment, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewAdded, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
