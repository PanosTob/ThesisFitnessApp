package gr.dipae.thesisfitnessapp.usecase.user.challenges

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import javax.inject.Inject

class GetUserSportChallengesUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(): List<UserSportChallenge> {
        return userRepository.getUserSportChallenges()
    }
}