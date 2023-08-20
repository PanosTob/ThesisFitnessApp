package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import javax.inject.Inject

class IsSportParameterAchievedUseCase @Inject constructor() {

    operator fun invoke(goalParameter: SportParameter?, distance: Long, duration: Long): Boolean {
        return when (goalParameter?.type) {
            is SportParameterType.Distance -> distance <= goalParameter.value
            is SportParameterType.Duration -> duration >= goalParameter.value
            SportParameterType.Unknown -> false
            else -> false
        }
    }

}