package com.anirudh.todo_fe.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

object NetworkHelper {

    fun isNetworkConnected(context: Context):Boolean{
        var result =false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                result = checkNetworkConnection(this,this.activeNetwork)
            }
            else{
                val network = this.allNetworks
                network.forEach {
                    if (checkNetworkConnection(this,it)){
                        result = true
                    }
                }
            }
        }
        return result
    }
    private fun checkNetworkConnection(cm:ConnectivityManager,network: Network?):Boolean{
        cm.getNetworkCapabilities(network)?.also {
            if(it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                return true
            }
        }
        return false
    }
}