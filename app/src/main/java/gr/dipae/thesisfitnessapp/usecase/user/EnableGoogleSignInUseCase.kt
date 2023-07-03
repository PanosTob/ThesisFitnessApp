package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class EnableGoogleSignInUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke() {
        repository.resetGoogleSignInDenialCount()
    }
}