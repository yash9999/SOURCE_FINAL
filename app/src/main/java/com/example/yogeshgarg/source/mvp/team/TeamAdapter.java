package com.example.yogeshgarg.source.mvp.team;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshu on 29/07/17.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    Context context;
    ArrayList<MyTeamModel.Result> products;


    public TeamAdapter(Context context, ArrayList<MyTeamModel.Result> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_team_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyTeamModel.Result product = products.get(position);

        Picasso.with(context).load(ConstIntent.PREFIX_URL_OF_IMAGE + product.getImagelink()).transform(new CircleTransform()).into(holder.imgViewProfile);
        holder.txtViewName.setText(product.getFirstname() + " " + product.getLastname());
        holder.txtViewNumber.setText(product.getPhone());

        holder.txtViewStore.setText(product.getStore());

        holder.txtViewRole.setText(product.getRoleName());

        if(product.getStatus().equals("Working")){
            holder.txtViewStatus.setText(product.getStatus());
            holder.txtViewStatus.setTextColor(ContextCompat.getColor(context,R.color.color_bg));
        }else{
            holder.txtViewStatus.setTextColor(ContextCompat.getColor(context,R.color.color_red));
            String str[]=product.getStatus().split(":");
            holder.txtViewStatus.setText(str[0]);
        }



    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imgViewProfile)
        ImageView imgViewProfile;

        @BindView(R.id.txtViewRole)
        TextView txtViewRole;

        @BindView(R.id.txtViewName)
        TextView txtViewName;

        @BindView(R.id.txtViewStore)
        TextView txtViewStore;

        @BindView(R.id.txtViewNumber)
        TextView txtViewNumber;

        @BindView(R.id.txtViewStatus)
        TextView txtViewStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            //Font
            FontHelper.applyFont(context, txtViewName, FontHelper.FontType.FONT_Normal);
            FontHelper.applyFont(context, txtViewNumber, FontHelper.FontType.FONT_Normal);
            FontHelper.applyFont(context, txtViewStatus, FontHelper.FontType.FONT_Normal);
            FontHelper.applyFont(context, txtViewStore, FontHelper.FontType.FONT_Normal);
            FontHelper.applyFont(context, txtViewRole, FontHelper.FontType.FONT_Normal);

        }
    }
}
