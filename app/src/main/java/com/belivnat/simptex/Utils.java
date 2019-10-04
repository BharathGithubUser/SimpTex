package com.belivnat.simptex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

class Utils {
     static boolean isConnectedToInternet(Context context) {
         ConnectivityManager cm =
                 (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
         return activeNetwork != null &&
                 activeNetwork.isConnectedOrConnecting();
     }
     static void makeToast(Context context, String message) {
         Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
     }
}
