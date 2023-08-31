package gr.dipae.thesisfitnessapp.data.diet.broadcast

import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class DailyDietBroadcast {

    private var _dailyDietState: MutableStateFlow<DailyDiet?> = MutableStateFlow(null)
    val dailyDietState = _dailyDietState.asStateFlow()

    suspend fun refreshDailyDietState(dailyDiet: DailyDiet?) {
        Timber.tag(DailyDietBroadcast::class.simpleName.toString()).e("Emitting daily diet state $dailyDiet")
        _dailyDietState.emit(dailyDiet)
    }

    fun clear() {
        _dailyDietState.value = null
    }

    companion object {

        @Volatile
        private var INSTANCE: DailyDietBroadcast? = null

        fun getInstance(): DailyDietBroadcast {
            return INSTANCE ?: synchronized(this) {
                DailyDietBroadcast().also { INSTANCE = it }
            }
        }
    }
}