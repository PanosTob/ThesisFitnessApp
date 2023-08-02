package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class PauseSportSessionBreakTimerUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    operator fun invoke() {
        repository.pauseSportSessionBreakTimer()
    }
}