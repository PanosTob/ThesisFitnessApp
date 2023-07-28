package gr.dipae.thesisfitnessapp.data.sport.broadcast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StopWatchBroadcast {

    private var _stopWatchMillisPassed: MutableStateFlow<Long> = MutableStateFlow(0)
    val stopWatchMillisPassed = _stopWatchMillisPassed.asStateFlow()

    suspend fun refreshStopWatchMillisPassed(millis: Long) {
        _stopWatchMillisPassed.emit(millis)
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