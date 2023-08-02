package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class SetSportSessionDistanceUseCase @Inject constructor(
    private val sportsRepository: SportsRepository
) : UseCase {
    suspend operator fun invoke(distance: Double) {
        sportsRepository.setSportSessionDistance(distance)
    }
}