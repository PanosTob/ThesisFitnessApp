package gr.dipae.thesisfitnessapp.usecase.user

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): User? {
        return try {
            repository.getUser()
        } catch (ex: Exception) {
            null
        }
    }
}