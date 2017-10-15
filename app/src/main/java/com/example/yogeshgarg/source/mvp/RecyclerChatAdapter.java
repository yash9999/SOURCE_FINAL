package com.example.yogeshgarg.source.mvp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.utils.Utility;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Braintech on 19-Aug-16.
 */
public class RecyclerChatAdapter extends RecyclerView.Adapter<RecyclerChatAdapter.ViewHolder> {

    ArrayList<QBChatMessage> listChat;
    Context context;
    int opponentId = 0;
    ArrayList<HashMap<String, String>> userImages;

    public RecyclerChatAdapter(Context context, ArrayList<QBChatMessage> listChat, ArrayList<HashMap<String, String>> userImages, int opponentId) {
        this.userImages = userImages;
        this.opponentId = opponentId;
        this.context = context;
        this.listChat = listChat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_chat_item, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        QBChatMessage item = listChat.get(position);

        if (item.getSenderId() == opponentId) {


            Collection<QBAttachment> attachments = item.getAttachments();
            if (attachments != null) {

                String fileId = null;
                for (QBAttachment qbAttachment : attachments) {
                    fileId = qbAttachment.getUrl();
                }


                /*if (Utility.isEmptyString(fileId)) {
                    holder.frmSenderImage.setVisibility(View.GONE);
                } else {
                    holder.frmSenderImage.setVisibility(View.VISIBLE);
                    holder.imgProgressSender.setVisibility(View.VISIBLE);

                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.imgProgressSender);
                    Glide.with(context).load(R.raw.loader).into(imageViewTarget);
                }*/

                final String finalFileId = fileId;
                /*holder.imgFromSender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialogManager.alertShowImage(context, finalFileId);
                    }
                });
*/
  /*              Picasso.with(context)
                        .load(fileId)
                        .error(R.mipmap.ic_default_profile)
                        .noFade()
                        .into(holder.imgFromSender, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.imgProgressSender.setVisibility(View.GONE);
                                holder.frmSenderImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                holder.imgProgressSender.setVisibility(View.GONE);
                            }
                        });
  */          } else {
    //            holder.frmSenderImage.setVisibility(View.GONE);
            }


            holder.card_view_receiver.setVisibility(View.GONE);
            holder.card_view_Sender.setVisibility(View.VISIBLE);
            if (!Utility.isEmptyString(item.getBody())) {
                holder.txtMessageSender.setText(item.getBody());
            } else {
                holder.txtMessageSender.setVisibility(View.GONE);
            }
            /*if (userImages.size() > 0) {
                Picasso.with(context)
                        .load(userImages.get(0).get(Const.KEY_PROFILE_PIC))
                        .error(R.mipmap.ic_default_profile)
                        .noFade()
                        .into(holder.imgSender);

                if (!TextUtils.isEmpty(userImages.get(0).get(Const.KEY_NAME)))
                    holder.txtSenderName.setText(userImages.get(0).get(Const.KEY_NAME));
                else {
                    if (item.getId() != null)
                        holder.txtSenderName.setText(item.getId());
                }
            } else {

                if (item.getId() != null)
                    holder.txtSenderName.setText(item.getId());
            }*/

        } else {
            holder.card_view_Sender.setVisibility(View.GONE);
            holder.card_view_receiver.setVisibility(View.VISIBLE);

            Collection<QBAttachment> attachments = item.getAttachments();
            if (attachments != null) {

                String fileId = null;
                for (QBAttachment qbAttachment : attachments) {
                    fileId = qbAttachment.getUrl();
                }

                /*if (Utility.isEmptyString(fileId)) {
                    holder.frmReceiverImage.setVisibility(View.GONE);
                } else {
                    holder.frmReceiverImage.setVisibility(View.VISIBLE);
                    holder.imgProgressReceiver.setVisibility(View.VISIBLE);

                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.imgProgressReceiver);
                    Glide.with(context).load(R.raw.loader).into(imageViewTarget);

                }*/

                /*final String finalFileId = fileId;
                holder.imgFromReceiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialogManager.alertShowImage(context, finalFileId);
                    }
                });
*/

  /*              Picasso.with(context)
                        .load(fileId)
                        .error(R.mipmap.ic_default_profile)
                        .noFade()
                        .into(holder.imgFromReceiver, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.imgProgressReceiver.setVisibility(View.GONE);
                                holder.frmReceiverImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                holder.imgProgressReceiver.setVisibility(View.GONE);
                            }
                        });
  */          } else {

//                holder.frmReceiverImage.setVisibility(View.GONE);

            }


            if (!Utility.isEmptyString(item.getBody())) {
                holder.txtMessageReceiver.setText(item.getBody());
            } else {
                holder.txtMessageReceiver.setVisibility(View.GONE);
            }


           /* if (userImages.size() > 1) {
                Picasso.with(context)
                        .load(userImages.get(1).get(Const.KEY_PROFILE_PIC))
                        .error(R.mipmap.ic_default_profile)
                        .noFade()
                        .into(holder.imgReceiver);

                if (!TextUtils.isEmpty(userImages.get(1).get(Const.KEY_NAME)))
                    holder.txtReceiverName.setText(userImages.get(1).get(Const.KEY_NAME));
                else if (item.getId() != null)
                    holder.txtReceiverName.setText(item.getId());

            } else {
                if (item.getId() != null)
                    holder.txtReceiverName.setText(item.getId());
            }*/


        }


    }

    public void updateList(ArrayList<QBChatMessage> listChat) {
        this.listChat = listChat;
        notifyDataSetChanged();
    }

    public void updateList(QBChatMessage msgDetail) {

        int previousChat = listChat.size();
        listChat.add(msgDetail);
        int newChat = listChat.size();

        if (newChat > previousChat) {

            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    // Any UI task, example
                    notifyDataSetChanged();
                    ((ChatActivity) context).scrollToLast();
                }
            };
            handler.sendEmptyMessage(1);

        }
    }

    public void updateUserDetail(ArrayList<HashMap<String, String>> userImages) {
        this.userImages = userImages;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtMessageReceiver)
        TextView txtMessageReceiver;

        @BindView(R.id.card_view_receiver)
        CardView card_view_receiver;

        @BindView(R.id.card_view_Sender)
        CardView card_view_Sender;

        @BindView(R.id.txtMessageSender)
        TextView txtMessageSender;



/*
        @InjectView(R.id.imgSender)
        ImageView imgSender;
*/


/*
        @InjectView(R.id.imgReceiver)
        ImageView imgReceiver;
*/

/*
        @InjectView(R.id.txtSenderName)
        TextView txtSenderName;
*/

/*

        @InjectView(R.id.imgFromReceiver)
        ImageView imgFromReceiver;*/

        /*@InjectView(R.id.imgFromSender)
        ImageView imgFromSender;
*/
  /*      @InjectView(R.id.frmReceiverImage)
        FrameLayout frmReceiverImage;
*/
  /*      @InjectView(R.id.frmSenderImage)
        FrameLayout frmSenderImage;
*/
  /*      @InjectView(R.id.imgProgressReceiver)
        ImageView imgProgressReceiver;
*/
  /*      @InjectView(R.id.imgProgressSender)
        ImageView imgProgressSender;
*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

