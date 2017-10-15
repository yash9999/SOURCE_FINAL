package com.example.yogeshgarg.source.common.quickblox.qb;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;


import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.SourceApp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

public class VerboseQbChatConnectionListener implements ConnectionListener {
    private static final String TAG = VerboseQbChatConnectionListener.class.getSimpleName();
    private View rootView;
    private Snackbar snackbar;

    public VerboseQbChatConnectionListener(View rootView) {
        this.rootView = rootView;
    }

    @Override
    public void connected(XMPPConnection connection) {
        Log.i(TAG, "connected()");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean authenticated) {
        Log.i(TAG, "authenticated()");
    }

    @Override
    public void connectionClosed() {
        Log.i(TAG, "connectionClosed()");
    }

    @Override
    public void connectionClosedOnError(final Exception e) {
        Log.i(TAG, "connectionClosedOnError(): " + e.getLocalizedMessage());
        snackbar = Snackbar.make(rootView, SourceApp.getInstance().getString(R.string.internet_connectivity_message), Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void reconnectingIn(final int seconds) {
        if (seconds % 5 == 0 && seconds != 0) {
            Log.i(TAG, "reconnectingIn(): " + seconds);
           /* snackbar = Snackbar.make(rootView, MyApplication.getInstance().getString(R.string.reconnect_alert, seconds), Snackbar.LENGTH_INDEFINITE);
            snackbar.show();*/
        }
    }

    @Override
    public void reconnectionSuccessful() {
        Log.i(TAG, "reconnectionSuccessful()");
        snackbar.dismiss();
    }

    @Override
    public void reconnectionFailed(final Exception error) {
        Log.i(TAG, "reconnectionFailed(): " + error.getLocalizedMessage());
    }
}
