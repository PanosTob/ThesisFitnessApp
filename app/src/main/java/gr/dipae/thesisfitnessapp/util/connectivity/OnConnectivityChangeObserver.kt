package gr.dipae.thesisfitnessapp.util.connectivity

import gr.dipae.thesisfitnessapp.util.connectivity.OnConnectivityChangeCallback

interface OnConnectivityChangeObserver {

    fun subscribeOnChanges(callback: OnConnectivityChangeCallback)

    fun unsubscribe()
}