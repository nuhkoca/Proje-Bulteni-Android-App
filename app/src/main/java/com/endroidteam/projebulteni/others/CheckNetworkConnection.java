package com.endroidteam.projebulteni.others;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.endroidteam.projebulteni.R;

/**
 * Created by NuhKoca on 24.04.2016.
 */
public class CheckNetworkConnection {
    public static boolean CheckConn(final Context context, View view) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) {
            Snackbar mSnackbar = Snackbar.make(view, R.string.no_active_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.settings, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    });
            mSnackbar.setActionTextColor(ContextCompat.getColor(context, R.color.snackbar_text_color));

            mSnackbar.show();

            return false;
        }
        if (!mNetworkInfo.isConnected()) {
            Snackbar mSnackbar = Snackbar.make(view, R.string.no_active_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.settings, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    });
            mSnackbar.setActionTextColor(ContextCompat.getColor(context, R.color.snackbar_text_color));

            mSnackbar.show();
            return false;
        }
        if (!mNetworkInfo.isAvailable()) {
            Snackbar mSnackbar = Snackbar.make(view, R.string.no_active_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.settings, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    });
            mSnackbar.setActionTextColor(ContextCompat.getColor(context, R.color.snackbar_text_color));

            mSnackbar.show();
            return false;
        }

        return true;
    }
}
