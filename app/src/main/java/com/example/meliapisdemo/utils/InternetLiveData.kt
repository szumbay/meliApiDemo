package com.example.meliapisdemo.utils

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.LiveData


class InternetLiveData(val context: Context) : LiveData<Boolean>() {

    private var lastInfo : NetworkInfo.State? = null

    val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                val activeNetwork = intent.extras!!.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
                val isConnected = activeNetwork.isConnectedOrConnecting
                val state = activeNetwork.state
                if(lastInfo == null || state != lastInfo){
                    lastInfo = state
                    if (isConnected) {
                        postValue(true)
                    } else {
                        postValue(false)
                    }
                }
            }
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }
}

