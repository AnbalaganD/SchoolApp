package edu.schoolapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) SchoolApp.getAppContext().getSystemService(SchoolApp.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
