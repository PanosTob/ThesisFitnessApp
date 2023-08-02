package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import javax.inject.Inject

class StopUserLocationUpdatesUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) {

    operator fun invoke() {
        sportsRepository.stopUserLocationUpdated()
    }
}