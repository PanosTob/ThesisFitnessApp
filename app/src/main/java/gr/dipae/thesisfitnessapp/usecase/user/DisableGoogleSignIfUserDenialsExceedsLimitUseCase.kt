package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class DisableGoogleSignIfUserDenialsExceedsLimitUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    operator fun invoke(limit: Int = 3) {
        val denialCountSoFar = repository.getGoogleSignInDenialCount()
        if (denialCountSoFar >= limit) {
            repository.setGoogleSignInBlockedTime()
        } else {
            repository.setGoogleSignInDenialCount(denialCountSoFar + 1)
        }
    }
}