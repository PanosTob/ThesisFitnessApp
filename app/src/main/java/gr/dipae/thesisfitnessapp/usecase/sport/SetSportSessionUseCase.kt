package gr.dipae.thesisfitnessapp.usecase.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.challenges.GetUserSportChallengesBySportIdUseCase
import gr.dipae.thesisfitnessapp.usecase.user.challenges.SetUserSportChallengeProgressUseCase
import gr.dipae.thesisfitnessapp.util.ext.toDoubleWithSpecificDecimals
import timber.log.Timber
import javax.inject.Inject

class SetSportSessionUseCase @Inject constructor(
    private val repository: SportSessionRepository,
    private val userRepository: UserRepository,
    private val isSportParameterAchievedUseCase: IsSportParameterAchievedUseCase,
    private val getUserSportChallengesBySportIdUseCase: GetUserSportChallengesBySportIdUseCase,
    private val setUserSportChallengeProgressUseCase: SetUserSportChallengeProgressUseCase
) : UseCase {
    suspend operator fun invoke(sportId: String, goalParameter: SportParameter?, duration: Long, distance: Long, breakTime: Long): SportSessionSaveResult {
        try {

            val durationMinutes = convertMillisToMinutes(duration)
            val breakMinutes = convertMillisToMinutes(breakTime)

            val userSportChallenges = getUserSportChallengesBySportIdUseCase(sportId)
            getChallengeByParameterType(userSportChallenges, SportParameterType.Duration)?.let { durationChallenge ->
                setUserSportChallengeProgressUseCase(
                    challengeId = durationChallenge.id,
                    progress = (durationChallenge.progress + (durationMinutes / durationChallenge.goal.value)).toDoubleWithSpecificDecimals(2)
                )
            }
            getChallengeByParameterType(userSportChallenges, SportParameterType.Distance)?.let { distanceChallenge ->
                setUserSportChallengeProgressUseCase(
                    challengeId = distanceChallenge.id,
                    progress = (distanceChallenge.progress + (distance / distanceChallenge.goal.value)).toDoubleWithSpecificDecimals(4)
                )
            }

            val todaySummary = userRepository.getDaySummary()
            repository.setSportSession(
                todaySummaryId = todaySummary?.id,
                sportId = sportId,
                duration = durationMinutes,
                distance = distance,
                breakTime = breakMinutes,
                goalParameter = Pair(goalParameter, isSportParameterAchievedUseCase(goalParameter, distance, durationMinutes))
            )

            return SportSessionSaveResult.Success
        } catch (ex: Exception) {
            Timber.tag(SetSportSessionUseCase::class.simpleName.toString()).e(ex)
            return SportSessionSaveResult.Failure(ex)
        }
    }

    private fun getChallengeByParameterType(userSportChallenges: List<UserSportChallenge>, parameterType: SportParameterType): UserSportChallenge? {
        return userSportChallenges.find { it.goal.type == parameterType }
    }

    private fun convertMillisToMinutes(durationMillis: Long): Long {
        return ((durationMillis / (1000 * 60)).mod(60)).toLong()
    }
}