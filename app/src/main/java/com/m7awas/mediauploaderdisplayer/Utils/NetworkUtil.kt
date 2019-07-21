package com.m7awas.mediauploaderdisplayer.Utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {

    companion object {
        fun isConnected(context: Context): Boolean {
            val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectionManager.activeNetworkInfo

            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}