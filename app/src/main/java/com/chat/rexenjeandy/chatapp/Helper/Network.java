package com.chat.rexenjeandy.chatapp.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class Network {
    // sets the context in every pages
    public static Context clsContext = null;

    /**
     * check if it is connected to internet
     * @return boolean
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) clsContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
