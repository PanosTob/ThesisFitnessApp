package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class StartUserLocationUpdatesUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) : UseCase {

    operator fun invoke(locationUpdateIntervalMillis: Long = 5000) {
        sportsRepository.startUserLocationUpdates(locationUpdateIntervalMillis)
    }
}