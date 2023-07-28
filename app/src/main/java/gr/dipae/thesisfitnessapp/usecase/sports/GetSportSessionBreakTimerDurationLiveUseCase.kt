package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSportSessionBreakTimerDurationLiveUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    operator fun invoke(): Flow<Long> {
        return repository.getSportSessionBreakTimerLive()
    }
}