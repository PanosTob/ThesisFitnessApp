package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class StopSportSessionBreakTimerUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    operator fun invoke() {
        repository.clearSportSessionBreakTimer()
    }
}