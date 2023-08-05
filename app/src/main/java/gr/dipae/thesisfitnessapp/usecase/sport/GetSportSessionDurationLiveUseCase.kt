package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSportSessionDurationLiveUseCase @Inject constructor(
    private val repository: SportSessionRepository
) : UseCase {
    operator fun invoke(): StateFlow<Long> {
        return repository.getSportSessionDurationLive()
    }
}