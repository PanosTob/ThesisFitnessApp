package gr.dipae.thesisfitnessapp.usecase.sports

import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class SetSportSessionUseCase @Inject constructor(
    private val repository: SportsRepository,
    private val getSportSessionDurationLiveUseCase: GetSportSessionDurationLiveUseCase,
    private val getSportSessionDistanceLiveUseCase: GetSportSessionDistanceLiveUseCase
) : UseCase {
    suspend operator fun invoke(sportId: String, goalParameter: SportParameter?): SportSessionSaveResult {
        try {
            goalParameter ?: return SportSessionSaveResult.Failure()

            val duration = getSportSessionDurationLiveUseCase().value
            val distance = getSportSessionDistanceLiveUseCase().value
            repository.setSportSession(
                sportId = sportId,
                duration = duration,
                distance = distance,
                goalParameter = goalParameter
            )

            return SportSessionSaveResult.Success
        } catch (ex: Exception) {
            Timber.tag(SetSportSessionUseCase::class.simpleName.toString()).e(ex)
            return SportSessionSaveResult.Failure(ex)
        }
    }
}