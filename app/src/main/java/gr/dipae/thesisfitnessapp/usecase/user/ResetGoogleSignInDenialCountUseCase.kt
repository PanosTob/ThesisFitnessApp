package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class ResetGoogleSignInDenialCountUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    operator fun invoke() {
        repository.setGoogleSignInDenialCount(0)
    }
}