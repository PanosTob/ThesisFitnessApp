package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class SetSportSessionDistanceUseCase @Inject constructor(
    private val sportsRepository: SportSessionRepository
) : UseCase {
    suspend operator fun invoke(distance: Long) {
        sportsRepository.setSportSessionDistance(distance)
    }
}