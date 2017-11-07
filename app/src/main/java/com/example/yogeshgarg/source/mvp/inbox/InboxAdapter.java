package com.example.yogeshgarg.source.mvp.inbox;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.DateMethods;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.helper.Utils;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.mvp.chatting.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 15/10/17.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.Holder> {

    Activity activity;
    ArrayList<InboxModel.Result> resultArrayList;

    public InboxAdapter(Activity activity, ArrayList<InboxModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_inbox, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final InboxModel.Result result = resultArrayList.get(position);
        String firstName = result.getFirstname();
        String lastName = result.getLastname();
        final String name = firstName + " " + lastName;

        holder.txtViewName.setText(Utils.camelCasing(name));
        holder.txtViewStore.setText(result.getStore());
        holder.txtViewLastMsg.setText(result.getMessage());
        holder.txtViewUnreadMsg.setText(result.getTotalunread().toString());
        holder.txtViewDate.setText(DateMethods.setDateNotYear(result.getDate()));

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getImagelink()).transform(new CircleTransform()).into(holder.imgViewProfile);

        holder.relLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("UserName",name);
                intent.putExtra("OpponentProfile",result.getImagelink());
                intent.putExtra("UserId",result.getUserId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.relLay)
        RelativeLayout relLay;

        @BindView(R.id.imgViewProfile)
        ImageView imgViewProfile;

        @BindView(R.id.txtViewName)
        TextView txtViewName;

        @BindView(R.id.txtViewStore)
        TextView txtViewStore;

        @BindView(R.id.txtViewLastMsg)
        TextView txtViewLastMsg;

        @BindView(R.id.txtViewDate)
        TextView txtViewDate;

        @BindView(R.id.txtViewUnreadMsg)
        TextView txtViewUnreadMsg;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(txtViewName, FontHelper.FontType.FONT_Semi_Bold, activity);
            FontHelper.setFontFace(txtViewStore, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewLastMsg, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewDate, FontHelper.FontType.FONT_Normal, activity);
            FontHelper.setFontFace(txtViewUnreadMsg, FontHelper.FontType.FONT_Normal, activity);
        }
    }
}
