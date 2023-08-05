package gr.dipae.thesisfitnessapp.data.sport.broadcast

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@ViewModelScoped
class SportSessionBreakTimer @Inject constructor() {

    private var breakDuration: Duration = Duration.ZERO
    private lateinit var breakTimer: Timer

    private val _breakTimerMillisPassed: MutableStateFlow<Long> = MutableStateFlow(0)
    val breakTimerMillisPassed = _breakTimerMillisPassed.asStateFlow()

    fun startBreakTimer() {
        breakTimer = fixedRateTimer(period = 1000L) {
            breakDuration = breakDuration.plus(1000.milliseconds)
            _breakTimerMillisPassed.update { breakDuration.inWholeMilliseconds }
        }
    }

    fun pauseBreakTimer() {
        if (this::breakTimer.isInitialized) {
            breakTimer.cancel()
        }
    }

    fun clear() {
        pauseBreakTimer()
        _breakTimerMillisPassed.update { 0 }
    }
}