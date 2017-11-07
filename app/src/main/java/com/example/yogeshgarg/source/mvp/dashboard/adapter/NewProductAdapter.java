package com.example.yogeshgarg.source.mvp.dashboard.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshu on 29/07/17.
 */

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    Activity activity;
    ArrayList<NewProductModel.Result> resultArrayList;


    public NewProductAdapter(Activity activity, ArrayList<NewProductModel.Result> resultArrayList) {
        this.activity = activity;
        this.resultArrayList = resultArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_dash_recent_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewProductModel.Result result = resultArrayList.get(position);

        //Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink()).into(holder.imgViewProduct);

        Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink()).into(holder.imgViewProduct, new Callback(){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

               /* byte[] image=result.getLinkByte();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                holder.imgViewProduct.setImageBitmap(bitmap);*/
            }
        });

        final String brandName = Utils.camelCasing(result.getBrandName());
        holder.txtViewProductCategoryName.setText(brandName);//category name is changed into brand name

        final String productName = Utils.camelCasing(result.getProductName());
        holder.txtViewProductName.setText(productName);


        if (result.getDiscount() == null) {
            holder.txtViewDiscount.setVisibility(View.GONE);
        } else {
            String discount = result.getDiscount();
            holder.txtViewDiscount.setText(discount + "%");
        }


        final String price = Utils.currencyFormat(result.getCost());
        holder.txtViewProductMRP.setText(price);
        holder.txtViewProductMRP.setPaintFlags(holder.txtViewProductMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        try {
            double douPrice = Double.parseDouble(result.getCost());
            double douDiscount = Double.parseDouble(result.getDiscount());
            double discountPrice = (douPrice * douDiscount) / 100;

            double sellingPrice = douPrice - discountPrice;

            String strSellingPrice = String.valueOf(sellingPrice);

            holder.txtViewProductSellingPrice.setText(Utils.currencyFormat(strSellingPrice));

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        holder.txtViewProductQuantity.setText("UOM: " + result.getWeight());
        holder.txtViewStoreNameAndCity.setText(Utils.camelCasing(result.getStoreName() + "-" + Utils.camelCasing(result.getCity())));

        holder.txtViewProductDate.setText(setDate(result.getDateadded()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_recent);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(Color.BLACK)));


                ImageView imgViewProduct = (ImageView) dialog.findViewById(R.id.imgViewProduct);
                TextView txtViewDiscount = (TextView) dialog.findViewById(R.id.txtViewDiscount);
                TextView txtViewProductCategoryName = (TextView) dialog.findViewById(R.id.txtViewProductCategoryName);
                TextView txtViewProductName = (TextView) dialog.findViewById(R.id.txtViewProductName);
                TextView txtViewProductMRP = (TextView) dialog.findViewById(R.id.txtViewProductMRP);
                TextView txtViewProductSellingPrice = (TextView) dialog.findViewById(R.id.txtViewProductSellingPrice);
                TextView txtViewProductQuantity = (TextView) dialog.findViewById(R.id.txtViewProductQuantity);
                TextView txtViewStoreNameAndCity = (TextView) dialog.findViewById(R.id.txtViewStoreNameAndCity);
                TextView txtViewProductDate = (TextView) dialog.findViewById(R.id.txtViewProductDate);
                ImageView imgViewClose = (ImageView) dialog.findViewById(R.id.imgViewClose);

                FontHelper.applyFont(activity, txtViewDiscount, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductCategoryName, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductName, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductMRP, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductSellingPrice, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductQuantity, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewStoreNameAndCity, FontHelper.FontType.FONT_Normal);
                FontHelper.applyFont(activity, txtViewProductDate, FontHelper.FontType.FONT_Normal);

                //Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink()).into(imgViewProduct);

                Picasso.with(activity).load(ConstIntent.PREFIX_URL_OF_IMAGE + result.getLink()).into(imgViewProduct, new Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

               /* byte[] image=result.getLinkByte();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                holder.imgViewProduct.setImageBitmap(bitmap);*/
                    }
                });

                txtViewProductCategoryName.setText(brandName);
                txtViewProductName.setText(productName);


                if (result.getDiscount() == null) {
                    txtViewDiscount.setVisibility(View.GONE);
                } else {
                    String discount = result.getDiscount();
                    txtViewDiscount.setText(discount + "%");
                }

                txtViewProductMRP.setText(price);
                txtViewProductMRP.setPaintFlags(txtViewProductMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                try {
                    double douPrice = Double.parseDouble(result.getCost());
                    double douDiscount = Double.parseDouble(result.getDiscount());
                    double discountPrice = (douPrice * douDiscount) / 100;

                    double sellingPrice = douPrice - discountPrice;

                    String strSellingPrice = String.valueOf(sellingPrice);

                    txtViewProductSellingPrice.setText(Utils.currencyFormat(strSellingPrice));

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                txtViewProductQuantity.setText("UOM: " + result.getWeight());
                txtViewStoreNameAndCity.setText(Utils.camelCasing(result.getStoreName() + "-" + Utils.camelCasing(result.getCity())));
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
        return resultArrayList.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.imgViewProduct)
        ImageView imgViewProduct;

        @BindView(R.id.txtViewDiscount)
        TextView txtViewDiscount;

        @BindView(R.id.txtViewProductName)
        TextView txtViewProductName;

        @BindView(R.id.txtViewProductCategoryName)
        TextView txtViewProductCategoryName;

        @BindView(R.id.txtViewProductMRP)
        TextView txtViewProductMRP;

        @BindView(R.id.txtViewProductSellingPrice)
        TextView txtViewProductSellingPrice;

        @BindView(R.id.txtViewProductQuantity)
        TextView txtViewProductQuantity;

        @BindView(R.id.txtViewStoreNameAndCity)
        TextView txtViewStoreNameAndCity;

        @BindView(R.id.txtViewProductDate)
        TextView txtViewProductDate;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {


            FontHelper.setFontFace(txtViewDiscount, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductCategoryName, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductMRP, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductSellingPrice, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductQuantity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewStoreNameAndCity, FontHelper.FontType.FONT_Normal, activity);

            FontHelper.setFontFace(txtViewProductDate, FontHelper.FontType.FONT_Normal, activity);

        }
    }
}
