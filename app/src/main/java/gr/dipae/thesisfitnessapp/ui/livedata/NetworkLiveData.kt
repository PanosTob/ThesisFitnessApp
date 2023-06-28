package gr.dipae.thesisfitnessapp.ui.livedata

import gr.dipae.thesisfitnessapp.ui.livedata.SingleLiveEvent
import gr.dipae.thesisfitnessapp.util.connectivity.ConnectivityStatus
import gr.dipae.thesisfitnessapp.util.connectivity.OnConnectivityChangeCallback
import gr.dipae.thesisfitnessapp.util.connectivity.network.ConnectivityMonitor
import javax.inject.Inject

class NetworkLiveData @Inject constructor(
    private val connectivityMonitor: ConnectivityMonitor
) : SingleLiveEvent<ConnectivityStatus>() {

    private val connectivityCallback = object : OnConnectivityChangeCallback {
        override fun onConnected() {
            postValue(ConnectivityStatus.CONNECTED)
        }

        override fun onDisconnected() {
            postValue(ConnectivityStatus.DISCONNECTED)
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityMonitor.subscribeOnChanges(connectivityCallback)
    }

    override fun onInactive() {
        connectivityMonitor.unsubscribe()
        super.onInactive()
    }
}