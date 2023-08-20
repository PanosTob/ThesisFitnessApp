package gr.dipae.thesisfitnessapp.usecase.user.challenges

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import javax.inject.Inject

class GetUserSportChallengesBySportIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(sportId: String): List<UserSportChallenge> {
        return userRepository.getUserSportChallengesBySportId(sportId)
    }
}