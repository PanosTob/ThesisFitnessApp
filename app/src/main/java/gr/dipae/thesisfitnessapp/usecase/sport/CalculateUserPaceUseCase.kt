package gr.dipae.thesisfitnessapp.usecase.sport

import javax.inject.Inject

class CalculateUserPaceUseCase @Inject constructor() {
    operator fun invoke(timeTravellingInMillis: Long, distanceTravelledInKm: Double): Long {
        val minutesTravelling = ((timeTravellingInMillis / (1000 * 60f)).mod(60f))
        return (minutesTravelling / distanceTravelledInKm).toLong().coerceAtMost(120)
    }
}