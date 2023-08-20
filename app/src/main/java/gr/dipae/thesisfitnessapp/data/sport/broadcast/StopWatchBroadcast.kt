package gr.dipae.thesisfitnessapp.data.sport.broadcast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StopWatchBroadcast {

    private val _stopWatchMillisPassed: MutableStateFlow<Long> = MutableStateFlow(0)
    val stopWatchMillisPassed = _stopWatchMillisPassed.asStateFlow()

    fun refreshStopWatchMillisPassed(millis: Long) {
        _stopWatchMillisPassed.update { millis }
    }

    fun clear() {
        _stopWatchMillisPassed.value = 0
    }

    companion object {

        @Volatile
        private var INSTANCE: StopWatchBroadcast? = null

        fun getInstance(): StopWatchBroadcast {
            return INSTANCE ?: synchronized(this) {
                StopWatchBroadcast().also { INSTANCE = it }
            }
        }
    }
}