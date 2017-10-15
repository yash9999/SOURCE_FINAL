package com.example.yogeshgarg.source.mvp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.AttrRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.Const;
import com.example.yogeshgarg.source.common.session.UserSession;
import com.example.yogeshgarg.source.common.utils.AlertDialogManager;
import com.example.yogeshgarg.source.common.utils.Utility;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.listeners.QBMessageSentListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;


public class ChatActivity extends BaseActivity {

    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @BindView(R.id.edtChatMessage)
    EditText edtChatMessage;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.bottom_sheet)
    View bottom_sheet;

    @BindView(R.id.touch_outside)
    View touch_outside;


    private BottomSheetBehavior mBottomSheetBehavior;

    LinearLayoutManager linearLayoutManager;

    Uri imageToUploadUri;


    public ChatActivity() {
        // Required empty public constructor
    }

    QBChatService chatService;

    private QBPrivateChatManagerListener privateChatManagerListener;
    QBMessageListener<QBPrivateChat> privateChatMessageListener;
    QBMessageSentListener<QBPrivateChat> privateChatMessageSentListener;
    QBPrivateChatManager privateChatManager;
    QBDialog qbDialog;

    String userId = "";
    String dialogId = "";
    public Integer opponentId = 0;
    String opponentName = "";


    public ArrayList<QBChatMessage> chatMessages = new ArrayList<>();
    public ArrayList<HashMap<String, String>> userImages = new ArrayList<>();
    RecyclerChatAdapter chatAdapter;

    UserSession userSession;

    private static final int REQ_CODE_PICK_IMAGE = 101;
    private static final int CAMERA_PIC_REQUEST = 102;
    private Dialog selectImageDialog;
    QBUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        userSession = new UserSession(this);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                    touch_outside.setVisibility(View.GONE);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    touch_outside.setVisibility(View.VISIBLE);
                } else {
                    touch_outside.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });


        manageToolbar();

        manageRecyclerView();

        dialogId = "59d921dca28f9a0ab0ce1407";
        opponentId = getIntent().getIntExtra(Const.KEY_OPPONENT_ID, 0);
        opponentName = getIntent().getStringExtra(Const.KEY_NAME);

        //txtToolbarTitle.setText(opponentName);

        privateChatMessageSentListener = new QBMessageSentListener<QBPrivateChat>() {
            @Override
            public void processMessageSent(QBPrivateChat qbChat, QBChatMessage qbChatMessage) {
                chatAdapter.updateList(qbChatMessage);
            }

            @Override
            public void processMessageFailed(QBPrivateChat qbChat, QBChatMessage qbChatMessage) {

            }
        };


        privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
            @Override
            public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {
                Log.e("Chat Message", "" + chatMessage.getBody());
                chatAdapter.updateList(chatMessage);

                // chatMessage.getBody();


               /* requestBuilder.setSkip(skipRecords = 0);
                if (isActivityForeground) {
                    loadDialogsFromQbInUiThread(true);
                }*/
            }


            @Override
            public void processError(QBPrivateChat privateChat, QBChatException error, QBChatMessage originMessage) {
                error.printStackTrace();
                Progress.stop();
            }
        };

        privateChatManagerListener = new QBPrivateChatManagerListener() {
            @Override
            public void chatCreated(QBPrivateChat qbPrivateChat, boolean createdLocally) {
                if (!createdLocally) {
                    Progress.stop();
                    qbPrivateChat.addMessageListener(privateChatMessageListener);
                    qbPrivateChat.addMessageSentListener(privateChatMessageSentListener);

                }
            }
        };


        handleChatQuickblox();


        //  callChatRefreshHandler();

    }

    private void manageToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

    }

    private void manageRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    //Scroll to last
    public void scrollToLast() {
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
        // Log.d("getChildCount", "" + chatAdapter.getItemCount());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//
//        MenuItem playMenu = menu.findItem(R.id.action_chat);
//        playMenu.setIcon(R.drawable.invisible_menu_item);
//        return true;
//    }

    @OnClick(R.id.touch_outside)
    public void touch_outside_click() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @OnClick(R.id.linLayCamera)
    public void openCamera() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }


        int permissionCheckCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        int permissionCheckRead = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((permissionCheckCamera == -1) || (permissionCheckRead == -1) || (permissionCheckWrite == -1)) {

                if (!Settings.System.canWrite(this)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 2909);
                } else {
                    captureImage();
                }
            } else {
                captureImage();
            }
        } else {
            captureImage();
        }

    }

    @OnClick(R.id.linLayGallery)
    public void openGallery() {

        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    }

    @OnClick(R.id.imgArrow)
    public void imgArrowClick() {
        String msg = edtChatMessage.getText().toString();
        edtChatMessage.setText("");
        if (!TextUtils.isEmpty(msg))
            sendMessage(privateChatManager, msg);

    }


    @OnClick(R.id.imgCamera)
    public void imgCameraClick() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBottomSheetBehavior.setPeekHeight(0);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == RESULT_OK) {

                if (data != null && data.getData() != null) {

                    Uri selectedImageUri = data.getData();
                    String tempPath = getRealPathFromURI(this, selectedImageUri);
                    sendImageInChat(tempPath);
                }

            } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
                onCaptureImageResult(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onCaptureImageResult(Intent data) {

        if (imageToUploadUri != null) {
            Uri selectedImage = imageToUploadUri;
            sendImageInChat(selectedImage.getPath());
        }

    }


    //getting path of device image
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2909: {

                int permissionCheckCamera = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA);

                int permissionCheckRead = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);


                if ((permissionCheckCamera == 0) && (permissionCheckRead == 0) && (permissionCheckWrite == 0)) {
                    openCamera();
                } else {

                    // SnackHelper.showMessage("Please provide security permission from aap setting.", rootView);
                }

                return;
            }


        }
    }


    private void captureImage() {
        //Create file name

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageToUploadUri = Uri.fromFile(f);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);

    }

    private void handleChatQuickblox() {

        Progress.start(this);

        if (QBChatService.getInstance().isLoggedIn()) {
            chatService = QBChatService.getInstance();
            registerQbChatListeners();

            Log.d("isLoggedIn", "true");
        } else {
            Log.d("isLoggedIn", "false");
            //initialise chat service
            QBChatService.setDebugEnabled(true); // enable chat logging
            QBChatService.setDefaultAutoSendPresenceInterval(120); //enable sending online status every 60 sec to keep connection alive

            //configure chat socket
            QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
            chatServiceConfigurationBuilder.setSocketTimeout(180); //Sets chat socket's read timeout in seconds
            chatServiceConfigurationBuilder.setKeepAlive(true); //Sets connection socket's keepAlive option.
            chatServiceConfigurationBuilder.setUseTls(true); //Sets the TLS security mode used when making the connection. By default TLS is disabled.
            QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);

            chatService = QBChatService.getInstance();

            user = new QBUser("Garg", Const.AC_PWD);
            QBAuth.createSession(user, new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(QBSession session, Bundle params) {
                    // success, login to chat
                    Log.d("createSession", "true");
                    user.setId(session.getUserId());
                    userId = String.valueOf(session.getId());

                    loginToQuikBlox();
                }

                @Override
                public void onError(QBResponseException errors) {
                    Progress.stop();
                    Log.d("createSession", "onError" + errors.getMessage());
                    errors.printStackTrace();
                }
            });


            ConnectionListener connectionListener = new ConnectionListener() {
                @Override
                public void connected(XMPPConnection connection) {
                    loginToQuikBlox();
                    Log.d("Inside", "connection");
                }

                @Override
                public void authenticated(XMPPConnection xmppConnection, boolean b) {
                    Log.d("Inside", "authenticated");
                    registerQbChatListeners();
                }


                @Override
                public void connectionClosed() {
                    Log.d("Inside", "connectionClosed");
                }

                @Override
                public void connectionClosedOnError(Exception e) {
                    Log.d("Inside", "connectionClosedOnError" + e.getMessage());
                }

                @Override
                public void reconnectingIn(int seconds) {
                    Log.d("Inside", "reconnectingIn seconds" + seconds);
                }

                @Override
                public void reconnectionSuccessful() {
                    Log.d("reconnectionSuccessful", "true");
                }

                @Override
                public void reconnectionFailed(Exception e) {
                    Log.d("reconnectionFailed", "failed");
                }
            };


            QBChatService.getInstance().addConnectionListener(connectionListener);
        }


    }

    private void loginToQuikBlox() {
        if (!chatService.isLoggedIn())
            chatService.login(user, new QBEntityCallback() {

                @Override
                public void onSuccess(Object o, Bundle bundle) {
                    Log.d("login", "onSuccess");
                }

                @Override
                public void onError(QBResponseException errors) {
                    Progress.stop();
                    Log.d("login", "errors" + errors.getMessage());
                    errors.printStackTrace();
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        boolean isLoggedIn = chatService.isLoggedIn();
        if (!isLoggedIn) {
            Log.e("onDestroy isLoggedIn", "false");
            return;
        }

        Log.e("onDestroy isLoggedIn", "true");
        chatService.logout(new QBEntityCallback() {


            @Override
            public void onSuccess(Object o, Bundle bundle) {
                Log.e("logout onSuccess", "true");
                chatService.destroy();
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.e("logout onError", "errors" + errors.getMessage());
                Progress.stop();
            }
        });
    }


    public void chatInPrivateChat(QBPrivateChatManager privateChatManager, final boolean isRefresh) {

        //57c410a5a28f9a6e21000010
        qbDialog = new QBDialog(dialogId);

        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);

        QBChatService.getDialogMessages(qbDialog, requestBuilder, new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> messages, Bundle args) {
                Progress.stop();

                Log.d("getDialogMessages", "onSuccess"+messages.size());
                chatMessages = messages;
                if (!isRefresh) {

                    Log.d("isRefresh", "false");
                    chatAdapter = new RecyclerChatAdapter(ChatActivity.this, chatMessages, userImages, Integer.parseInt(userSession.getCurrentUserOpponentId()));
                    recyclerView.setAdapter(chatAdapter);

                    ArrayList<Integer> listQuickBloxId = new ArrayList<Integer>();
                    listQuickBloxId.add(opponentId);

                    for (int i = 0; i < chatMessages.size(); i++) {
                        if ((chatMessages.get(i).getSenderId() != opponentId)) {
                            listQuickBloxId.add(chatMessages.get(i).getSenderId());
                            break;

                        }
                    }

                    //if (listQuickBloxId.size() > 0)
                    //getImageOfQuickbloxUser(listQuickBloxId);

                } else {
                    Log.d("isRefresh", "true");
                    //chatAdapter.updateList(chatMessages);
                }
                scrollToLast();

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d("getDialogMessages", "onError" + errors.getMessage());
                Progress.stop();
                errors.printStackTrace();
            }
        });


    }

    public void sendMessage(final QBPrivateChatManager privateChatManager, String msg) {

        try {


            QBChatMessage chatMessage = new QBChatMessage();
            chatMessage.setBody(msg);
            chatMessage.setProperty("save_to_history", "1"); // Save a message to history
            chatMessage.setSenderId(Integer.parseInt(userSession.getCurrentUserOpponentId()));
            //chatMessage.setId();

            if (chatAdapter != null) {
                chatAdapter.updateList(chatMessage);
                //scrollToLast();
            } else {
                chatAdapter = new RecyclerChatAdapter(ChatActivity.this, chatMessages, userImages, Integer.parseInt(userSession.getCurrentUserOpponentId()));
                recyclerView.setAdapter(chatAdapter);
                chatAdapter.updateList(chatMessage);
                //scrollToLast();
            }


            try {
                QBPrivateChat privateChat = privateChatManager.getChat(opponentId);
                if (privateChat == null) {
                    privateChat = privateChatManager.createChat(opponentId, privateChatMessageListener);
                }
                privateChat.sendMessage(chatMessage);

            } catch (NullPointerException e) {

                e.printStackTrace();
                //SnackHelper.showMessage("Connection lost, try again after some time, thank you.", coordinateLayout);
                // AlertDialogManager.sAlertFinish(this, "Connection lost, try again after some time, thank you.");
            }

            Utility.hideKeyboardIfOpen(ChatActivity.this);
        } catch (SmackException.NotConnectedException e) {

        }


    }

    public void sendImageMessage(final QBPrivateChatManager privateChatManager, QBFile someFile) {

        try {

            String fileId = "" + someFile.getId();

            QBAttachment attachment = new QBAttachment("photo");
            attachment.setId(fileId);
            attachment.setUrl(someFile.getPublicUrl());


            QBChatMessage chatMessage = new QBChatMessage();
            chatMessage.addAttachment(attachment);

            chatMessage.setProperty("save_to_history", "1"); // Save a message to history
            chatMessage.setSenderId(Integer.parseInt(userSession.getCurrentUserOpponentId()));


            chatAdapter.updateList(chatMessage);
            // scrollToLast();
            QBPrivateChat privateChat = privateChatManager.getChat(opponentId);
            if (privateChat == null) {
                privateChat = privateChatManager.createChat(opponentId, privateChatMessageListener);
            }
            privateChat.sendMessage(chatMessage);

            Utility.hideKeyboardIfOpen(ChatActivity.this);
        } catch (SmackException.NotConnectedException e) {

        }


    }

    private void registerQbChatListeners() {
        privateChatManager = QBChatService.getInstance().getPrivateChatManager();
        //QBGroupChatManager groupChatManager = QBChatService.getInstance().getGroupChatManager();
        if (privateChatManager != null) {
            privateChatManager.addPrivateChatManagerListener(privateChatManagerListener);


            new CreateDialog().execute(privateChatManager);

        }
    }


    private class CreateDialog extends AsyncTask<QBPrivateChatManager, String, QBPrivateChatManager> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected QBPrivateChatManager doInBackground(QBPrivateChatManager... qbPrivateChatManagers) {


            return qbPrivateChatManagers[0];
        }

        @Override
        protected void onPostExecute(final QBPrivateChatManager s) {
            super.onPostExecute(s);

            s.createDialog(opponentId, new QBEntityCallback<QBDialog>() {
                @Override
                public void onSuccess(QBDialog dialog, Bundle args) {
                    Log.e("CreateDialog", "onSuccess");
                    dialogId = dialog.getDialogId();
                    chatInPrivateChat(s, false);
                }

                @Override
                public void onError(QBResponseException errors) {
                    Log.d("CreateDialog", "onError " + errors.getMessage());
                    //Progress.stop();
                    AlertDialogManager.sAlertFinish(ChatActivity.this, getString(R.string.chat_not_availble));
                    errors.printStackTrace();
                }
            });
        }
    }

    private void getImageOfQuickbloxUser(final ArrayList<Integer> dialogs) {

        Progress.start(this);

        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            if (Integer.parseInt(userSession.getQuikbloxId()) > 0)
                jsonArray.put(new JSONObject().put(Const.KEY_QUICK_BLOCK_ID, userSession.getQuikbloxId()));
            for (int i = 0; i < dialogs.size(); i++) {
                if (Integer.parseInt(userSession.getQuikbloxId()) != dialogs.get(i))
                    jsonArray.put(new JSONObject().put(Const.KEY_QUICK_BLOCK_ID, dialogs.get(i)));
            }
            jsonObject.put("ids", jsonArray);
            jsonObject.put(Const.KEY_DEVICE_ID, Utility.getDeviceId(this));
            Log.d("json", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<UserImage> callLogin;

        callLogin = ApiAdapter.getApiService().getImageOfQuickbloxUser("application/json", "no-cache", userSession.getUserToken(), body);

        callLogin.enqueue(new Callback<UserImage>() {
            @Override
            public void onResponse(Call<UserImage> call, Response<UserImage> response) {

                Progress.stop();
                try {

                    Log.d("response", response.body() + "");
                    if (response.code() == 200) {

                        ArrayList<UserImage.Datum> listImages = response.body().getData();

                        for (int i = 0; i < listImages.size(); i++) {
                            if (!TextUtils.isEmpty(listImages.get(i).getImage())) {
                                HashMap<String, String> userDetail = new HashMap<String, String>();
                                userDetail.put(Const.KEY_USER_NAME, listImages.get(i).getName());
                                userDetail.put(Const.KEY_PROFILE_PIC, listImages.get(i).getImage());
                                userImages.add(userDetail);
                            }

                        }

                        chatAdapter.updateUserDetail(userImages);


                    } else {

                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();


                }
            }

            @Override
            public void onFailure(Call<UserImage> call, Throwable t) {
                Progress.stop();
                // view.getUsersUnsuccessful(context.getString(R.string.server_error));
            }

        });*/
    }


    public void sendImageInChat(String filePath) {
        File filePhoto = new File(filePath);
        Boolean fileIsPublic = true;
        QBContent.uploadFileTask(filePhoto, fileIsPublic, null, new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile file, Bundle params) {
                sendImageMessage(privateChatManager, file);
                // qbDialog.setPhoto(file.getId().toString());
            }

            @Override
            public void onError(QBResponseException errors) {
                // error
            }
        });
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray a = this.getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }
}

