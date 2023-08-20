package gr.dipae.thesisfitnessapp.data.sport.broadcast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SportSessionBroadcast {

    private val _sportDistance: MutableStateFlow<Long> = MutableStateFlow(0)
    val sportDistance = _sportDistance.asStateFlow()

    suspend fun refreshSportDistance(meters: Long) {
        _sportDistance.emit(meters)
    }

    fun clear() {
        _sportDistance.value = 0
    }

    companion object {

        @Volatile
        private var INSTANCE: SportSessionBroadcast? = null

        fun getInstance(): SportSessionBroadcast {
            return INSTANCE ?: synchronized(this) {
                SportSessionBroadcast().also { INSTANCE = it }
            }
        }
    }
}