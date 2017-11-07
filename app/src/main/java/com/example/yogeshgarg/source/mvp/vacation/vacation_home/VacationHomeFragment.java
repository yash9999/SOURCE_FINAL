package com.example.yogeshgarg.source.mvp.vacation.vacation_home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.interfaces.OnClickInterface;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.navigation.NavigationActivity;
import com.example.yogeshgarg.source.mvp.vacation.vacation_calendar.VacationCalendarActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VacationHomeFragment extends Fragment implements VacationHomeView {

    @BindView(R.id.imgViewPlus)
    ImageView imgViewPlus;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @BindView(R.id.relLayNoVacationTaken)
    RelativeLayout relLayNoVacationTaken;

    @BindView(R.id.txtViewVacation)
    TextView txtViewVacation;

    ArrayList<VacationHomeModel.Result> resultArrayList;

    VacationHomePresenterImpl vacationHomePresenterImpl = null;
    VacationHomeAdapter vacationHomeAdapter;
    String vacationId;

    private Paint p = new Paint();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vacationHomePresenterImpl = new VacationHomePresenterImpl(getActivity(), this);
        setFont();
        callingVacationHomeApi();
    }


    private void setFont() {
        FontHelper.setFontFace(txtViewVacation, FontHelper.FontType.FONT_Normal, getActivity());
    }


    //------------------------------------------------------------------------------
    @Override
    public void onSuccessVH(ArrayList<VacationHomeModel.Result> resultArrayList) {

        this.resultArrayList = resultArrayList;
        if (resultArrayList.size() == 0) {
            relLayNoVacationTaken.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            relLayNoVacationTaken.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            setLayoutManager();
        }

    }

    @Override
    public void onUnsuccessVH(String message) {
        SnackNotify.showMessage(message, frameLayout);
    }

    @Override
    public void onInternetError() {
        SnackNotify.checkConnection(onRetryVacationHomeApi, frameLayout);
    }

    OnClickInterface onRetryVacationHomeApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingVacationHomeApi();
        }
    };

    //----------------------------------------------------------------------------------------------
    @Override
    public void onSuccessDelete() {

    }

    @Override
    public void onUnsuccessDelete(String message) {
        SnackNotify.showMessage(message, frameLayout);
    }

    @Override
    public void onInternetErrorDelete() {
        SnackNotify.checkConnection(onRetryVactionDeleteApi, frameLayout);
    }

    OnClickInterface onRetryVactionDeleteApi = new OnClickInterface() {
        @Override
        public void onClick() {
            callingVacationDelete(vacationId);
        }
    };


    //----------------------------------------------------------------------------------------------

    private void callingVacationHomeApi() {
        vacationHomePresenterImpl.callingVacationHomeApi();
    }

    @OnClick(R.id.imgViewPlus)
    public void setImgViewPlus() {
        Intent intent = new Intent(getActivity(), VacationCalendarActivity.class);
        ((NavigationActivity) getActivity()).startActivity(intent);
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        vacationHomeAdapter = new VacationHomeAdapter(getActivity(), resultArrayList, this);
        recyclerView.setAdapter(vacationHomeAdapter);
       // initSwipe();
    }

    /*private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    vacationHomeAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, p);
                    icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_delete_white);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, p);

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }*/

    public void callingVacationDelete(String vacationID) {
        this.vacationId = vacationID;
        vacationHomePresenterImpl.callingVacationDeleteApi(vacationID);
    }


}
