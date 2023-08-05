package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class SetSportSessionUseCase @Inject constructor(
    private val repository: SportSessionRepository,
    private val getSportSessionDurationLiveUseCase: GetSportSessionDurationLiveUseCase,
    private val getSportSessionDistanceLiveUseCase: GetSportSessionDistanceLiveUseCase,
    private val getSportSessionBreakTimerDurationLiveUseCase: GetSportSessionBreakTimerDurationLiveUseCase,
    private val isSportParameterAchievedUseCase: IsSportParameterAchievedUseCase
) : UseCase {
    suspend operator fun invoke(sportId: String, goalParameter: SportParameter?): SportSessionSaveResult {
        try {

            val duration = getSportSessionDurationLiveUseCase().value
            val distance = getSportSessionDistanceLiveUseCase().value
            val breakTime = getSportSessionBreakTimerDurationLiveUseCase().value
            repository.setSportSession(
                sportId = sportId,
                duration = duration,
                distance = distance,
                breakTime = breakTime,
                goalParameter = Pair(goalParameter, isSportParameterAchievedUseCase(goalParameter, distance, breakTime))
            )

            return SportSessionSaveResult.Success
        } catch (ex: Exception) {
            Timber.tag(SetSportSessionUseCase::class.simpleName.toString()).e(ex)
            return SportSessionSaveResult.Failure(ex)
        }
    }
}