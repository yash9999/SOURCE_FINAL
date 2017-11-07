package com.example.yogeshgarg.source.mvp.chatting;

import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.CircleTransform;
import com.example.yogeshgarg.source.common.helper.FontHelper;
import com.example.yogeshgarg.source.common.requestResponse.ConstIntent;
import com.example.yogeshgarg.source.common.utility.SnackNotify;
import com.example.yogeshgarg.source.mvp.product_list.product_list_brand.ProductListBrandAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatActivity extends AppCompatActivity implements ChattingView {

    String username;
    String opponentImage;
    String userId;

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @BindView(R.id.imgViewBack)
    ImageView imgViewBack;

    @BindView(R.id.imgViewProfile)
    ImageView imgViewProfile;

    @BindView(R.id.txtViewOpponentName)
    TextView txtViewOpponentName;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.edtChatMessage)
    EditText edtChatMessage;

    @BindView(R.id.imgArrow)
    ImageView imgArrow;

    String message;
    ChattingPresenterImpl chattingPresenterImpl;
    ArrayList<ChattingModel.Result> results;

    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setFont();
        chattingPresenterImpl = new ChattingPresenterImpl(this, this);
        if (getIntent() != null) {
            username = getIntent().getStringExtra("UserName");
            if (!TextUtils.isEmpty(username)) {
                txtViewOpponentName.setText(username);
            }
            opponentImage = getIntent().getStringExtra("OpponentProfile");
            if (!TextUtils.isEmpty(opponentImage)) {
                Picasso.with(this).load(ConstIntent.PREFIX_URL_OF_IMAGE + opponentImage).transform(new CircleTransform())
                        .error(R.mipmap.ic_user).into(imgViewProfile);
            }

            userId = getIntent().getStringExtra("UserId");
            if (!TextUtils.isEmpty(userId)) {
                chattingPresenterImpl.callingConversationApi(userId);
            }
        }

        mHandler = new Handler();
        startRepeatingTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                chattingPresenterImpl.callingConversationApi(userId);
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void setFont() {
        FontHelper.applyFont(this, txtViewOpponentName, FontHelper.FontType.FONT_Normal);
        FontHelper.applyFont(this, edtChatMessage, FontHelper.FontType.FONT_Normal);
    }

    @OnClick(R.id.imgViewBack)
    public void setImgViewBack() {
        finish();
    }

    @OnClick(R.id.imgArrow)
    public void imgArrowClick() {
        message = edtChatMessage.getText().toString();
        chattingPresenterImpl.sendMessage(message, userId);
    }

    @Override
    public void onSuccessConversation(ArrayList<ChattingModel.Result> results) {
        this.results=results;
        if (results.size() > 0) {
            setLayoutManager();
        }
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.scrollToPosition(results.size()-1);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        setAdapter();
    }

    private void setAdapter() {
        ChatAdapter chatAdapter= new ChatAdapter(this,results);
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    public void onUnsuccessConversation(String message) {
        SnackNotify.showMessage(message, coordinateLayout);
    }

    @Override
    public void onInternetError() {

    }

    @Override
    public void onSuccessSendMessage(SendingModel.Result result) {
        chattingPresenterImpl.callingConversationApi(userId);
        edtChatMessage.setText("");
    }

    @Override
    public void onUnsuccessSendMessage(String message) {

    }

    @Override
    public void onInternetErrorSendMessage() {

    }

    @Override
    public void onSuccessReceivedMessage(ArrayList<ReceivedModel.Result> results) {

    }

    @Override
    public void onUnsuccessReceivedMessage(String message) {

    }

    @Override
    public void onInternetErrorReceivedMessage() {

    }
}
