package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import javax.inject.Inject

class StopUserLocationUpdatesUseCase @Inject constructor(
    private val sportsRepository: SportSessionRepository
) {

    operator fun invoke() {
        sportsRepository.stopUserLocationUpdated()
    }
}