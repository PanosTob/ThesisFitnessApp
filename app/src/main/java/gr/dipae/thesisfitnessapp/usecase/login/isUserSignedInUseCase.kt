package gr.dipae.thesisfitnessapp.usecase.login

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class isUserSignedInUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(): Boolean {
        return repository.isUserSignedIn()
    }
}