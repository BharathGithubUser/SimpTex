package com.belivnat.simptex.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Utils {
     public static boolean isConnectedToInternet(Context context) {
         ConnectivityManager cm =
                 (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
         return activeNetwork != null &&
                 activeNetwork.isConnectedOrConnecting();
     }
     public static void makeToast(Context context, String message) {
         Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
     }
}
