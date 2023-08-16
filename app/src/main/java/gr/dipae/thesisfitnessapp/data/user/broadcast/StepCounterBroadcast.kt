package gr.dipae.thesisfitnessapp.data.user.broadcast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class StepCounterBroadcast {

    private var _stepCounterValue: MutableStateFlow<Long?> = MutableStateFlow(null)
    val stepCounterValue = _stepCounterValue.asStateFlow()

    suspend fun updateStepCounter(counter: Long) {
        Timber.tag(StepCounterBroadcast::class.simpleName.toString()).e("Emitting step counter value $counter")
        _stepCounterValue.emit(counter)
    }

    fun clear() {
        _stepCounterValue.value = null
    }

    companion object {

        @Volatile
        private var INSTANCE: StepCounterBroadcast? = null

        fun getInstance(): StepCounterBroadcast {
            return INSTANCE ?: synchronized(this) {
                StepCounterBroadcast().also { INSTANCE = it }
            }
        }
    }
}