package com.example.yogeshgarg.source.mvp.team;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.mvp.dashboard.NewProductModel;
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
        View view= LayoutInflater.from(context).inflate(R.layout.layout_team_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyTeamModel.Result product=products.get(position);

        Picasso.with(context).load("https://www.augmentedui.com/source/v1/image/"+product.getImagelink()).transform(new CircleTransform()).into(holder.imgUser);
        holder.txtUserName.setText(product.getFirstname()+" "+product.getLastname());

        holder.txtPhone.setText(product.getPhone());
        holder.txtUserMessage.setText(product.getCompanyName());
        holder.txtPhone.setText(product.getPhone());

        if(product.getLocationName()!=null)
        holder.txtLocation.setText(product.getLocationName());

        holder.txtStatus.setText(product.getStatus());

        //Font
        FontHelper.applyFont(context,holder.txtUserName, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(context,holder.txtPhone, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(context,holder.txtUserMessage, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(context,holder.txtLocation, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(context,holder.txtStatus, FontHelper.FontType.FONT_Normal);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgUser)
        ImageView imgUser;

        @BindView(R.id.txtUserName)
        TextView txtUserName;

       @BindView(R.id.txtPhone)
        TextView txtPhone;

        @BindView(R.id.txtUserMessage)
        TextView txtUserMessage;

        @BindView(R.id.txtLocation)
        TextView txtLocation;

        @BindView(R.id.txtStatus)
        TextView txtStatus;

     /*    @BindView(R.id.txtProductCategoryName)
        TextView txtProductCategoryName;

        @BindView(R.id.txtProductQuantity)
        TextView txtProductQuantity;

        @BindView(R.id.txtProductDate)
        TextView txtProductDate;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
