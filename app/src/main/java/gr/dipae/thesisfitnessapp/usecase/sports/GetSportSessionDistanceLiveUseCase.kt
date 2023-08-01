package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSportSessionDistanceLiveUseCase @Inject constructor(
    private val repository: SportsRepository
) : UseCase {
    operator fun invoke(): StateFlow<Long> {
        return repository.getSportSessionDistanceLive()
    }
}