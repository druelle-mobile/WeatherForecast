package ovh.geoffrey_druelle.weatherforecast.utils.helper

import android.content.Context
import android.net.ConnectivityManager

object ConnectivityHelper {
    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected: Boolean
        val activeNetwork = connectivityManager.activeNetworkInfo
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return isConnected
    }
}