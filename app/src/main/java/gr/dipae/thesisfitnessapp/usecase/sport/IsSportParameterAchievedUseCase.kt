package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import javax.inject.Inject

class IsSportParameterAchievedUseCase @Inject constructor() {

    operator fun invoke(goalParameter: SportParameter?, distance: Double, duration: Long): Boolean {
        return when (goalParameter?.type) {
            is SportParameterType.Distance -> (distance * 1000).toLong() <= goalParameter.value
            is SportParameterType.Duration -> convertMillisToMinutes(duration) >= goalParameter.value
            SportParameterType.Unknown -> false
            else -> false
        }
    }

    private fun convertMillisToMinutes(durationMillis: Long): Int {
        return ((durationMillis / (1000 * 60)).mod(60))
    }
}