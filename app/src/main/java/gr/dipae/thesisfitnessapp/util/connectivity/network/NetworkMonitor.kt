package gr.dipae.thesisfitnessapp.util.connectivity.network

import android.net.ConnectivityManager
import android.net.Network
import gr.dipae.thesisfitnessapp.util.connectivity.OnConnectivityChangeCallback
import gr.dipae.thesisfitnessapp.util.connectivity.OnConnectivityChangeObserver

sealed class ConnectivityMonitor : OnConnectivityChangeObserver

class NougatConnectivityMonitor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityMonitor() {

    private var connectivityCallback: OnConnectivityChangeCallback? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectivityCallback?.onConnected()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            connectivityCallback?.onDisconnected()
        }
    }

    override fun subscribeOnChanges(callback: OnConnectivityChangeCallback) {
        connectivityCallback = callback
        connectivityCallback?.onDisconnected()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        connectivityCallback = null
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}