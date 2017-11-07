package com.example.yogeshgarg.source.mvp.chatting;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogeshgarg on 05/11/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

    Activity activity;
    ArrayList<ChattingModel.Result> resultArrayList;

    ChatAdapter(Activity activity, ArrayList<ChattingModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_chat, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ChattingModel.Result result = resultArrayList.get(position);
        String message = result.getMessage();

        if (result.getOwner()) {
            holder.rel_receiver.setVisibility(View.GONE);
            if (TextUtils.isEmpty(message)) {
                holder.rel_Sender.setVisibility(View.GONE);
            } else {
                holder.rel_Sender.setVisibility(View.VISIBLE);
                holder.txtMessageSender.setText(message);
            }

        } else {
            holder.rel_Sender.setVisibility(View.GONE);
            if (TextUtils.isEmpty(message)) {
                holder.rel_receiver.setVisibility(View.GONE);
            } else {
                holder.rel_receiver.setVisibility(View.VISIBLE);
                holder.txtMessageReceiver.setText(message);
            }

        }

    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.rel_receiver)
        RelativeLayout rel_receiver;

        @BindView(R.id.rel_Sender)
        RelativeLayout rel_Sender;

        @BindView(R.id.txtMessageReceiver)
        TextView txtMessageReceiver;

        @BindView(R.id.txtMessageSender)
        TextView txtMessageSender;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            FontHelper.applyFont(activity, txtMessageReceiver, FontHelper.FontType.FONT_Normal);
            FontHelper.applyFont(activity, txtMessageSender, FontHelper.FontType.FONT_Normal);
        }
    }
}
