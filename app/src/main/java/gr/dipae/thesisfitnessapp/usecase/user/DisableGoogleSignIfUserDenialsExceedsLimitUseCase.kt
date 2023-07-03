package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class DisableGoogleSignIfUserDenialsExceedsLimitUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    operator fun invoke(limit: Int = 3) {
        if (repository.getGoogleSignInDenialCount() >= limit) {
            repository.setGoogleSignInBlockedTime()
        }
    }
}