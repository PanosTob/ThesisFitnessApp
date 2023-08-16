package gr.dipae.thesisfitnessapp.data.user.broadcast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class AccelerometerBroadcast {

    private var _isRunning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    suspend fun refreshIsRunningStatus(running: Boolean) {
        Timber.tag(AccelerometerBroadcast::class.simpleName.toString()).e("Emitting isRunning Status $running")
        _isRunning.emit(running)
    }

    fun clear() {
        _isRunning.value = false
    }

    companion object {

        @Volatile
        private var INSTANCE: AccelerometerBroadcast? = null

        fun getInstance(): AccelerometerBroadcast {
            return INSTANCE ?: synchronized(this) {
                AccelerometerBroadcast().also { INSTANCE = it }
            }
        }
    }
}