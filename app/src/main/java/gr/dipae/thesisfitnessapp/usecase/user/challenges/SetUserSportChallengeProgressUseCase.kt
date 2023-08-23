package gr.dipae.thesisfitnessapp.usecase.user.challenges

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class SetUserSportChallengeProgressUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(challengeId: String, progress: Long) {
        userRepository.setUserSportChallengeProgress(challengeId, progress)
    }
}